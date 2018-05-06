package com.ccpunion.comrade.page.mian.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.databinding.ItemMainBinding;
import com.ccpunion.comrade.page.mian.activity.StudyActivity;


/**
 * Created by Administrator on 2018/5/6.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_main,parent,false);
        binding.setClick(this);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().study.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyActivity.startActivity(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMainBinding binding ;

        public ItemMainBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemMainBinding binding) {
            this.binding = binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
