package com.beiing.gestruelock;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.KeyEvent;
import android.widget.Toast;

import com.beiing.gestruelock.base.BasePatternActivity;
import com.beiing.gestruelock.base.GestrueManager;
import com.beiing.gestruelock.gestruelock.PatternUtils;
import com.beiing.gestruelock.gestruelock.PatternView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 设置手势密码
 */
public class SetPatternActivity extends BasePatternActivity implements PatternView.OnPatternListener {
    private int minPatternSize;
    private String firstStr;
    private String secStr;
    private boolean firstStep = true;//是否为第一次设置
    protected boolean isReset = false;//是否为重设密码
    private final static int MSG_CLEAR_DELAY = 1001;
    private final static int DELAY_TIME = 600;
    private boolean isFromSysSetting = false;
    private boolean isFromSysReset = false;
    private boolean isScreenLock = false;
    private String ticket = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minPatternSize = getMinPatternSize();
        isReset = getIntent().getBooleanExtra("reset", false);
        messageText.setTextColor(Color.BLACK);
        if (isReset) {
            messageText.setText(getString(R.string.pl_change_new));
        } else {
            messageText.setText(getString(R.string.pl_gesture_set));
        }
        isFromSysSetting = getIntent().getBooleanExtra("isFromSysSetting", false);
        isFromSysReset = getIntent().getBooleanExtra("isFromSysReset", false);
        ticket = getIntent().getStringExtra("ticket");
        isScreenLock = getIntent().getBooleanExtra("isScreenLock", false);
    }

    @Override
    protected void initView() {
        super.initView();
        messageText.setTextColor(Color.BLACK);
        //changepwd_tv.setVisibility(View.INVISIBLE);
        patternView.setOnPatternListener(this);
    }

    @Override
    public boolean handleMessage(Message msg) {

        return super.handleMessage(msg);
    }

    // 设置手势密码：实际是上传到服务器
    // 测试：保存到内存中
    private void setGesturePwd(String pwd) {
        GestrueManager.GESTRUE = pwd;
    }

    @Override
    public void onPatternStart() {
        removeClearPatternRunnable();
        patternView.setDisplayMode(PatternView.DisplayMode.Correct);
    }

    @Override
    public void onPatternCleared() {
        removeClearPatternRunnable();
    }

    @Override
    public void onPatternCellAdded(List<PatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<PatternView.Cell> pattern) {

        if (pattern.size() < 4) {
            messageText.setTextColor(Color.RED);
            messageText.setText(getString(R.string.pl_4least));
            messageText.startAnimation(shakeAnimation);
            patternView.clearPattern();
            return;
        }
        String patStr = PatternUtils.patternToNoString(pattern);

        //1 第一次设置
        if (firstStep) {
            indicator.setPath(patStr);
            firstStep = false;
            String baseStr = Base64.encodeToString(PatternUtils.patternToNoString(pattern).getBytes(), Base64.NO_WRAP);
//            firstStr = MD5Util.stringToMD5(baseStr);
            firstStr = baseStr;// 暂不考虑安全性
            handler.sendEmptyMessageDelayed(MSG_CLEAR_DELAY, DELAY_TIME);

            patternView.clearPattern();//清空pattern
            messageText.setText(getString(R.string.pl_change_new2));
            messageText.setTextColor(Color.BLACK);
            //2 第二次设置
        } else {
            String baseStr2 = Base64.encodeToString(PatternUtils.patternToNoString(pattern).getBytes(), Base64.NO_WRAP);
//            secStr = MD5Util.stringToMD5(baseStr2);
            secStr = baseStr2; // 暂不考虑安全性
            // 2.1和第一次手势相同
            if (firstStr.equals(secStr)) {
                patternView.setDisplayMode(PatternView.DisplayMode.Correct);
                firstStep = true;
                Toast.makeText(getApplicationContext(), "手势密码设置成功" + secStr,Toast.LENGTH_SHORT).show();
                finish();
                if (isFromSysReset) {
//                    resetGesturePwdPhone(secStr);
                    setGesturePwd(secStr);
                    return;
                }

                //2.1.1重置密码
                if (isReset) {
//                    String oldPwd = appDataSP.getStrValue("oldPwd", "");
//                    resetGesturePwd(oldPwd, secStr);
//                    appDataSP.putStrValue(ConfirmPatternActivity.GestureWrongTime, "");//重置密码后，错误累计清空
                    // 2.1.2初次设置密码
                } else {
                    setGesturePwd(secStr);
                }
                //2.2和第一次手势不同
            } else {
                messageText.setTextColor(Color.RED);
                messageText.setText(getString(R.string.pl_different));
                messageText.startAnimation(shakeAnimation);
                patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
                postClearPatternRunnable();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected int getMinPatternSize() {
        return 4;
    }

}
