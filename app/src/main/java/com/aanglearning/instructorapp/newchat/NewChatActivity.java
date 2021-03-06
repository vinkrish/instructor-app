package com.aanglearning.instructorapp.newchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.chathome.ChatsActivity;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Chat;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.util.Conversion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewChatActivity extends AppCompatActivity implements
        NewChatView, View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.class_spinner) Spinner classSpinner;
    @BindView(R.id.section_spinner) Spinner sectionSpinner;
    @BindView(R.id.student_spinner) Spinner studentSpinner;
    @BindView(R.id.section_layout) LinearLayout sectionLayout;

    private NewChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new NewChatPresenterImpl(this, new NewChatInteractorImpl());

    }

    public void onResume() {
        super.onResume();
        presenter.getClassList(TeacherDao.getTeacher().getSchoolId());
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
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
    public void showError(String message) {
        Snackbar errorSnackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        errorSnackbar.setAction(R.string.retry, this);
        errorSnackbar.show();
    }

    @Override
    public void showClass(List<Clas> clasList) {
        ArrayAdapter<Clas> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clasList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showSection(List<Section> sectionList) {
        ArrayAdapter<Section> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sectionList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectionSpinner.setAdapter(adapter);
        sectionSpinner.setOnItemSelectedListener(this);
        if(sectionList.size() == 1 && sectionList.get(0).getSectionName().equals("none")) {
            sectionLayout.setVisibility(View.INVISIBLE);
            sectionLayout.setLayoutParams(new LinearLayout.LayoutParams(0,0));
        } else {
            sectionLayout.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int px = Conversion.dpToPx(24, getApplicationContext());
            sectionLayout.setPadding(0, px, 0, 0);
            sectionLayout.setLayoutParams(params);
        }
    }

    @Override
    public void showStudent(List<Student> studentList) {
        ArrayAdapter<Student> adapter = new
                ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentSpinner.setAdapter(adapter);
        studentSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void chatSaved(Chat chat) {
        startActivity(new Intent(this, ChatsActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        presenter.getClassList(TeacherDao.getTeacher().getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.class_spinner:
                presenter.getSectionList(((Clas)classSpinner.getSelectedItem()).getId(), TeacherDao.getTeacher().getId());
                break;
            case R.id.section_spinner:
                presenter.getStudentList(((Section)sectionSpinner.getSelectedItem()).getId());
                break;
            case R.id.student_spinner:
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void createChat(View view) {
        showProgress();
        Teacher t = TeacherDao.getTeacher();
        Chat newChat = new Chat();
        newChat.setStudentId(((Student)studentSpinner.getSelectedItem()).getId());
        newChat.setStudentName(((Student)studentSpinner.getSelectedItem()).getName());
        newChat.setClassName(((Clas) classSpinner.getSelectedItem()).getClassName());
        newChat.setSectionName(((Section) sectionSpinner.getSelectedItem()).getSectionName());
        newChat.setTeacherId(t.getId());
        newChat.setTeacherName(t.getName());
        newChat.setCreatedBy(t.getId());
        newChat.setCreatorRole("teacher");
        presenter.saveChat(newChat);
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
