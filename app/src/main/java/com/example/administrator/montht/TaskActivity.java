package com.example.administrator.montht;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;

import model.DayModel;
import utils.DateUtil;

@ContentView(R.layout.activity_task)
public class TaskActivity extends BaseActivity {

    public static final String TAG_DAY_MODEL = "dayModel";

    private DayModel dayModel;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.toolbar_layout)
    private CollapsingToolbarLayout toolbarLayout;

    @ViewInject(R.id.tv_day_surplus)
    private TextSwitcher tsDaysSurplus;

    @ViewInject(R.id.tv_start_value)
    private TextView tvDateStart;

    @ViewInject(R.id.tv_end_value)
    private TextView tvDateEnd;

    /**
     * 项目的开始日期、结束日期
     * */
    private String sTimeStart, sTimeEnd;

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

        initTextSwitcher();

    }

    private void initToolbar() {

        setSupportActionBar(toolbar);

        toolbarLayout.setTitle(dayModel == null ? "" : dayModel.getDayDate());
        toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

    }

    private void initTextSwitcher() {

        tsDaysSurplus.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return createTextView();
            }
        });
    }

    /**
     * TextSwitch内创建的index指示器的TextView
     */
    private TextView createTextView() {

        TextView tv = new TextView(this);
        tv.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        tv.setTextSize(28);
        tv.setTextColor(Color.DKGRAY);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }

    @Event(R.id.fab)
    private void initFab(View view) {

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Event(R.id.tv_start_value)
    private void selectDataStart(View view) {

        popDatePickerDialog(true);
    }

    @Event(R.id.tv_end_value)
    private void selectDataEnd(View view) {

        popDatePickerDialog(false);
    }


    /**
     * 弹出时间选择Dialog
     */
    private void popDatePickerDialog(final boolean isStart) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthBefore = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final String sToday = year + "/" + (monthBefore + 1) + "/" + day;

        final DateUtil dateUtil = DateUtil.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String time = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                        if (isStart) {

                            //判断日期是否小于当前日
                            long currentToday = dateUtil.getTimeInMillisFromString(sToday);
                            long selectDate = dateUtil.getTimeInMillisFromString(time);
                            long selectEnd = dateUtil.getTimeInMillisFromString(sTimeEnd);

                            if (currentToday > selectDate) {

                                tvDateStart.setHint(getResources().getString(R.string.target_time_after_current));
                            } else {

                                //是否已经选择了结束日
                                if (TextUtils.isEmpty(sTimeEnd) || selectDate < selectEnd) {

                                    sTimeStart = time;
                                    tvDateStart.setText(time);
                                } else {

                                    doShockAnimation(tvDateStart);
                                }
                            }
                        } else {

                            if (TextUtils.isEmpty(sTimeStart)) {

                                tvDateStart.setHint(getResources().getString(R.string.target_time_start_first));
                            } else {

                                sTimeEnd = time;
                                if (dateUtil.getTimeInMillisFromString(sTimeEnd) -
                                        dateUtil.getTimeInMillisFromString(sTimeStart) > 0) {

                                    tvDateEnd.setText(sTimeEnd);
                                    int currentDays = dateUtil.getCurrentDays(sTimeStart, sTimeEnd);
                                    tsDaysSurplus.setText(String.valueOf(currentDays));

                                } else {

                                    tvDateEnd.setHint(
                                            getResources().getString(R.string.target_time_after_must));
                                    doShockAnimation(tvDateEnd);
                                }
                            }
                        }
                    }
                }, year, monthBefore, day);

        dialog.show();
    }

    /**
     * View的横向震动效果
     */
    private void doShockAnimation(View target) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "translationX",
                0f, -24f, 24f, -24f, 24f, 0f);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
