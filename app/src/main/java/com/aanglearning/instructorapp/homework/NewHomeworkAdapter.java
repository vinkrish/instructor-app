package com.aanglearning.instructorapp.homework;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aanglearning.instructorapp.R;
import com.aanglearning.instructorapp.dashboard.GroupAdapter;
import com.aanglearning.instructorapp.model.Homework;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vinay on 24-04-2017.
 */

public class NewHomeworkAdapter extends RecyclerView.Adapter<NewHomeworkAdapter.ViewHolder>  {

    private final List<Homework> items;
    private final NewHomeworkAdapter.OnItemClickListener listener;

    interface OnItemClickListener {
        void onItemClick(Homework homework);
    }

    NewHomeworkAdapter(List<Homework> items, NewHomeworkAdapter.OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        return new NewHomeworkAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.group_name)
        TextView subjectName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(final Homework homework, final NewHomeworkAdapter.OnItemClickListener listener) {
            subjectName.setText(homework.getSubjectName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(homework);
                }
            });
        }

    }

}
