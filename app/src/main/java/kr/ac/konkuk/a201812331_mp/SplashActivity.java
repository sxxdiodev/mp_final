package kr.ac.konkuk.a201812331_mp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceStare) {
        super.onCreate(savedInstanceStare);
        // activity_splash.xml 배치
        setContentView(R.layout.activity_splash);
        // 3초 동안 스플래쉬 액티비티를 띄운 후 메인액티비티 시작하게 함
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            // 해당 액티비티가 나오고 3초후 MainActivity 실행
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
