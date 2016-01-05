package com.beiing.gestruelock.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

//import com.kakao.finance.util.SharedPreferencesUtils;
//import com.kakao.finance.view.HeadBar;
//import com.top.main.baseplatform.view.LoadDialog;
//import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;

public abstract class BaseNewActivity extends FragmentActivity implements OnClickListener, Handler.Callback {
    public  Context context;
    public  Activity activity;
    public  LayoutInflater inflater;
    public  int widthScreen;// 屏幕宽
    public  int heightScreen;// 屏幕高
    public Handler handler;
//    protected LoadDialog loadDialog;
//    public SharedPreferencesUtils appDataSP;
//    public HeadBar headBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findById();
    }

    private void findById() {
        context = this;
        inflater = LayoutInflater.from(context);
        WindowManager manager = getWindowManager();
        Display display = manager.getDefaultDisplay();
        widthScreen = display.getWidth();
        heightScreen = display.getHeight();
        handler = new Handler(this);
//        appDataSP = SharedPreferencesUtils.getInstance(context);
        init();

    }

    private void init() {
        loadViewLayout();
        setOverflowShowingAlways();
        initView();
        initData();
        setListener();
    }

    /**
     * 功能描述：<加载页面View>
     */
    protected abstract void loadViewLayout();
    /**
     * 功能描述：<初始化控件>
     */
    protected abstract void initView();
    /**
     * 功能描述：<初始化数据>
     */
    protected abstract void initData();


    /**
     * 功能描述：<设置监听>
     */
    protected abstract void setListener();



    public void DisplayToast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
