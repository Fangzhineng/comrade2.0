package com.ccpunion.comrade.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ccpunion.comrade.R;
import com.ccpunion.comrade.utils.AppManager;
import com.ccpunion.comrade.utils.StatusBarCompat;

/**
 * Created by Administrator on 2018/4/24.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /***是否显示标题栏*/
    private boolean isshowtitle = true;
    /***是否显示标题栏*/
    private boolean isshowstate = true;
    /***封装toast对象**/
    private static Toast toast;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppManager.getInstance().addActivity(this);


        if (!isshowtitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (isshowstate) {
            //设置全局状态栏颜色
            StatusBarCompat.compat(this);
        }

        intiLayout();
        //初始化控件
        initView();
        //设置数据
        initData(true);
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract void intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData(boolean flag);

    /**
     * 是否设置标题栏
     *
     * @return
     */
    public void setTitle(boolean ishow) {
        isshowtitle = ishow;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate = ishow;
    }


    public void isGoneTitle(boolean istrue) {
        RelativeLayout rl_back_color = (RelativeLayout) findViewById(R.id.rl_back_color);
        if (istrue) {
            rl_back_color.setVisibility(View.GONE);
        } else {
            rl_back_color.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 返回
     */
    public void back() {
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    /**
     * 设置title文字
     */
    public void setTitleName(int name) {
        TextView mTextViewName = (TextView) findViewById(R.id.tv_title_name);
        mTextViewName.setText(name);
    }

    /**
     * 设置title文字
     */
    public void setTitleName(String name) {
        TextView mTextViewName = (TextView) findViewById(R.id.tv_title_name);
        mTextViewName.setText(name);
    }

    /**
     * 是否显示左边
     */
    public void leftGone() {
        findViewById(R.id.ll_back).setVisibility(View.GONE);
    }


    /**
     * 是否显示右边
     *
     * @param isture
     */
    public void rightIsGong(boolean isture) {
        LinearLayout mLinearLayoutRight = (LinearLayout) findViewById(R.id.ll_right);
        if (isture) {
            mLinearLayoutRight.setVisibility(View.VISIBLE);
        } else {
            mLinearLayoutRight.setVisibility(View.GONE);
        }
    }


    /**
     * 设置监听
     *
     * @param linster
     */
    public void rightLinster(View.OnClickListener linster) {
        LinearLayout mLinearLayoutRight = (LinearLayout) findViewById(R.id.ll_right);
        mLinearLayoutRight.setOnClickListener(linster);
    }

    /**
     * 设置右边的文字和颜色
     *
     * @param color
     */
    public void rightText(int color, String text) {
        TextView mTextViewRight = (TextView) findViewById(R.id.tv_right);
        mTextViewRight.setTextColor(color);
        mTextViewRight.setText(text);
    }

    /**
     * 设置右边的文字和颜色
     *
     * @param img
     */
    public void rightImg(int img) {
        ImageView imgage = (ImageView) findViewById(R.id.iv_right_img);
        imgage.setBackgroundResource(img);
    }
}