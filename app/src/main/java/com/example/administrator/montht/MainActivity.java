package com.example.administrator.montht;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.MainAdapter;
import helper.CardScaleHelper;
import model.DayModel;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private DbManager dbManager = x.getDb(MonthApplication.getInstance().getDaoConfig());
    private ArrayList<DayModel> models;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.rv_main_act)
    private RecyclerView recyclerView;

    protected void initView() {

        initToolbar();

        initRecyclerView();
    }

    private void initToolbar() {

        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        MainAdapter adapter = new MainAdapter(this);
        recyclerView.setAdapter(adapter);

        CardScaleHelper helper = new CardScaleHelper();
        helper.attachToRecyclerView(recyclerView);

        models = findAllDayModels();
        adapter.setModels(models);

        adapter.setOnMainItemClickListener(new MainAdapter.OnMainItemClickListener() {
            @Override
            public void OnMainItemClick(View view, DayModel model) {

                if (model == null)
                    openActivity(TaskActivity.class, view);
                else {

                    Intent intent = new Intent(mActivity, TaskActivity.class);
                    intent.putExtra(TaskActivity.TAG_DAY_MODEL, model);
                    openActivity(intent, view);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {

            if (models.isEmpty()) {

                showToast("任务已经清空");
                changeAppIconName();
            } else {

                showToast("clean");
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取数据库存储的数据
     */
    private ArrayList<DayModel> findAllDayModels() {

        ArrayList<DayModel> targetList = new ArrayList<>();

        try {

            List<DayModel> models = dbManager.selector(DayModel.class).findAll();
            if (models != null)
                targetList.addAll(models);
        } catch (DbException e) {
            e.printStackTrace();
        }

        return targetList;
    }

    /**
     * 更改图标appName
     * */
    private void changeAppIconName(){

        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(getComponentName(),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        pm.setComponentEnabledSetting(new ComponentName(this, "com.example.administrator.montht.MainActivityAlias"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
