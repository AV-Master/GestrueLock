package com.beiing.gestruelock.base;

import com.beiing.gestruelock.R;
import com.beiing.gestruelock.gestruelock.LockIndicator;
import com.beiing.gestruelock.gestruelock.PatternView;

import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.kakao.finance.R;
//import com.kakao.finance.activity.BaseNewActivity;
//import com.kakao.finance.activity.ConfirmPatternActivity;
//import com.kakao.finance.util.FActivityManager;
//import com.kakao.finance.util.TimeUtils;
//import com.kakao.finance.view.HeadBar;

/**
 * �������븸��
 */
public class BasePatternActivity extends BaseNewActivity {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 1000;
    private final int errorTimeInterval = 10;

    protected TextView messageText;
    protected PatternView patternView;
    protected LinearLayout buttonContainer;
    protected Button leftButton;
    protected Button rightButton;
    //protected TextView changepwd_tv;
    protected TextView resetpwd_tv;
    protected LockIndicator indicator;
    protected Animation shakeAnimation;


    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            patternView.clearPattern();
        }
    };


    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.pl_base_pattern_activity);
    }

    @Override
    protected void initView() {
        messageText = (TextView) findViewById(R.id.pl_message_text);
        patternView = (PatternView) findViewById(R.id.pl_pattern);
        buttonContainer = (LinearLayout) findViewById(R.id.pl_button_container);
        leftButton = (Button) findViewById(R.id.pl_left_button);
        rightButton = (Button) findViewById(R.id.pl_right_button);
        //changepwd_tv = (TextView) findViewById(R.id.changepwd_tv);
//        title_head = (HeadBar) findViewById(R.id.title_head);
//        title_head.setBackgroundColor(Color.WHITE);
        indicator = (LockIndicator) findViewById(R.id.indicator);
        resetpwd_tv = (TextView) findViewById(R.id.resetpwd_tv);

    }

    @Override
    protected void initData() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_gesture_wrong);
    }

    @Override
    protected void setListener() {
        //changepwd_tv.setOnClickListener(this);
//        title_head.getBtnBack().setOnClickListener(this);
        resetpwd_tv.setOnClickListener(this);
    }

    protected void removeClearPatternRunnable() {
        patternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        patternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == title_head.getBtnBack().getId()) {
////            ActivityManager.getManager().goTo(this, LoginActivity.class);
////            finish();
//            FActivityManager.getManager().clearActivies();
//        }
    }


    protected boolean checkError() {
//        String errorTime = appDataSP.getStrValue(ConfirmPatternActivity.GestureWrongTime, "");
//        if (errorTime.isEmpty()) {
//            patternView.setInputEnabled(true);
//            return true;
//        }
//        String curTime = TimeUtils.getCurrentTime(TimeUtils.dateFormat);
//        float interval = TimeUtils.getTimeInterval(errorTime, curTime, TimeUtils.dateFormat);
//        if (!errorTime.isEmpty() && interval < errorTimeInterval) {
//            patternView.setInputEnabled(false);
//            return false;
//        } else {
//            patternView.setInputEnabled(true);
//            return true;
//        }
        return false;
    }
}
