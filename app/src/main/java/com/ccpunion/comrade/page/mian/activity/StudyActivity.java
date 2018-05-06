package com.ccpunion.comrade.page.mian.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseActivity;
import com.ccpunion.comrade.databinding.ActivityStudyBinding;


/**
 * Created by Administrator on 2018/5/6.
 */

public class StudyActivity extends BaseActivity {

    private ActivityStudyBinding binding;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, StudyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void intiLayout() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(boolean flag) {

    }
}
