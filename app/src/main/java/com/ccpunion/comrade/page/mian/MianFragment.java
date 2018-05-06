package com.ccpunion.comrade.page.mian;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseFragment;
import com.ccpunion.comrade.databinding.FragmentMainBinding;
import com.ccpunion.comrade.page.mian.adapter.MainAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;


/**
 * Created by Administrator on 2018/5/6.
 */

public class MianFragment extends BaseFragment implements XRecyclerView.LoadingListener {
    FragmentMainBinding binding;
    private MainAdapter adapter;

    @Override
    public ViewDataBinding getLayoutResId(ViewGroup container) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_main,container,false);
        return binding;
    }

    @Override
    public void initView(View view) {
        binding.mainRv.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        binding.mainRv.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        binding.mainRv.setArrowImageView(R.mipmap.pull_down_arrow);
        binding.mainRv.setLoadingListener(this);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.mainRv.setAdapter(adapter = new MainAdapter(context));
    }

    @Override
    public void initData(boolean flag) {

    }

    @Override
    public void onRefresh() {
        binding.mainRv.loadMoreComplete();
        binding.mainRv.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        binding.mainRv.loadMoreComplete();
        binding.mainRv.refreshComplete();
    }
}
