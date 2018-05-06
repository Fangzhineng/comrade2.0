package com.ccpunion.comrade;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ccpunion.comrade.base.BaseActivity;
import com.ccpunion.comrade.databinding.ActivityMainBinding;
import com.ccpunion.comrade.page.concentric.ConcentricFragment;
import com.ccpunion.comrade.page.me.MyFragment;
import com.ccpunion.comrade.page.mian.MianFragment;
import com.ccpunion.comrade.page.wish.WishFragment;
import com.ccpunion.comrade.utils.AppManager;


public class MainActivity extends BaseActivity {

    /**
     * ┏┓　　　┏┓
     * ┏┛┻━━━┛┻┓
     * ┃　　　　　　　┃
     * ┃　　　━　　　┃
     * ┃　┳┛　┗┳　┃
     * ┃　　　　　　　┃
     * ┃　　　┻　　　┃
     * ┃　　　　　　　┃
     * ┗━┓　　　┏━┛
     * ┃　　　┃ 神兽保佑
     * ┃　　　┃ 代码无BUG！
     * ┃　　　┗━━━┓
     * ┃　　　　　　　┣┓
     * ┃　　　　　　　┏┛
     * ┗┓┓┏━┳┓┏┛
     * ┃┫┫　┃┫┫
     * ┗┻┛　┗┻┛
     **/
    ActivityMainBinding binding;

    @Override
    public void intiLayout() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void initView() {
        inittabHost();

    }


    public static void startActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context,MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void initData(boolean flag) {
//        SharedPreferencesUtils.putBoolean(this, AppConstant.IS_LOGIN, true);
        //SharedPreferencesUtils.putString(this, AppConstant.COMMUNIST_ID, "");
    }

    private void inittabHost() {
        String texts[] = getResources().getStringArray(R.array.main_tab_text);
        binding.tabHost.setup(this, getSupportFragmentManager(), R.id.container);

        binding.tabHost.addTab(binding.tabHost.newTabSpec(texts[0])
                .setIndicator(getTabItemView(texts[0], 0)), MianFragment.class, null);

        binding.tabHost.addTab(binding.tabHost.newTabSpec(texts[1])
                .setIndicator(getTabItemView(texts[1], 1)), ConcentricFragment.class, null);

        binding.tabHost.addTab(binding.tabHost.newTabSpec(texts[2])
                .setIndicator(getTabItemView(texts[2], 2)), WishFragment.class, null);

        binding.tabHost.addTab(binding.tabHost.newTabSpec(texts[3])
                .setIndicator(getTabItemView(texts[3], 3)), MyFragment.class, null);

        binding.tabHost.getTabWidget().setDividerDrawable(null);

        changedListener();
        binding.tabHost.setCurrentTab(0);
    }

    private View getTabItemView(String title, int position) {
        int[] icons = {R.drawable.icon_tab_home_selector,
                R.drawable.icon_tab_my_selector,
                R.drawable.icon_tab_home_selector,
                R.drawable.icon_tab_my_selector,};


        View view = getLayoutInflater().inflate(R.layout.item_main_bottom, null);
        ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
        TextView tab_text = (TextView) view.findViewById(R.id.tab_text);
        icon.setImageResource(icons[position]);
        tab_text.setText(title);
        return view;
    }


    private void changedListener() {
        binding.tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String str) {
                if (str.equals(getResources().getStringArray(R.array.main_tab_text)[2])) {

                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AppManager.getInstance().outSystem(this,keyCode,event);
        return false;
    }

}
