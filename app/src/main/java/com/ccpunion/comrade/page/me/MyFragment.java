package com.ccpunion.comrade.page.me;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseFragment;
import com.ccpunion.comrade.databinding.FragmentMeBinding;


/**
 * Created by Administrator on 2018/4/24.
 */

public class MyFragment extends BaseFragment {
    FragmentMeBinding binding;


    @Override
    public void initData(boolean flag) {

    }

    @Override
    public ViewDataBinding getLayoutResId(ViewGroup container) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_me, container, false);
        return binding;
    }

    @Override
    public void initView(View view) {

    }


}
