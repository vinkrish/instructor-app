package com.aanglearning.instructorapp.dashboard.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements HomeView {
    @BindView(R.id.text_view) TextView textView;
    @BindView(R.id.image_view) ImageView imageView;
    @BindView(R.id.progress) ProgressBar progressBar;

    private Drawable d;

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
        startActivityForResult(intent, Constants.REQUEST_CODE);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            StringBuilder stringBuilder = new StringBuilder();
            String[] urls = new String[images.size()];
            for (int i = 0, l = images.size(); i < l; i++) {
                stringBuilder.append(images.get(i).path).append("\n");
                urls[i] = images.get(i).path;
                File imgFile = new  File(images.get(i).path);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //imageView.setImageBitmap(myBitmap);
                }
            }
            textView.setText(stringBuilder.toString());
            new OffLoadTask().execute(urls);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    class OffLoadTask extends AsyncTask<String, Void, Void> {
        Bitmap myBitmap;

        protected void onPreExecute() {
            super.onPreExecute();
            d = imageView.getDrawable();
            showProgress();
        }

        @Override
        protected Void doInBackground(String... urls) {
            Cloudinary cloudinary = new Cloudinary("cloudinary://327363375662612:0EcU7FUd1dBfN9pzEGq6GHOWFGw@vinkrish");
            try {

                for(String url: urls) {
                    //Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                    //BitmapDrawable bitDw = ((BitmapDrawable) d);
                    //Bitmap bitmap = bitDw.getBitmap();

                    File imgFile = new  File(url);
                    if(imgFile.exists()){
                        myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    }

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                    //byte[] imageInByte = stream.toByteArray();
                    ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

                    cloudinary.uploader().upload(bis, ObjectUtils.emptyMap());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            hideProgess();
        }
    }

}
