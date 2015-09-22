package com.nama_gatsuo.dreamplan;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import com.nama_gatsuo.dreamplan.model.Project;

public class ShowProjectActivity extends ActionBarActivity {

    private Project project;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private boolean isPortrait = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_project);

        // IntentでのProjectIDの受け取り
        project = (Project) getIntent().getSerializableExtra("Project");

        // Fragment表示のための準備
        manager = getFragmentManager();

        // Set ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(project.getName());
    }

//    boolean isGantt = true;
//
//    private void init() {
//        transaction = manager.beginTransaction();
//        // 縦置きか横置きかで表示するFragmentを決定
//        if (!isGantt) {
//            // TaskListFragmentを表示
//            TaskListFragment tlf = (TaskListFragment) manager.findFragmentByTag("list");
//            if (tlf == null) {
//                tlf = new TaskListFragment();
//            }
//            transaction.add(R.id.container, tlf, "list");
//        } else {
//            // ActionBarを非表示にする
//            getSupportActionBar().hide();
//
//            // TaskGanttFragmentを表示
//            TaskGanttFragment tgf = (TaskGanttFragment) manager.findFragmentByTag("gantt");
//            if (tgf == null) {
//                tgf = new TaskGanttFragment();
//            }
//            transaction.add(R.id.container, tgf, "gantt");
//        }
//        transaction.commit();
//    }
//
//    void reset() {
//        transaction = manager.beginTransaction();
//        if (!isGantt) {
//            transaction.remove(manager.findFragmentByTag("list"));
//        } else {
//            transaction.remove(manager.findFragmentByTag("gantt"));
//        }
//        transaction.commit();
//    }

    @Override
    protected void onResume() {
        super.onResume();
//        init();
        transaction = manager.beginTransaction();

        // 縦置きか横置きかで表示するFragmentを決定
        Configuration config = getResources().getConfiguration();
        if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            // TaskListFragmentを表示
            TaskListFragment tlf = (TaskListFragment) manager.findFragmentByTag("list");
            if (tlf == null) {
                tlf = new TaskListFragment();
            }
            transaction.add(R.id.container, tlf, "list");
            isPortrait = true;
        } else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // ActionBarを非表示にする
            getSupportActionBar().hide();

            // TaskGanttFragmentを表示
            TaskGanttFragment tgf = (TaskGanttFragment) manager.findFragmentByTag("gantt");
            if (tgf == null) {
                tgf = new TaskGanttFragment();
            }
            transaction.add(R.id.container, tgf, "gantt");
            isPortrait = false;
        }
        transaction.commit();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (R.id.button == id) {
//            isGantt = !isGantt;
//            reset();
//            init();
            if (isPortrait) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    @Override
    protected void onPause() {
        // onPause時にFragmentを削除
        super.onPause();
//        reset();
        transaction = manager.beginTransaction();
        if (isPortrait) {
            transaction.remove(manager.findFragmentByTag("list"));
            isPortrait = false;
        } else {
            transaction.remove(manager.findFragmentByTag("gantt"));
            isPortrait = true;
        }
        transaction.commit();
    }

    public Project getProject() {
        if (project == null) {
            Project newproject = new Project();
            newproject.setProjectID(1);
            return newproject;
        } else {
            return this.project;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}