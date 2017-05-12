package com.aanglearning.instructorapp.messagegroup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.util.Constants;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.PermissionUtil;
import com.aanglearning.instructorapp.util.Util;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageUploadActivity extends AppCompatActivity {
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.image_view) ImageView choseImage;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.new_msg) EditText newMsg;

    private static final String TAG = "ImageUploadActivity";
    private static final int READ_STORAGE_PERMISSION = 4000;
    private String imagePath;
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
        progressBar.setVisibility(View.GONE);
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 2000).show();
    }

    public void newImageSendListener (View view) {
        if (imagePath == null) {
            showSnackbar("Please choose image");
            return;
        }
        if (NetworkUtil.isNetworkAvailable(this)){
            progressBar.setVisibility(View.VISIBLE);
            beginUpload(imagePath);
        }
        else showSnackbar("You are offline,check your internet");
    }

    public void chooseImage (View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionUtil.checkPermissionForExternalStorage(ImageUploadActivity.this)) {
                PermissionUtil.requestStoragePermission(ImageUploadActivity.this, READ_STORAGE_PERMISSION);
            } else {
                imagePicker();
            }
        }else{
            imagePicker();
        }
    }

    private void imagePicker() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 19) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.setType("*/*");
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
        }
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            try {
                imagePath = getPath(uri);

                Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);
                        choseImage.setImageBitmap(myBitmap);
                    }
                };
                handler.postDelayed(r, 100);

            } catch (URISyntaxException e) {
                Toast.makeText(this,
                        "Unable to get the file from the given URI.  See error log for details",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable to upload file from the given uri", e);
            }
        }
    }

    /*
     * Begins to upload the file specified by the file path.
     */
    private void beginUpload(String filePath) {
        File compressedFile = saveBitmapToFile(new File(filePath));
        //File file = new File(filePath);
        TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, compressedFile.getName(),
                compressedFile);
         observer.setTransferListener(new UploadListener());
    }

    /*
     * Gets the file path of the given Uri.
     */
    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[] {
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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
            //updateList();
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.d(TAG, String.format("onProgressChanged: %d, total: %d, current: %d",
                    id, bytesTotal, bytesCurrent));
            //updateList();
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            Log.d(TAG, "onStateChanged: " + id + ", " + newState);
            if(newState.toString().equals("COMPLETED")) {
                progressBar.setVisibility(View.GONE);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("text", newMsg.getText().toString().trim());
                resultIntent.putExtra("imgName", imageName);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    public File saveBitmapToFile(File file){
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            File dir = new File(Environment.getExternalStorageDirectory().getPath(), "AangSolutions/Images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File newFile = new File(dir, file.getName());
            imageName = file.getName();
            newFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(newFile);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return newFile;
        } catch (Exception e) {
            return null;
        }
    }

}
