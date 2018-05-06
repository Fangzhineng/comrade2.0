package com.ccpunion.comrade.page.concentric;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseFragment;
import com.ccpunion.comrade.databinding.FragmentConcentricBinding;
import com.ccpunion.comrade.http.ResultCallBack;
import com.ccpunion.comrade.page.concentric.adapter.ConcentricAdapter;
import com.ccpunion.comrade.page.concentric.bean.TestBean;
import com.ccpunion.comrade.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ConcentricFragment extends BaseFragment implements XRecyclerView.LoadingListener, ResultCallBack {
    FragmentConcentricBinding binding;

    private ConcentricAdapter adapter;
    private List<TestBean.BodyBean> mlist = new ArrayList<>();

    @Override
    public ViewDataBinding getLayoutResId(ViewGroup container) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_concentric, container, false);
        return binding;
    }

    @Override
    public void initView(View view) {
        binding.concentricRv.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        binding.concentricRv.setLoadingMoreProgressStyle(ProgressStyle.BallClipRotate);
        binding.concentricRv.setArrowImageView(R.mipmap.pull_down_arrow);
        binding.concentricRv.setLoadingListener(this);
        binding.concentricRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.concentricRv.setAdapter(adapter = new ConcentricAdapter(context, mlist));
    }

    @Override
    public void initData(boolean flag) {
//        OkHttpUtils.postAsync(context
//                , URLConstant.TEXT
//                , new RequestParams(context).getParams()
//                , this, flag, 1);
    }

    @Override
    public void onRefresh() {
        initData(false);

    }

    @Override
    public void onLoadMore() {
        initData(false);
    }


    @Override
    public void onSuccess(String response, int flag) {
        binding.concentricRv.loadMoreComplete();
        binding.concentricRv.refreshComplete();
        if (flag == 1) {
            TestBean bean = JSON.parseObject(response, TestBean.class);
            if (bean.getCode().equals("0")) {
                mlist.clear();
                mlist.addAll(bean.getBody());
                adapter.onRefreshData(mlist);
            } else {
                ToastUtils.showToast(context,bean.getMsg());
            }
        }
    }

    @Override
    public void onFailure(Request request, IOException e, int flag) {
        binding.concentricRv.loadMoreComplete();
        binding.concentricRv.refreshComplete();
    }
}
