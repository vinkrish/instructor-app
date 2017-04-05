package com.aanglearning.instructorapp.usergroup;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.model.UserGroup;
import com.aanglearning.instructorapp.util.AlertDialogHelper;
import com.aanglearning.instructorapp.util.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserGroupActivity extends AppCompatActivity implements
        UserGroupView, AlertDialogHelper.AlertDialogListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.group_name_tv) TextView groupName;
    @BindView(R.id.add_students_layout) RelativeLayout addStudentsLayout;
    @BindView(R.id.add_teacher_layout) TextView addTeacherLayout;
    @BindView(R.id.member_recycler_view) RecyclerView memberView;
    @BindView(R.id.student_recycler_view) RecyclerView studentView;
    @BindView(R.id.teacher_recycler_view) RecyclerView teacherView;

    private UserGroupPresenter presenter;
    private Groups group;
    private UserGroupAdapter adapter;
    private StudentMemberAdapter studentAdapter;
    private TeacherMemberAdapter teacherAdapter;
    AlertDialogHelper alertDialogHelper;

    ActionMode mActionMode;
    boolean isMultiSelect = false;
    ArrayList<UserGroup> userGroups = new ArrayList<>();
    ArrayList<UserGroup> multiselect_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_group);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new UserGroupPresenterImpl(this, new UserGroupInteractorImpl());

        alertDialogHelper = new AlertDialogHelper(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        memberView.setLayoutManager(new LinearLayoutManager(this));
        memberView.setNestedScrollingEnabled(false);
        memberView.setItemAnimator(new DefaultItemAnimator());

        adapter = new UserGroupAdapter(this, userGroups, multiselect_list);
        memberView.setAdapter(adapter);

        memberView.addOnItemTouchListener(new RecyclerItemClickListener(this, memberView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) multi_select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<UserGroup>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }
                multi_select(position);
            }
        }));

        studentView.setLayoutManager(new LinearLayoutManager(this));
        studentView.setNestedScrollingEnabled(false);
        studentView.setItemAnimator(new DefaultItemAnimator());

        teacherView.setLayoutManager(new LinearLayoutManager(this));
        teacherView.setNestedScrollingEnabled(false);
        teacherView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onResume() {
        super.onResume();
        group = GroupDao.getGroup();
        groupName.setText(group.getName());
        presenter.getUserGroup(group.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public void selectAllStudents(View view) {
        showProgress();
        ArrayList<GroupStudent> groupStudents = studentAdapter.getDataSet();
        for(GroupStudent groupStudent: groupStudents) {
            groupStudent.setSelected(true);
        }
        studentAdapter.setDataSet(groupStudents);
        hideProgress();
    }

    public void saveUsers(View view) {
        showProgress();
        ArrayList<UserGroup> userGroups = new ArrayList<>();
        for(GroupStudent groupStudent: studentAdapter.getDataSet()) {
            if(groupStudent.isSelected()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(groupStudent.getId());
                userGroup.setRole("student");
                userGroup.setGroupId(group.getId());
                userGroup.setActive(true);
                userGroups.add(userGroup);
            }
        }
        for(GroupTeacher groupTeacher: teacherAdapter.getDataSet()) {
            if(groupTeacher.isSelected()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserId(groupTeacher.getId());
                userGroup.setRole("teacher");
                userGroup.setGroupId(group.getId());
                userGroup.setActive(true);
                userGroups.add(userGroup);
            }
        }
        hideProgress();
        if(userGroups.size() == 0) {
            showSnackbar("No changes detected");
        } else {
            presenter.saveUserGroup(userGroups);
        }
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
    public void setError() {
        showSnackbar(getString(R.string.request_error));
    }

    @Override
    public void showAPIError(String message) {
        showSnackbar(message);
    }

    @Override
    public void showUserGroup(GroupUsers groupUsers) {
        userGroups = groupUsers.getUserGroupList();
        adapter.setDataSet(userGroups, multiselect_list);

        ArrayList<GroupStudent> groupStudents = new ArrayList<>();
        for(Student s: groupUsers.getStudents()) {
            groupStudents.add(new GroupStudent(s.getId(), s.getStudentName()));
        }
        studentAdapter = new StudentMemberAdapter(groupStudents);
        studentView.setAdapter(studentAdapter);
        if(groupStudents.size() == 0) {
            addStudentsLayout.setVisibility(View.GONE);
        }

        ArrayList<GroupTeacher> groupTeachers = new ArrayList<>();
        for(Teacher t: groupUsers.getTeachers()) {
            groupTeachers.add(new GroupTeacher(t.getId(), t.getTeacherName()));
        }
        teacherAdapter = new TeacherMemberAdapter(groupTeachers);
        teacherView.setAdapter(teacherAdapter);
        if(groupTeachers.size() == 0) {
            addTeacherLayout.setVisibility(View.GONE);
        }
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(userGroups.get(position)))
                multiselect_list.remove(userGroups.get(position));
            else
                multiselect_list.add(userGroups.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");
            refreshAdapter();
        }
    }

    public void refreshAdapter() {
        adapter.setDataSet(userGroups, multiselect_list);
    }

    @Override
    public void userGroupSaved() {
        recreate();
    }

    @Override
    public void userGroupDeleted() {
        recreate();
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 3000).show();
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
                    alertDialogHelper.showAlertDialog("","Remove Users","DELETE","CANCEL",1,false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<UserGroup>();
            refreshAdapter();
        }
    };

    @Override
    public void onPositiveClick(int from) {
        if(from==1) {
            if(multiselect_list.size()>0) {
                presenter.deleteUsers(multiselect_list);
                if (mActionMode != null) {
                    mActionMode.finish();
                }
            }
        }
    }

    @Override
    public void onNegativeClick(int from) {

    }

    @Override
    public void onNeutralClick(int from) {

    }
}
