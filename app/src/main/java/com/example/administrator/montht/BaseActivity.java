package com.example.administrator.montht;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.xutils.x;

import top.wefor.circularanim.CircularAnim;

/**
 * Created by Administrator on 2017/8/13.
 * 基本Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        mActivity = this;
        mContext = this;

        dealWithIntent();

        initView();
    }

    protected void dealWithIntent() {

    }

    protected void initView() {

    }

    protected void openActivity(final Class clazz, View view) {

        CircularAnim.fullActivity(this, view)
                .colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(new Intent(BaseActivity.this, clazz));
                    }
                });
    }

    protected void openActivity(final Intent intent, View view) {

        CircularAnim.fullActivity(this, view)
                .colorOrImageRes(R.color.colorAccent)
                .go(new CircularAnim.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd() {

                        startActivity(intent);
                    }
                });
    }

    protected void showToast(int resId) {

        showToast(getResources().getString(resId));
    }

    protected void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
