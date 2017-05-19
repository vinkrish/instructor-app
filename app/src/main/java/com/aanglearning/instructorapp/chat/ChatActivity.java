package com.aanglearning.instructorapp.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.TeacherDao;
import com.aanglearning.instructorapp.messagegroup.ImageUploadActivity;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.util.EndlessRecyclerViewScrollListener;
import com.aanglearning.instructorapp.util.NetworkUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements ChatView, View.OnClickListener{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.new_msg) EditText newMsg;
    @BindView(R.id.enter_msg) ImageView enterMsg;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private long recipientId;
    private ChatPresenter presenter;
    private ArrayList<Message> messages = new ArrayList<>();
    private ChatAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    final static int REQ_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipientId = extras.getLong("recipientId", 0);
        }

        presenter = new ChatPresenterImpl(this, new ChatInteractorImpl());

        setupRecyclerView();

        newMsg.addTextChangedListener(newMsgWatcher);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getMessages("teacher", TeacherDao.getTeacher().getId(), "student", recipientId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ChatAdapter(this, messages);
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getFollowupMessages("teacher", TeacherDao.getTeacher().getId(), "student", recipientId, messages.get(messages.size()-1).getId());
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(0);
                        }
                    }, 100);
                }
            }
        });
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
        Snackbar errorSnackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        errorSnackbar.setAction(R.string.retry, this);
        errorSnackbar.show();
    }

    @Override
    public void onMessageSaved(Message message) {
        adapter.insertDataSet(message);
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void showMessages(ArrayList<Message> messages) {
        this.messages = messages;
        adapter.setDataSet(messages);
    }

    @Override
    public void showFollowupMessages(ArrayList<Message> msgs) {
        adapter.updateDataSet(msgs);
        this.messages = adapter.getDataSet();
    }

    public void uploadImage (View view) {
        Intent intent = new Intent(ChatActivity.this, ImageUploadActivity.class);
        startActivityForResult(intent, REQ_CODE);
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
                    //showSnackbar("Error in sending message");
                }
                break;
            }
        }
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
            showSnackbar("Please enter message");
        } else {
            if (NetworkUtil.isNetworkAvailable(this)) {
                Message message = new Message();
                message.setSenderId(TeacherDao.getTeacher().getId());
                message.setSenderName(TeacherDao.getTeacher().getTeacherName());
                message.setSenderRole("teacher");
                message.setRecipientId(recipientId);
                message.setRecipientRole("student");
                message.setGroupId(0);
                message.setMessageType(messageType);
                message.setImageUrl(imgUrl);
                message.setMessageBody(newMsg.getText().toString());
                message.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
                presenter.saveMessage(message);
            } else {
                showSnackbar("You are offline,check your internet.");
            }
        }
    }

    @Override
    public void onClick(View view) {

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
}
