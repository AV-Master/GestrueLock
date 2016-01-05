package com.beiing.gestruelock;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }


    public void gotoSetGestrue(View view) {
        Intent intent = new Intent(this, SetPatternActivity.class);
        startActivity(intent);
    }

    public void gotoGestrueLogin(View view) {
        Intent intent = new Intent(this, ConfirmPatternActivity.class);
        startActivity(intent);
    }
}
