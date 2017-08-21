package com.salmankaleem.dailytasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Salman on 8/17/2017.
 */

public class TaskListAdapter extends ArrayAdapter<Task> {
    public TaskListAdapter(Context context, List<Task> tasks){
        super(context, R.layout.task_item, tasks);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Task t = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        TextView taskName = (TextView) convertView.findViewById(R.id.taskName);

        taskName.setText(t.title);

        return convertView;
    }
}

class Task {
    String title;
    String description;

    public Task( String title, String description ){
        this.title = title;
        this.description = description;
    }
}