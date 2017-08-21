package com.salmankaleem.dailytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    static final int NEW_TASK_REQUEST = 1;  // The request code
    static final int EDIT_TASK_REQUEST = 2;
    private TaskListAdapter adapter;
    private ArrayList<Task> arrayofTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTaskIntent = new Intent(MainActivity.this,NewTaskActivity.class);
                startActivityForResult(newTaskIntent, NEW_TASK_REQUEST);
            }
        });

        arrayofTasks = new ArrayList<>();   // Replace this with a retrieval from a saved file
        adapter = new TaskListAdapter(this, arrayofTasks);

        ListView listView = (ListView) findViewById(R.id.taskListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?>adapter, View v, int position, long arg3){
                Intent editTaskIntent = new Intent(MainActivity.this,NewTaskActivity.class);
                Bundle strings = new Bundle();
                Task t = (Task) adapter.getItemAtPosition(position);
                strings.putString("TASK_TITLE", t.title);
                strings.putString("TASK_DESC", t.description);
                strings.putInt("TASK_INDEX", position);
                editTaskIntent.putExtras(strings);

                startActivityForResult(editTaskIntent, EDIT_TASK_REQUEST);

                Log.d("TASKITEM", t.title);
            }
        });
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?>adapter, View v, int position, long arg3){
                Task t = (Task) adapter.getItemAtPosition(position);

                Log.d("LONGCLICK_TASK", t.title + t.description);

                return true;
            }
        });*/
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == NEW_TASK_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                String task_title = extras.getString("TASK_TITLE");
                String task_desc = extras.getString("TASK_DESC");
                this.adapter.add( new Task(task_title, task_desc) );
            }
        } else if( requestCode == EDIT_TASK_REQUEST ){
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                String task_title = extras.getString("TASK_TITLE");
                String task_desc = extras.getString("TASK_DESC");
                int task_index = extras.getInt("TASK_INDEX");

                arrayofTasks.set(task_index, new Task(task_title, task_desc));
                adapter.notifyDataSetChanged();

                Log.d("EDITED_TASK_TITLE", task_title);
                Log.d("EDITED_TASK_DESC", task_desc);
                Log.d("EDITED_TASK_INDEX", Integer.toString(task_index));
            }
        }
    }
}
