package com.aanglearning.instructorapp.messagegroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dao.GroupDao;
import com.aanglearning.instructorapp.model.Groups;
import com.aanglearning.instructorapp.model.Message;
import com.aanglearning.instructorapp.usergroup.UserGroupActivity;
import com.aanglearning.instructorapp.util.EndlessRecyclerViewScrollListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageActivity extends AppCompatActivity implements MessageView{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;

    private MessagePresenter presenter;
    private Groups group;
    private ArrayList<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int previousSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new MessagePresenterImpl(this, new MessageInteractorImpl());

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
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void showMessages(ArrayList<Message> messages) {
        this.messages = messages;
        previousSize = messages.size();
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
        previousSize = messages.size();
    }
}
