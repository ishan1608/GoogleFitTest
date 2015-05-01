package com.ishan1608.healthifyPlus;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity {

    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Removing title and setting full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        final ImageView splashImageView = (ImageView) findViewById(R.id.splash_image);

        TimerTask splashImageChangerTask = new TimerTask() {
            int imageCounter = 1;
            @Override
            public void run() {
                if(imageCounter == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            splashImageView.setImageResource(R.drawable.yv_progress_2);
                        }
                    });
                    imageCounter = 2;
                } else if(imageCounter == 2) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            splashImageView.setImageResource(R.drawable.yv_progress_3);
                        }
                    });
                    imageCounter = 3;
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            splashImageView.setImageResource(R.drawable.yv_progress_1);
                        }
                    });
                    imageCounter = 1;
                }
            }
        };
        Timer splashImageChangerTimer = new Timer("splashImageChangerTimer");
        splashImageChangerTimer.scheduleAtFixedRate(splashImageChangerTask, 1000, 1000);

        // Moving on to login activity after splash
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(loginIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
