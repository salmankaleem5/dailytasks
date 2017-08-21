package com.salmankaleem.dailytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by Salman on 8/17/2017.
 */

public class NewTaskActivity extends AppCompatActivity {
    public Intent returnIntent;
    public Bundle strings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newtask);

        final TextInputLayout titleWrapper = (TextInputLayout) findViewById(R.id.titleWrapper);
        final TextInputLayout descWrapper = (TextInputLayout) findViewById(R.id.descWrapper);

        returnIntent = new Intent();
        strings = new Bundle();

        Bundle extras = getIntent().getExtras();
        if( extras != null ){
            String receivedTaskTitle = extras.getString("TASK_TITLE");
            String receivedTaskDesc = extras.getString("TASK_DESC");
            titleWrapper.getEditText().setText(receivedTaskTitle);
            descWrapper.getEditText().setText(receivedTaskDesc);

            strings.putInt("TASK_INDEX", extras.getInt("TASK_INDEX"));
            //Log.d("passed_task_title", s);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save_task_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitle = titleWrapper.getEditText().getText().toString();
                String taskDesc = descWrapper.getEditText().getText().toString();

                if( taskTitle.trim().isEmpty() ){
                    titleWrapper.setError("Please enter a valid task title");
                } else {
                    titleWrapper.setErrorEnabled(false);

                    strings.putString("TASK_TITLE", taskTitle);
                    strings.putString("TASK_DESC", taskDesc);
                    returnIntent.putExtras(strings);

                    setResult(RESULT_OK, returnIntent);
                    finish();
                }

            }
        });

    }
}
