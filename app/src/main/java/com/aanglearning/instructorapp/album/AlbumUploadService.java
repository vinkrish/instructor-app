package com.aanglearning.instructorapp.album;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.aanglearning.instructorapp.dao.ImageStatusDao;
import com.aanglearning.instructorapp.messagegroup.ImageUploadActivity;
import com.aanglearning.instructorapp.model.AlbumImage;
import com.aanglearning.instructorapp.model.ImageStatus;
import com.aanglearning.instructorapp.util.Constants;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;
import com.aanglearning.instructorapp.util.Util;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumUploadService extends IntentService {
    private TransferUtility transferUtility;

    public AlbumUploadService() {
        super("AlbumUploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            transferUtility = Util.getTransferUtility(getApplicationContext());
            ArrayList<ImageStatus> albumImages = ImageStatusDao.getAlbumImages(intent.getLongExtra("albumId", 0));
            for(ImageStatus imageStatus : albumImages) {
                File file = new File(Environment.getExternalStorageDirectory().getPath(),
                        "Shikshitha/Teacher/" + SharedPreferenceUtil.getTeacher(this).getSchoolId() + "/" + imageStatus.getName());
                if(file.exists()) {
                    TransferObserver observer = transferUtility.upload(Constants.BUCKET_NAME, file.getName(),
                            file);
                    observer.setTransferListener(new UploadListener(imageStatus.getName()));
                }
            }
        }

    }

    private class UploadListener implements TransferListener {
        private String imageName;

        UploadListener(String name) {
            this.imageName = name;
        }

        @Override
        public void onError(int id, Exception e) {
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
        }

        @Override
        public void onStateChanged(int id, TransferState newState) {
            if (newState.toString().equals("COMPLETED")) {
                ImageStatusDao.delete(imageName);
            } else if (newState.toString().equals("FAILED")) {

            }
        }
    }

}
