package com.aanglearning.instructorapp.chat;

import android.app.Activity;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aanglearning.instructorapp.App;
import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.api.ApiClient;
import com.aanglearning.instructorapp.api.TeacherApi;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.model.Teacher;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatImageActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.image_view) ImageView choseImage;
    @BindView(R.id.progress_layout) FrameLayout progressLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private static final String TAG = "ChatImageActivity";
    private static final int WRITE_STORAGE_PERMISSION = 999;
    private String imageName;
    private long recipientId;
    private Teacher teacher;
    private TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_image);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipientId = extras.getLong("recipientId", 0);
        }

        transferUtility = Util.getTransferUtility(this);

        teacher = TeacherDao.getTeacher();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            imagePicker();
        } else {
            showSnackbar("Permission has been denied");
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(com.darsh.multipleimageselect.helpers.Constants.INTENT_EXTRA_IMAGES);
            File imgFile = new  File(images.get(images.size()-1).path);
            if(imgFile.exists()){
                choseImage.setImageBitmap(new Compressor(ChatImageActivity.this).compressToBitmap(new File(imgFile.getAbsolutePath())));
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
            if (newState.toString().equals("COMPLETED")) {
                Message message = new Message();
                message.setSenderId(teacher.getId());
                message.setSenderName(teacher.getName());
                message.setSenderRole("teacher");
                message.setRecipientId(recipientId);
                message.setRecipientRole("student");
                message.setGroupId(0);
                message.setMessageType("image");
                message.setImageUrl(imageName);
                message.setVideoUrl("");
                message.setMessageBody("");
                message.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
                saveMessage(message);
            } else if (newState.toString().equals("FAILED")) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        }
    }

    public void saveMessage(Message message) {
        TeacherApi api = ApiClient.getAuthorizedClient().create(TeacherApi.class);

        Call<Message> queue = api.saveMessage(message);
        queue.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(response.isSuccessful()) {
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, resultIntent);
                finish();
            }
        });
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
