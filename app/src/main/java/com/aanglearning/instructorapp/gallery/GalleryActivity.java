package com.aanglearning.instructorapp.gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.album.AlbumActivity;
import com.aanglearning.instructorapp.dao.AlbumDao;
import com.aanglearning.instructorapp.dao.DeletedAlbumDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Album;
import com.aanglearning.instructorapp.model.DeletedAlbum;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.newalbum.NewAlbumActivity;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.PermissionUtil;
import com.aanglearning.instructorapp.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements GalleryView,
        ActivityCompat.OnRequestPermissionsResultCallback{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.noGroups) LinearLayout noGroups;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private Teacher teacher;
    private GalleryPresenter presenter;
    private GalleryAdapter adapter;

    private Album selectedAlbum = new Album();
    private int selectedAlbumPosition, oldSelectedAlbumPosition;
    ActionMode mActionMode;
    boolean isAlbumSelect = false;

    private static final int WRITE_STORAGE_PERMISSION = 333;
    final static int REQ_CODE = 789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teacher = TeacherDao.getTeacher();

        presenter = new GalleryPresenterImpl(this, new GalleryInteractorImpl());

        setupRecyclerView();

        loadOfflineData();

        if (PermissionUtil.isStoragePermissionGranted(this, WRITE_STORAGE_PERMISSION)) {
            syncGallery();
        }
    }

    private void syncGallery() {
        if(NetworkUtil.isNetworkAvailable(this)) {
            if(adapter.getDataSet().size() == 0) {
                presenter.getAlbums(teacher.getSchoolId(), teacher.getId());
            } else {
                presenter.getAlbumsAboveId(teacher.getSchoolId(), teacher.getId(),
                        adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            syncGallery();
        } else {
            showSnackbar("Permission has been denied");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_overflow, menu);
        return true;
    }

    private void loadOfflineData() {
        List<Album> albums = AlbumDao.getAlbums();
        if(albums.size() == 0) {
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(albums, selectedAlbum);
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GalleryAdapter(getApplicationContext(), teacher.getSchoolId(), new ArrayList<Album>(0));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(isAlbumSelect) {
                    single_select(position);
                } else {
                    Intent intent = new Intent(GalleryActivity.this, AlbumActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("album", adapter.getDataSet().get(position));
                    intent.putExtras(args);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isAlbumSelect) {
                    selectedAlbum = new Album();
                    isAlbumSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                    single_select(position);
                }
            }
        }));
    }

    public void addAlbum(MenuItem item) {
        if (NetworkUtil.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, NewAlbumActivity.class);
            startActivityForResult(intent, REQ_CODE);
            overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        } else {
            showSnackbar("You are offline,check your internet.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(adapter.getDataSet().size() == 0) {
                presenter.getAlbums(teacher.getSchoolId(), teacher.getId());
            } else {
                presenter.getAlbumsAboveId(teacher.getSchoolId(), teacher.getId(),
                        adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
            }
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showSnackbar(message);
    }

    @Override
    public void setAlbum(Album album) {
        if(adapter.getDataSet().size() == 0) {
            presenter.getAlbums(teacher.getSchoolId(), teacher.getId());
        } else {
            presenter.getAlbumsAboveId(teacher.getSchoolId(), teacher.getId(),
                    adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
        }
    }

    @Override
    public void albumDeleted() {
        adapter.deleteDataSet(selectedAlbumPosition);
        syncDeletedAlbums();
    }

    @Override
    public void setRecentAlbums(List<Album> albums) {
        adapter.updateDataSet(albums);
        backupAlbums(albums);
        syncDeletedAlbums();
    }

    @Override
    public void setAlbums(List<Album> albums) {
        if(albums.size() == 0) {
            recyclerView.invalidate();
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(albums, selectedAlbum);
            backupAlbums(albums);
        }
        syncDeletedAlbums();
    }

    private void backupAlbums(final List<Album> albums) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AlbumDao.insert(albums);
            }
        }).start();
    }

    private void syncDeletedAlbums() {
        DeletedAlbum deletedAlbum = DeletedAlbumDao.getNewestDeletedAlbum();
        if(deletedAlbum.getId() == 0) {
            presenter.getDeletedAlbums(teacher.getSchoolId(), teacher.getId());
        } else {
            presenter.getRecentDeletedAlbums(teacher.getSchoolId(), teacher.getId(), deletedAlbum.getId());
        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_multi_select, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(GalleryActivity.this);
                    alertDialog.setMessage("Are you sure you want to delete?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            selectedAlbum = new Album();
                            mActionMode.finish();
                            DeletedAlbum deletedAlbum = new DeletedAlbum();
                            deletedAlbum.setSenderId(teacher.getId());
                            deletedAlbum.setAlbumId(adapter.getDataSet().get(selectedAlbumPosition).getId());
                            deletedAlbum.setSchoolId(teacher.getSchoolId());
                            deletedAlbum.setDeletedAt(System.currentTimeMillis());
                            presenter.deleteAlbum(deletedAlbum);
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isAlbumSelect = false;
            selectedAlbum = new Album();
            adapter.selectedItemChanged(selectedAlbumPosition, selectedAlbum);
        }
    };

    public void single_select(int position) {
        selectedAlbumPosition = position;
        if (mActionMode != null) {
            if(selectedAlbum.getId() == adapter.getDataSet().get(position).getId() ) {
                selectedAlbum = new Album();
                mActionMode.finish();
                adapter.selectedItemChanged(position, selectedAlbum);
            } else {
                selectedAlbum = new Album();
                adapter.selectedItemChanged(oldSelectedAlbumPosition, selectedAlbum);
                oldSelectedAlbumPosition = position;
                selectedAlbum = adapter.getDataSet().get(position);
                adapter.selectedItemChanged(position, selectedAlbum);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
