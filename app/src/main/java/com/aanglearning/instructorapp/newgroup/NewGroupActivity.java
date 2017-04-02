package com.aanglearning.instructorapp.newgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.dashboard.DashboardActivity;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.util.EditTextWatcher;
import com.aanglearning.instructorapp.util.SharedPreferenceUtil;

import org.joda.time.LocalDate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGroupActivity extends AppCompatActivity implements NewGroupView,
        AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.group_et)
    EditText groupName;
    @BindView(R.id.group)
    TextInputLayout groupLayout;
    @BindView(R.id.spinner_class)
    Spinner classSpinner;
    @BindView(R.id.spinner_section)
    Spinner sectionSpinner;
    @BindView(R.id.section_layout)
    LinearLayout sectionLayout;
    @BindView(R.id.checkBox)
    CheckBox isForClass;

    private NewGroupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        groupName.addTextChangedListener(new EditTextWatcher(groupLayout));
        presenter = new NewGroupPresenterImpl(this, new NewGroupInteractorImpl());

        isForClass.setOnCheckedChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getClassList(SharedPreferenceUtil.getTeacher(this).getSchoolId());
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 3000).show();
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
            groups.setCreatedBy(TeacherDao.getTeacher().getId());
            LocalDate localDate = new LocalDate();
            groups.setCreatedDate(localDate.toString());
            groups.setActive(true);
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
    public void setError() {
        showSnackbar(getString(R.string.request_error));
    }

    @Override
    public void showAPIError(String message) {
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
    }

    @Override
    public void groupSaved(Groups groups) {
        startActivity(new Intent(this, DashboardActivity.class));
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
}
