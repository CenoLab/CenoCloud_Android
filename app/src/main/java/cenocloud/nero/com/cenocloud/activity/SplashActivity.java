package cenocloud.nero.com.cenocloud.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import cenocloud.nero.com.cenocloud.R;
import cenocloud.nero.com.cenocloud.utils.AdvancedCountdownTimer;

import static android.content.ContentValues.TAG;

/**
 * Created by neroyang on 2018/3/18.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jump;
    AdvancedCountdownTimer countdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        jump = findViewById(R.id.jump);
        jump.setOnClickListener(this);
        startCustomCountDownTime(5);
    }

    private void startCustomCountDownTime(long time) {
        countdownTimer = new AdvancedCountdownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished, int percent) {
                Log.d(TAG, "onTick   " + millisUntilFinished / 1000);
                jump.setText("跳过 ("+millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                jump.setText("跳过");
                Intent intent =new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        countdownTimer.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.jump:

                Intent intent =new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                countdownTimer.cancel();
                break;
            default:break;
        }
    }

}
