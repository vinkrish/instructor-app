package com.aanglearning.instructorapp.newgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Teacher;
import com.aanglearning.instructorapp.util.Conversion;
import com.aanglearning.instructorapp.util.EditTextWatcher;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

import org.joda.time.LocalDate;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGroupActivity extends AppCompatActivity implements NewGroupView,
        AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.group_et) EditText groupName;
    @BindView(R.id.group) TextInputLayout groupLayout;
    @BindView(R.id.spinner_class) Spinner classSpinner;
    @BindView(R.id.spinner_section) Spinner sectionSpinner;
    @BindView(R.id.section_layout) LinearLayout sectionLayout;
    @BindView(R.id.checkBox) CheckBox isForClass;

    private Teacher teacher;
    private NewGroupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teacher = TeacherDao.getTeacher();

        groupName.addTextChangedListener(new EditTextWatcher(groupLayout));
        presenter = new NewGroupPresenterImpl(this, new NewGroupInteractorImpl());

        isForClass.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getClassList(teacher.getId());
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public void createGroup(View view) {
        if (groupName.getText().toString().length() == 0) {
            groupName.setError("Group name can't be empty");
        } else {
            Groups groups = new Groups();
            groups.setName(groupName.getText().toString());
            groups.setClassId(((Clas) classSpinner.getSelectedItem()).getId());
            if (isForClass.isChecked()) {
                groups.setClas(true);
                groups.setSection(false);
            } else {
                groups.setSection(false);
                groups.setSection(true);
                groups.setSectionId(((Section) sectionSpinner.getSelectedItem()).getId());
            }
            Teacher teacher = TeacherDao.getTeacher();
            groups.setCreatedBy(teacher.getId());
            groups.setCreatorName(teacher.getName());
            groups.setCreatorRole("admin");
            LocalDate localDate = new LocalDate();
            groups.setCreatedDate(localDate.toString());
            groups.setActive(true);
            groups.setSchoolId(teacher.getSchoolId());
            presenter.saveGroup(groups);
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
    public void showError(String message) {
        showSnackbar(message);
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
            int px = Conversion.dpToPx(20, getApplicationContext());
            sectionLayout.setPadding(0, px, 0, 0);
            sectionLayout.setLayoutParams(params);
        }
    }

    @Override
    public void groupSaved(Groups groups) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        Clas clas = (Clas) classSpinner.getSelectedItem();
        switch (parent.getId()) {
            case R.id.spinner_class:
                presenter.getSectionList(clas.getId());
                break;
            case R.id.spinner_section:
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()) {
            sectionLayout.setVisibility(View.GONE);
        } else {
            sectionLayout.setVisibility(View.VISIBLE);
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
