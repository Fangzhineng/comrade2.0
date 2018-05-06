package com.ccpunion.comrade.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Administrator on 2018/4/24.
 */

public abstract class BaseFragment extends Fragment {

    public ViewDataBinding binding;

    public final String TAG = getClass().getSimpleName();

    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();

        binding = getLayoutResId(container);

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) binding.getRoot().getParent();
        if (parent != null) {
            Log.e(TAG + " null", String.valueOf(binding.getRoot()));
            parent.removeView(binding.getRoot());
        }

        Log.e(TAG, String.valueOf(binding.getRoot()));

        initView(binding.getRoot());

        initData(false);

        return binding.getRoot();
    }


    /***
     * 获取布局文件
     */
    public abstract ViewDataBinding getLayoutResId(ViewGroup container);

    /***
     * 初始化控件
     */
    public abstract void initView(View view);

    /**
     * 加载数据
     */
    public abstract void initData(boolean flag);


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
