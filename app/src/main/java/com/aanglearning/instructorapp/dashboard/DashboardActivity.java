package com.aanglearning.instructorapp.dashboard;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.dao.ServiceDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.login.LoginActivity;
import com.aanglearning.instructorapp.messagegroup.MessageActivity;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Service;
import com.aanglearning.instructorapp.newgroup.NewGroupActivity;
import com.aanglearning.instructorapp.util.AppGlobal;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements GroupView{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private GroupPresenter presenter;
    private GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        presenter = new GroupPresenterImpl(this, new GroupInteractorImpl());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.dashboard_item:
                                break;
                            case R.id.attendance_item:
                                break;
                            case R.id.homework_item:
                                break;
                            case R.id.logout_item:
                                SharedPreferenceUtil.logout(DashboardActivity.this);
                                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                                finish();
                                break;
                            default:
                                break;
                        }
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

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

        View hView = navigationView.inflateHeaderView(R.layout.header);
        ImageView imageView = (ImageView) hView.findViewById(R.id.user_image);
        TextView tv = (TextView) hView.findViewById(R.id.name);
        imageView.setImageResource(R.drawable.child);
        tv.setText(TeacherDao.getTeacher().getTeacherName());

        hideDrawerItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getGroups(TeacherDao.getTeacher().getId());
    }

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
    }

    public void addGroup(View view) {
        if (NetworkUtil.isNetworkAvailable(this)) {
            startActivity(new Intent(this, NewGroupActivity.class));
        } else {
            showSnackbar("You are offline,check your internet.");
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 3000).show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        showSnackbar(getString(R.string.request_error));
    }

    @Override
    public void showAPIError(String message) {
        showSnackbar(message);
    }

    @Override
    public void setGroups(List<Groups> groups) {
        adapter = new GroupAdapter(groups, new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Groups group) {
                GroupDao.clear();
                GroupDao.insert(group);
                startActivity(new Intent(DashboardActivity.this, MessageActivity.class));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
