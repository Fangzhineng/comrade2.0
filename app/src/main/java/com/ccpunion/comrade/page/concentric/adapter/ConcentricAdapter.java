package com.ccpunion.comrade.page.concentric.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.databinding.ItemTestBinding;
import com.ccpunion.comrade.page.concentric.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ConcentricAdapter extends RecyclerView.Adapter<ConcentricAdapter.ViewHolder> {

    private Context context;

    private List<TestBean.BodyBean> list = new ArrayList<>();

    public ConcentricAdapter(Context context, List<TestBean.BodyBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTestBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_test, parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemTestBinding binding = holder.getBinding();
        binding.old.setText(list.get(position).getUid());
        binding.name.setText(list.get(position).getName());
        binding.sex.setText(list.get(position).getSex());
       // binding.headerIv.loadImage(list.get(position).getHeadImages(),0);

        // GlideUtils.loadImageView(context, list.get(position).getHeadImages(), binding.headerIv);
        binding.executePendingBindings(); //防止闪烁
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onRefreshData(List<TestBean.BodyBean> mlist) {
        list = mlist;
        notifyDataSetChanged();
    }

    public void onLoadMoreData(List<TestBean.BodyBean> mlist) {
        list.addAll(mlist);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTestBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ItemTestBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemTestBinding binding) {
            this.binding = binding;
        }


    }
}
