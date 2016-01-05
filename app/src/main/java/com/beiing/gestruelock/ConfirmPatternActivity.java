package com.beiing.gestruelock;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.beiing.gestruelock.base.BasePatternActivity;
import com.beiing.gestruelock.base.GestrueManager;
import com.beiing.gestruelock.gestruelock.PatternUtils;
import com.beiing.gestruelock.gestruelock.PatternView;

import java.util.List;

/**
 * 手势密码校验
 *
 * @author zgf
 * @date 2015-05-22
 */
public class ConfirmPatternActivity extends BasePatternActivity
        implements PatternView.OnPatternListener {

    private static final String KEY_NUM_FAILED_ATTEMPTS = "num_failed_attempts";
    protected int numFailedAttempts;
    private String gestureStr;
    private boolean isScreenLock = false;
    private boolean isFromHomeToWithdraw = false;
    private double unUsedAmount;//剩余可提现金额
    public static final String GestureRightTime = "GestureRightTime";
    public static final String GestureWrongTime = "GestureWrongTime";
    public static final String FinanceInfoKey = "FinanceInfo";
    public static final String ServerTimeKey = "ServerTime";
//    private CustomDialog.Builder builder;
//    private FinanceInfo mFinanceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_gesture_wrong);
        //重新进入再次验证

        messageText.setText(R.string.pl_draw_pattern);
        messageText.setTextColor(Color.BLACK);

        if (savedInstanceState == null) {
            numFailedAttempts = 0;
        } else {
            numFailedAttempts = savedInstanceState.getInt(KEY_NUM_FAILED_ATTEMPTS);
        }
    }


    @Override
    protected void initView() {
        super.initView();
        messageText.setText(R.string.pl_draw_pattern_to_unlock);
        indicator.setVisibility(View.GONE);
        resetpwd_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        super.setListener();
        patternView.setOnPatternListener(this);
    }


    @Override
    public void onPatternStart() {
        removeClearPatternRunnable();
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
        String patStr = PatternUtils.patternToNoString(pattern);
        String baseStr = Base64.encodeToString(patStr.getBytes(), Base64.NO_WRAP);
        gestureStr = baseStr;
//        gestureStr = MD5Util.stringToMD5(baseStr); // 安全性考虑这样做
        verifyGesture(gestureStr);
    }


    @Override
    protected void initData() {

    }

    @Override
    public boolean handleMessage(Message msg) {
        return super.handleMessage(msg);
    }


    /**
     * 验证手势
     */
    public void verifyGesture(String pwd) {
        if(pwd.equals(GestrueManager.GESTRUE)){
            Toast.makeText(this,"登录成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            messageText.setTextColor(Color.RED);
            messageText.setText("手势错误");

            messageText.startAnimation(shakeAnimation);
            patternView.setDisplayMode(PatternView.DisplayMode.Wrong);
            postClearPatternRunnable();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.changepwd_tv) {
        } else if (v.getId() == R.id.resetpwd_tv) {
            finish();
        }
        super.onClick(v);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_NUM_FAILED_ATTEMPTS, numFailedAttempts);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}