package com.aanglearning.instructorapp.messagegroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.dao.MessageDao;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.usergroup.UserGroupActivity;
import com.aanglearning.instructorapp.util.EndlessRecyclerViewScrollListener;
import com.aanglearning.instructorapp.util.FloatingActionButton;
import com.aanglearning.instructorapp.util.NetworkUtil;
import com.aanglearning.instructorapp.util.PermissionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements MessageView, View.OnKeyListener,
        ActivityCompat.OnRequestPermissionsResultCallback{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.refreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.new_msg_layout) LinearLayout newMsgLayout;
    @BindView(R.id.new_msg) EditText newMsg;
    @BindView(R.id.enter_msg) ImageView enterMsg;

    private static final String TAG = "MessageActivity";
    private MessagePresenter presenter;
    private Groups group;
    private MessageAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private FloatingActionButton fabButton;

    final static int REQ_CODE = 999;
    private static final int WRITE_STORAGE_PERMISSION = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            group = new Groups();
            group.setId(extras.getLong("groupId"));
            group.setName(extras.getString("groupName"));
        }
        getSupportActionBar().setTitle(group.getName());

        presenter = new MessagePresenterImpl(this, new MessageInteractorImpl());

        setupRecyclerView();

        refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBackupMessages();
            }
        });

        setupFab();

        newMsg.setOnKeyListener(this);
        newMsg.addTextChangedListener(newMsgWatcher);

        if(PermissionUtil.isStoragePermissionGranted(this, WRITE_STORAGE_PERMISSION)) {
            getBackupMessages();
        }
    }

    private void getBackupMessages() {
        List<Message> messages = MessageDao.getGroupMessages(group.getId());
        adapter.setDataSet(messages);
        if(NetworkUtil.isNetworkAvailable(this)) {
            if(messages.size() == 0) {
                presenter.getMessages(group.getId());
            } else {
                presenter.getRecentMessages(group.getId(), adapter.getDataSet().get(0).getId());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            getBackupMessages();
        } else {
            showSnackbar("Permission has been denied");
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MessageAdapter(this, new ArrayList<Message>(0));
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(NetworkUtil.isNetworkAvailable(MessageActivity.this)) {
                    presenter.getFollowupMessages(group.getId(), adapter.getDataSet().get(adapter.getDataSet().size()-1).getId());
                } else {
                    List<Message> messages = MessageDao.getGroupMessagesFromId(group.getId(),
                            adapter.getDataSet().get(adapter.getDataSet().size()-1).getId());
                    adapter.updateDataSet(messages);
                }
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
                Intent intent = new Intent(this, UserGroupActivity.class);
                intent.putExtra("groupId", group.getId());
                intent.putExtra("groupName", group.getName());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        showSnackbar(message);
    }

    @Override
    public void onMessageSaved(Message message) {
        newMsg.setText("");
        adapter.insertDataSet(message);
        recyclerView.smoothScrollToPosition(0);
        backupMessages(Collections.singletonList(message));
    }

    @Override
    public void showRecentMessages(List<Message> messages) {
        adapter.insertDataSet(messages);
        recyclerView.smoothScrollToPosition(0);
        backupMessages(messages);
    }

    @Override
    public void showMessages(List<Message> messages) {
        adapter.setDataSet(messages);
        backupMessages(messages);
    }

    @Override
    public void showFollowupMessages(List<Message> messages) {
        adapter.updateDataSet(messages);
        backupMessages(messages);
    }

    private void backupMessages(final List<Message> messages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MessageDao.insertGroupMessages(messages);
            }
        }).start();
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
        if(newMsg.getText().toString().trim().isEmpty() && imgUrl.equals("")) {
            showError("Please enter message");
        } else {
            if (NetworkUtil.isNetworkAvailable(this)) {
                Message message = new Message();
                message.setSenderId(TeacherDao.getTeacher().getId());
                message.setSenderName(TeacherDao.getTeacher().getName());
                message.setSenderRole("teacher");
                message.setGroupId(group.getId());
                message.setRecipientRole("group");
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
                    sendMessage("image", imgName);
                } else {
                    hideProgress();
                    showSnackbar("Canceled Image Upload");
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
