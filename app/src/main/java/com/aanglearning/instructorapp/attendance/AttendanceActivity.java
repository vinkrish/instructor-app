package com.aanglearning.instructorapp.attendance;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Attendance;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Section;
import com.aanglearning.instructorapp.model.Student;
import com.aanglearning.instructorapp.model.StudentSet;
import com.aanglearning.instructorapp.model.UserGroup;
import com.aanglearning.instructorapp.usergroup.StudentMemberAdapter;
import com.aanglearning.instructorapp.util.AlertDialogHelper;
import com.aanglearning.instructorapp.util.DatePickerFragment;
import com.aanglearning.instructorapp.util.DateUtil;
import com.aanglearning.instructorapp.util.DividerItemDecoration;
import com.aanglearning.instructorapp.util.RecyclerItemClickListener;

import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceActivity extends AppCompatActivity implements AttendanceView,
        AdapterView.OnItemSelectedListener, AlertDialogHelper.AlertDialogListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.spinner_class) Spinner classSpinner;
    @BindView(R.id.spinner_section) Spinner sectionSpinner;
    @BindView(R.id.date_tv) TextView dateView;
    @BindView(R.id.absentees_recycler_view) RecyclerView absenteesRecycler;
    @BindView(R.id.student_recycler_view) RecyclerView studentRecycler;
    @BindView(R.id.absentees_tv) TextView absenteesTv;
    @BindView(R.id.mark_students) TextView markStudentsTv;

    private AttendancePresenter presenter;
    private AttendanceAdapter attendanceAdapter;
    private StudentAdapter studentAdapter;
    private String attendanceDate;
    ActionMode mActionMode;
    boolean isMultiSelect = false;
    AlertDialogHelper alertDialogHelper;
    ArrayList<Attendance> absentees = new ArrayList<>();
    ArrayList<Attendance> multiselect_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new AttendancePresenterImpl(this, new AttendanceInteractorImpl());
        setDefaultDate();

        alertDialogHelper = new AlertDialogHelper(this);

        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getClassList(TeacherDao.getTeacher().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void initRecyclerView() {
        absenteesRecycler.setLayoutManager(new LinearLayoutManager(this));
        absenteesRecycler.setNestedScrollingEnabled(false);
        absenteesRecycler.setItemAnimator(new DefaultItemAnimator());

        attendanceAdapter = new AttendanceAdapter(this, absentees, multiselect_list);
        absenteesRecycler.setAdapter(attendanceAdapter);

        absenteesRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, absenteesRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) multi_select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    multiselect_list = new ArrayList<>();
                    isMultiSelect = true;

                    if (mActionMode == null) {
                        mActionMode = startActionMode(mActionModeCallback);
                    }
                }
                multi_select(position);
            }
        }));

        studentRecycler.setLayoutManager(new LinearLayoutManager(this));
        studentRecycler.setNestedScrollingEnabled(false);
        studentRecycler.setItemAnimator(new DefaultItemAnimator());
        studentRecycler.addItemDecoration(new DividerItemDecoration(this));
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(absentees.get(position)))
                multiselect_list.remove(absentees.get(position));
            else
                multiselect_list.add(absentees.get(position));

            if (multiselect_list.size() > 0)
                mActionMode.setTitle("" + multiselect_list.size());
            else
                mActionMode.setTitle("");
            refreshAdapter();
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
                    alertDialogHelper.showAlertDialog("","Remove Marked Attendance","DELETE","CANCEL",1,false);
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            isMultiSelect = false;
            multiselect_list = new ArrayList<>();
            refreshAdapter();
        }
    };

    public void refreshAdapter() {
        attendanceAdapter.setDataSet(absentees, multiselect_list);
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, 3000).show();
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
    public void showAttendance(AttendanceSet attendanceSet) {
        absentees = attendanceSet.getAttendanceList();
        attendanceAdapter.setDataSet(absentees, multiselect_list);
        if(absentees.size() == 0) {
            absenteesTv.setVisibility(View.GONE);
        }

        ArrayList<StudentSet> studentSets = new ArrayList<>();
        for(Student s: attendanceSet.getStudents()) {
            studentSets.add(new StudentSet(s.getId(), s.getRollNo(), s.getStudentName()));
        }
        studentAdapter = new StudentAdapter(studentSets, getApplicationContext());
        studentRecycler.setAdapter(studentAdapter);
        if(studentSets.size() == 0) {
            markStudentsTv.setVisibility(View.GONE);
        }
    }

    public void saveAbsentees(View view) {
        showProgress();
        ArrayList<Attendance> attList = new ArrayList<>();
        for(StudentSet studentSet : studentAdapter.getDataSet()) {
            if(studentSet.isSelected()) {
                Attendance att = new Attendance();
                att.setStudentId(studentSet.getId());
                att.setStudentName(studentSet.getStudentName());
                att.setSectionId(((Section)sectionSpinner.getSelectedItem()).getId());
                att.setDateAttendance(attendanceDate);
                att.setTypeOfLeave("Absent");
                att.setType("Daily");
                att.setSession(0);
                attList.add(att);
            }
        }
        hideProgress();
        if(attList.size() == 0) {
            showSnackbar("No changes detected");
        } else {
            presenter.saveAttendance(attList);
        }
    }

    @Override
    public void attendanceSaved() {
        recreate();
    }

    @Override
    public void attendanceDeleted() {
        recreate();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        //Clas clas = (Clas) classSpinner.getSelectedItem();
        switch (parent.getId()) {
            case R.id.spinner_class:
                presenter.getSectionList(((Clas) classSpinner.getSelectedItem()).getId(), TeacherDao.getTeacher().getId());
                break;
            case R.id.spinner_section:
                presenter.getAttendance(((Section)sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                        0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setDefaultDate() {
        dateView.setText(DateUtil.getDisplayFormattedDate(new LocalDate().toString()));
        attendanceDate = new LocalDate().toString();
    }

    public void changeDate(View view) {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(attendanceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        newFragment.setCallBack(onDate);
        newFragment.setArguments(bundle);
        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            Date date = cal.getTime();

            Calendar tomorrowCal = Calendar.getInstance();
            Date tomorrowDate = tomorrowCal.getTime();

            if (date.after(tomorrowDate)) {
                showSnackbar(getResources().getText(R.string.future_date).toString());
            } else {
                dateView.setText(DateUtil.getDisplayFormattedDate(dateFormat.format(date)));
                attendanceDate = dateFormat.format(date);
                getAttendance();
            }
        }
    };

    private void getAttendance() {
        presenter.getAttendance(((Section)sectionSpinner.getSelectedItem()).getId(), attendanceDate,
                0);
    }

    @Override
    public void onPositiveClick(int from) {
        if(from==1) {
            if(multiselect_list.size()>0) {
                presenter.deleteAttendance(multiselect_list);
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
