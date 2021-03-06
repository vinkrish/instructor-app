package com.aanglearning.instructorapp.messagegroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.util.Constants;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.PermissionUtil;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;
import com.aanglearning.instructorapp.util.Util;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.models.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;

public class ImageUploadActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.image_view) ImageView choseImage;
    @BindView(R.id.progress_layout) FrameLayout progressLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.youtube_url) TextView youtubeURL;
    @BindView(R.id.new_msg) EditText newMsg;

    private static final String TAG = "ImageUploadActivity";
    private static final int WRITE_STORAGE_PERMISSION = 666;
    private String imageName;
    private TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        ButterKnife.bind(this);
        transferUtility = Util.getTransferUtility(this);
    }

    @Override
    public void onBackPressed() {
        //progressLayout.setVisibility(View.GONE);
        //progressBar.setVisibility(View.GONE);
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            imagePicker();
        } else {
            showSnackbar("Permission has been denied");
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    public void pasteYoutubeUrl(View view) {
        CharSequence pasteString = "";
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard.getPrimaryClip() != null) {
            android.content.ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pasteString = item.getText();
        }

        if (pasteString != null) {
            if (URLUtil.isValidUrl(pasteString.toString())) {
                youtubeURL.setVisibility(View.VISIBLE);
                youtubeURL.setText(pasteString);
            } else {
                youtubeURL.setText("");
                youtubeURL.setVisibility(View.GONE);
                showSnackbar("URL is not valid");
            }
        } else {
            youtubeURL.setText("");
            youtubeURL.setVisibility(View.GONE);
            showSnackbar("copy YouTube url before pasting");
        }
    }

    public void newImageSendListener(View view) {
        if (!hasImage(choseImage)) {
            showSnackbar("Please choose image");
            return;
        }
        if (NetworkUtil.isNetworkAvailable(this)) {
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            //choseImage.setColorFilter(ContextCompat.getColor(this, R.color.default_white), android.graphics.PorterDuff.Mode.MULTIPLY);
            beginUpload();
        } else showSnackbar("You are offline,check your internet");
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }
        return hasImage;
    }

    public void chooseImage(View view) {
        if (PermissionUtil.isStoragePermissionGranted(this, WRITE_STORAGE_PERMISSION)) {
            imagePicker();
        }
    }

    private void imagePicker() {
        Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
        intent.putExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_LIMIT, 1);
        startActivityForResult(intent, com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            String[] urls = new String[images.size()];
            for (int i = 0, l = images.size(); i < l; i++) {
                urls[i] = images.get(i).path;
                File imgFile = new  File(images.get(i).path);
                if(imgFile.exists()){
                    choseImage.setImageBitmap(new Compressor(ImageUploadActivity.this).compressToBitmap(new File(imgFile.getAbsolutePath())));
                }
            }
        }
    }

    /*
     * Begins to upload the file specified by the file path.
     */
    private void beginUpload() {
        File compressedFile = saveBitmapToFile();
        TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, compressedFile.getName(),
                compressedFile);
        observer.setTransferListener(new UploadListener());
    }

    /*
     * A TransferListener class that can listen to a upload task and be notified
     * when the status changes.
     */
    private class UploadListener implements TransferListener {

        // Simply updates the UI list when notified.
        @Override
        public void onError(int id, Exception e) {
            Log.e(TAG, "Error during upload: " + id, e);
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                    id, bytesTotal, bytesCurrent));
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            Log.d(TAG, "onStateChanged: " + id + ", " + newState);
            if (newState.toString().equals("COMPLETED")) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("text", newMsg.getText().toString().trim());
                if(youtubeURL.getText().equals("")) {
                    resultIntent.putExtra("type", "image");
                    resultIntent.putExtra("url", "");
                } else {
                    resultIntent.putExtra("type", "both");
                    resultIntent.putExtra("url", youtubeURL.getText().toString());
                }
                resultIntent.putExtra("imgName", imageName);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } else if (newState.toString().equals("FAILED")) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        }
    }

    public File saveBitmapToFile() {
        try {
            Bitmap selectedBitmap = ((BitmapDrawable) choseImage.getDrawable()).getBitmap();

            imageName = System.currentTimeMillis() + ".jpg";

            // here i override the original image file
            File dir = new File(Environment.getExternalStorageDirectory().getPath(),
                    "Shikshitha/Teacher/" + SharedPreferenceUtil.getTeacher(this).getSchoolId());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File newFile = new File(dir, imageName);

            newFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(newFile);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream);

            return newFile;
        } catch (Exception e) {
            return null;
        }
    }

}
