package com.example.administrator.montht;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import model.DayModel;

@ContentView(R.layout.activity_task)
public class TaskActivity extends BaseActivity {

    public static final String TAG_DAY_MODEL = "dayModel";

    private DayModel dayModel;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.toolbar_layout)
    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void dealWithIntent() {

        dayModel = (DayModel) getIntent().getSerializableExtra(TAG_DAY_MODEL);
    }

    @Override
    protected void initView() {
        super.initView();

        initToolbar();

    }

    private void initToolbar() {

        setSupportActionBar(toolbar);

        toolbarLayout.setTitle(dayModel == null ? "" : dayModel.getDayDate());
        toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
    }

    @Event(R.id.fab)
    private void initFab(View view) {

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
