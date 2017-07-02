package com.global.market;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by IMART on 8/3/2016.
 */
public class ShowAfterIntertialAd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_after_inertial_ad);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 2000);
    }
}
