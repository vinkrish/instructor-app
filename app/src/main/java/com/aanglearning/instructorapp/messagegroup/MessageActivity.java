package com.aanglearning.instructorapp.messagegroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.usergroup.UserGroupActivity;
import com.aanglearning.instructorapp.util.EndlessRecyclerViewScrollListener;
import com.aanglearning.instructorapp.util.FloatingActionButton;
import com.aanglearning.instructorapp.util.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements MessageView, View.OnKeyListener{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.new_msg_layout) LinearLayout newMsgLayout;
    @BindView(R.id.new_msg) EditText newMsg;
    @BindView(R.id.enter_msg) ImageView enterMsg;

    private static final String TAG = "MessageActivity";
    private MessagePresenter presenter;
    private Groups group;
    private ArrayList<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private FloatingActionButton fabButton;
    final static int REQ_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MessagePresenterImpl(this, new MessageInteractorImpl());

        setupRecyclerView();

        setupFab();

        newMsg.setOnKeyListener(this);
        newMsg.addTextChangedListener(newMsgWatcher);
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MessageAdapter(this, messages);
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getFollowupMessages(group.getId(), messages.get(messages.size()-1).getId());
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void setupFab() {
        fabButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.drawable.ic_add))
                .withButtonColor(ContextCompat.getColor(this, R.color.colorAccent))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabButton.hideFloatingActionButton();
                newMsgLayout.setVisibility(View.VISIBLE);
                newMsg.requestFocus();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(newMsg, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(newMsgLayout.getVisibility() == View.VISIBLE) {
            newMsgLayout.setVisibility(View.GONE);
            fabButton.showFloatingActionButton();
            newMsg.setText("");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_group_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.disclaimer:
                startActivity(new Intent(this, UserGroupActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        group = GroupDao.getGroup();
        //groupName.setText(group.getName());
        presenter.getMessages(group.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
    public void onMessageSaved(Message message) {
        adapter.insertDataSet(message);
    }

    @Override
    public void showMessages(ArrayList<Message> messages) {
        this.messages = messages;
        adapter.setDataSet(messages);
    }

    @Override
    public void showFollowupMessages(ArrayList<Message> msgs) {
        /*ArrayList<Message> oldMessages = messages;
        this.messages = new ArrayList<>();
        this.messages.addAll(oldMessages);
        this.messages.addAll(msgs);
        previousSize = messages.size();*/
        //adapter.notifyItemRangeInserted(previousSize, messages.size() - 1);
        //adapter.setDataSet(messages);
        adapter.updateDataSet(msgs);
        this.messages = adapter.getDataSet();
    }

    public void uploadImage (View view) {
        Intent intent = new Intent(MessageActivity.this, ImageUploadActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }

    public void newMsgSendListener (View view) {
        sendMessage("text", "");
        newMsg.setText("");
    }

    private void sendMessage(String messageType, String imgUrl) {
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        if(newMsg.getText().toString().trim().isEmpty()) {
            showError("Please enter message");
        } else {
            if (NetworkUtil.isNetworkAvailable(this)) {
                Message message = new Message();
                message.setSenderId(TeacherDao.getTeacher().getId());
                message.setSenderName(TeacherDao.getTeacher().getTeacherName());
                message.setSenderRole("teacher");
                message.setGroupId(group.getId());
                message.setMessageType(messageType);
                message.setImageUrl(imgUrl);
                message.setMessageBody(newMsg.getText().toString());
                message.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
                presenter.saveMessage(message);
            } else {
                showError("You are offline,check your internet.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (REQ_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    String msg = data.getStringExtra("text");
                    newMsg.setText(msg);
                    String imgName = data.getStringExtra("imgName");
                    //Log.e(TAG,  msg + " Result from child activity " + imgName);
                    sendMessage("image", imgName);
                } else {
                    hideProgress();
                    //showSnackbar("Error in sending message");
                }
                break;
            }
        }
    }

    private final TextWatcher newMsgWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (newMsg.getText().toString().equals("")) {
            } else {
                enterMsg.setImageResource(R.drawable.ic_chat_send);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==0){
                enterMsg.setImageResource(R.drawable.ic_chat_send);
            }else{
                enterMsg.setImageResource(R.drawable.ic_chat_send_active);
            }
        }
    };

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER){
            sendMessage("text", "");
        }
        return false;
    }
}
