package com.aanglearning.instructorapp.homework;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Clas;
import com.aanglearning.instructorapp.model.Homework;
import com.aanglearning.instructorapp.model.Section;
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

public class HomeworkActivity extends AppCompatActivity implements HomeworkView,
        AdapterView.OnItemSelectedListener, AlertDialogHelper.AlertDialogListener {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.spinner_class) Spinner classSpinner;
    @BindView(R.id.spinner_section) Spinner sectionSpinner;
    @BindView(R.id.date_tv) TextView dateView;
    @BindView(R.id.homework_recycler_view) RecyclerView homeworkRecycler;
    @BindView(R.id.subject_recycler_view) RecyclerView subjectRecycler;
    @BindView(R.id.homework_tv) TextView homeworkTv;
    @BindView(R.id.enter_homework) TextView enterHomeworkTv;

    private HomeworkPresenter presenter;
    private String homeworkDate;
    ActionMode mActionMode;
    boolean isMultiSelect = false;
    AlertDialogHelper alertDialogHelper;

    ArrayList<Homework> homeworks = new ArrayList<>();
    ArrayList<Homework> multiselect_list = new ArrayList<>();

    private HomeworkAdapter homeworkAdapter;
    private NewHomeworkAdapter newHomeworkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new HomeworkPresenterImpl(this, new HomeworkInteractorImpl());

        alertDialogHelper = new AlertDialogHelper(this);

        initRecyclerView();

        setDefaultDate();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getClassList(TeacherDao.getTeacher().getId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeworks = new ArrayList<>();
        presenter.getClassList(TeacherDao.getTeacher().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setDefaultDate() {
        dateView.setText(DateUtil.getDisplayFormattedDate(new LocalDate().toString()));
        homeworkDate = new LocalDate().toString();
    }

    public void changeDate(View view) {
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        try {
            date = defaultFormat.parse(homeworkDate);
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
                homeworkDate = dateFormat.format(date);
                getHomework();
            }
        }
    };

    private void initRecyclerView() {
        homeworkRecycler.setLayoutManager(new LinearLayoutManager(this));
        homeworkRecycler.setNestedScrollingEnabled(false);
        homeworkRecycler.setItemAnimator(new DefaultItemAnimator());

        homeworkAdapter = new HomeworkAdapter(this, new ArrayList<Homework>(0), new ArrayList<Homework>(0));
        homeworkRecycler.setAdapter(homeworkAdapter);

        homeworkRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, homeworkRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect) multi_select(position);
                else {
                    final Homework homework = homeworks.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeworkActivity.this);
                    View v = getLayoutInflater().inflate(R.layout.homework_dialog, null);
                    TextView subjectName = (TextView) v.findViewById(R.id.hw_subject);
                    subjectName.setText(homework.getSubjectName());
                    final EditText homeworkText = (EditText) v.findViewById(R.id.hw_et);
                    homeworkText.setText(homework.getHomeworkMessage());
                    builder.setView(v);

                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            homework.setHomeworkMessage(homeworkText.getText().toString());
                            presenter.updateHomework(homework);
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                }
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

        subjectRecycler.setLayoutManager(new LinearLayoutManager(this));
        subjectRecycler.setNestedScrollingEnabled(false);
        subjectRecycler.setItemAnimator(new DefaultItemAnimator());
        subjectRecycler.addItemDecoration(new DividerItemDecoration(this));
        newHomeworkAdapter = new NewHomeworkAdapter(new ArrayList<Homework>(0), mItemListener);
        subjectRecycler.setAdapter(newHomeworkAdapter);
    }

    public void multi_select(int position) {
        if (mActionMode != null) {
            if (multiselect_list.contains(homeworks.get(position)))
                multiselect_list.remove(homeworks.get(position));
            else
                multiselect_list.add(homeworks.get(position));

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
                    alertDialogHelper.showAlertDialog("", "Delete Homework", "DELETE", "CANCEL", 1, false);
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
        homeworkAdapter.setDataSet(homeworks, multiselect_list);
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
        refreshLayout.setRefreshing(false);
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
    public void showHomeworks(List<Homework> hws) {
        refreshLayout.setRefreshing(false);
        homeworks = new ArrayList<>();
        List<Homework> newHomework = new ArrayList<>();
        for (Homework hw : hws) {
            if (hw.getId() == 0) newHomework.add(hw);
            else homeworks.add(hw);
        }
        homeworkAdapter.setDataSet(homeworks, multiselect_list);
        if (homeworks.size() == 0) homeworkTv.setVisibility(View.GONE);
        else homeworkTv.setVisibility(View.VISIBLE);

        newHomeworkAdapter.setDataSet(newHomework);
        if (newHomework.size() == 0) enterHomeworkTv.setVisibility(View.GONE);
        else enterHomeworkTv.setVisibility(View.VISIBLE);

    }

    @Override
    public void homeworkSaved(Homework homework) {
        recreate();
    }

    @Override
    public void homeworkUpdated() {
        recreate();
    }

    @Override
    public void homeworkDeleted() {
        recreate();
    }

    private void getHomework() {
        presenter.getHomework(((Section) sectionSpinner.getSelectedItem()).getId(), homeworkDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.spinner_class:
                presenter.getSectionList(((Clas) classSpinner.getSelectedItem()).getId(), TeacherDao.getTeacher().getId());
                break;
            case R.id.spinner_section:
                getHomework();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPositiveClick(int from) {
        if (from == 1) {
            if (multiselect_list.size() > 0) {
                presenter.deleteHomework(multiselect_list);
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

    NewHomeworkAdapter.OnItemClickListener mItemListener = new NewHomeworkAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(final Homework homework) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeworkActivity.this);
            View view = getLayoutInflater().inflate(R.layout.homework_dialog, null);
            TextView subjectName = (TextView) view.findViewById(R.id.hw_subject);
            subjectName.setText(homework.getSubjectName());
            final EditText homeworkText = (EditText) view.findViewById(R.id.hw_et);
            builder.setView(view);

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    homework.setHomeworkMessage(homeworkText.getText().toString());
                    presenter.saveHomework(homework);
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();

            /*AlertDialog dialog = builder.create();
            dialog.getWindow().setGravity(Gravity.TOP);
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = 150;
            dialog.getWindow().setAttributes(layoutParams);
            dialog.show();*/
        }
    };
}
