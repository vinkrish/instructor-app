package com.aanglearning.instructorapp.dashboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.attendance.AttendanceActivity;
import com.aanglearning.instructorapp.calendar.CalendarActivity;
import com.aanglearning.instructorapp.chathome.ChatsActivity;
import com.aanglearning.instructorapp.dao.DeletedGroupDao;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.dao.ServiceDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.gallery.GalleryActivity;
import com.aanglearning.instructorapp.homework.HomeworkActivity;
import com.aanglearning.instructorapp.login.LoginActivity;
import com.aanglearning.instructorapp.messagegroup.MessageActivity;
import com.aanglearning.instructorapp.model.DeletedGroup;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Service;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.newgroup.NewGroupActivity;
import com.aanglearning.instructorapp.sqlite.SqlDbHelper;
import com.aanglearning.instructorapp.timetable.TimetableActivity;
import com.aanglearning.instructorapp.util.DividerItemDecoration;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.PermissionUtil;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements GroupView{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.noGroups) LinearLayout noGroups;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private GroupPresenter presenter;
    private GroupAdapter adapter;
    private Teacher teacher;
    private long groupId;

    final static int REQ_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Boolean isNotified = false;
        setupDrawerContent(navigationView);

        teacher = TeacherDao.getTeacher();

        toolbar.setTitle(teacher.getName());
        toolbar.setSubtitle("Teacher");
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isNotified = true;
            groupId = extras.getLong("group_id", 0);
        }

        presenter = new GroupPresenterImpl(this, new GroupInteractorImpl());

        ActionBarDrawerToggle actionBarDrawerToggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setProfile();

        hideDrawerItem();

        setupRecyclerView();

        loadOfflineData();

        if(NetworkUtil.isNetworkAvailable(this)) {
            if(isNotified) {
                Groups group = GroupDao.getGroup(groupId);
                if(group.getId() == 0) {
                    presenter.getGroup(groupId);
                } else {
                    setGroup(group);
                }
            } else {
                if(adapter.getDataSet().size() == 0) {
                    presenter.getGroups(teacher.getId());
                } else {
                    presenter.getGroupsAboveId(teacher.getId(), adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
                }
            }
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        adapter = new GroupAdapter(new ArrayList<Groups>(0), mItemListener);
        recyclerView.setAdapter(adapter);

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(adapter.getDataSet().size() == 0) {
                    presenter.getGroups(teacher.getId());
                } else {
                    presenter.getGroupsAboveId(teacher.getId(), adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
                }
            }
        });
    }

    private void loadOfflineData() {
        List<Groups> groups = GroupDao.getGroups(teacher.getId());
        if(groups.size() == 0) {
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(groups);
        }
    }

    private void setProfile() {
        View hView = navigationView.inflateHeaderView(R.layout.header);
        final ImageView imageView = hView.findViewById(R.id.user_image);
        TextView tv = hView.findViewById(R.id.name);
        tv.setText(teacher.getName());

        if(PermissionUtil.getStoragePermissionStatus(this)) {
            File dir = new File(Environment.getExternalStorageDirectory().getPath(), "Shikshitha/Teacher/" + teacher.getSchoolId());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            final File file = new File(dir, teacher.getImage());
            if(file.exists()) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                Picasso.with(this)
                        .load("https://s3.ap-south-1.amazonaws.com/shikshitha-images/" + teacher.getSchoolId() + "/" + teacher.getImage())
                        .placeholder(R.drawable.splash_image)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                try {
                                    FileOutputStream fos = new FileOutputStream(file);
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError() {
                                imageView.setImageResource(R.drawable.ic_account_black);
                            }
                        });
            }
        }
    }

    protected boolean isNavDrawerOpen() {
        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void hideDrawerItem() {
        Menu menu = navigationView.getMenu();
        Service service = ServiceDao.getServices();
        if(!service.isAttendance()) menu.findItem(R.id.attendance_item).setVisible(false);
        if(!service.isHomework()) menu.findItem(R.id.homework_item).setVisible(false);
        if(!service.isChat()) menu.findItem(R.id.chat_item).setVisible(false);
        if (!service.isTimetable()) menu.findItem(R.id.timetable_item).setVisible(false);
    }

    public void addGroup(View view) {
        if (NetworkUtil.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, NewGroupActivity.class);
            startActivityForResult(intent, REQ_CODE);
        } else {
            showSnackbar("You are offline,check your internet.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(adapter.getDataSet().size() == 0) {
                presenter.getGroups(teacher.getId());
            } else {
                presenter.getGroupsAboveId(teacher.getId(), adapter.getDataSet().get(adapter.getItemCount() - 1).getId());
            }
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        showSnackbar(message);
    }

    @Override
    public void backupGroup(Groups group) {
        GroupDao.insertMany(Collections.singletonList(group));
    }

    @Override
    public void setGroup(Groups group) {
        backupGroup(group);
        loadOfflineData();
        Intent intent = new Intent(DashboardActivity.this, MessageActivity.class);
        Bundle args = new Bundle();
        if(group != null){
            args.putSerializable("group", group);
        }
        intent.putExtras(args);
        startActivity(intent);
    }

    @Override
    public void setRecentGroups(List<Groups> groups) {
        adapter.updateDataSet(groups);
        backupGroups(groups);
        syncDeletedGroups();
    }

    @Override
    public void setGroups(List<Groups> groups) {
        if(groups.size() == 0) {
            recyclerView.invalidate();
            noGroups.setVisibility(View.VISIBLE);
        } else {
            noGroups.setVisibility(View.INVISIBLE);
            adapter.replaceData(groups);
            backupGroups(groups);
        }
        syncDeletedGroups();
    }

    private void backupGroups(final List<Groups> groups) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                GroupDao.insertMany(groups);
            }
        }).start();
    }

    private void syncDeletedGroups() {
        DeletedGroup deletedGroup = DeletedGroupDao.getNewestDeletedGroup();
        if(deletedGroup.getId() == 0) {
            presenter.getDeletedGroups(teacher.getSchoolId());
        } else {
            presenter.getRecentDeletedGroups(teacher.getSchoolId(), deletedGroup.getId());
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard_item:
                        startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
                        break;
                    case R.id.attendance_item:
                        startActivity(new Intent(DashboardActivity.this, AttendanceActivity.class));
                        break;
                    case R.id.homework_item:
                        startActivity(new Intent(DashboardActivity.this, HomeworkActivity.class));
                        break;
                    case R.id.timetable_item:
                        startActivity(new Intent(DashboardActivity.this, TimetableActivity.class));
                        break;
                    case R.id.event_item:
                        startActivity(new Intent(DashboardActivity.this, CalendarActivity.class));
                        break;
                    case R.id.gallery_item:
                        startActivity(new Intent(DashboardActivity.this, GalleryActivity.class));
                        break;
                    case R.id.chat_item:
                        startActivity(new Intent(DashboardActivity.this, ChatsActivity.class));
                        break;
                    case R.id.logout_item:
                        logout();
                        break;
                    default:
                        break;
                }
                menuItem.setChecked(false);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void logout() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DashboardActivity.this);
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferenceUtil.logout(DashboardActivity.this);
                SqlDbHelper.getInstance(DashboardActivity.this).deleteTables();
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    GroupAdapter.OnItemClickListener mItemListener = new GroupAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Groups group) {
            Intent intent = new Intent(DashboardActivity.this, MessageActivity.class);
            Bundle args = new Bundle();
            if(group != null){
                args.putSerializable("group", group);
            }
            intent.putExtras(args);
            startActivity(intent);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
