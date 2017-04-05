package com.aanglearning.instructorapp.usergroup;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 04-04-2017.
 */

public class StudentMemberAdapter extends RecyclerView.Adapter<StudentMemberAdapter.ViewHolder> {
    private ArrayList<GroupStudent> items;

    StudentMemberAdapter(ArrayList<GroupStudent> items) {
        this.items = items;
    }

    public ArrayList<GroupStudent> getDataSet() {
        return items;
    }

    @UiThread
    public void setDataSet(ArrayList<GroupStudent> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public StudentMemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_member_item, parent, false);
        return new StudentMemberAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentMemberAdapter.ViewHolder holder, int position) {
        final GroupStudent groupStudent = items.get(position);
        holder.name.setText(groupStudent.getStudentName());
        holder.isSelected.setChecked(groupStudent.isSelected());
        holder.isSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                groupStudent.setSelected(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.checkBox) CheckBox isSelected;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
