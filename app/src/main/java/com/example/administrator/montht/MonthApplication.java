package com.example.administrator.montht;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Administrator on 2017/8/13.
 * 基本Application
 */

public class MonthApplication extends Application {

    private static MonthApplication myApplication;
    private DbManager.DaoConfig daoConfig;

    //单例模式获取唯一的myApplication
    public static MonthApplication getInstance() {

        synchronized (MonthApplication.class) {

            if (myApplication == null) {
                myApplication = new MonthApplication();
            }
        }
        return myApplication;
    }

    public DbManager.DaoConfig getDaoConfig() {

        return daoConfig;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        daoConfig = new DbManager.DaoConfig()
                .setDbName("monthTask.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {


                    }
                });
    }
}
