package kr.ac.konkuk.a201812331_mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.royrodriguez.transitionbutton.TransitionButton;

public class MainActivity extends AppCompatActivity {

    //Button btnNext;
    EditText editPw;
    String editPassword;
    private TransitionButton transitionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_main.xml 배치
        setContentView(R.layout.activity_main);
        // 메인 액티비티의 타이틀 지정
        setTitle("Daily Diary");
        // 입력한 패스워드를 editPw에 저장
        editPw = (EditText)findViewById(R.id.pw);
        // 입력한 패스워드를 String 자료형으로 변환하여 변수에 저장
        editPassword = editPw.getText().toString();

        // 로그인 버튼
        // 로그인 버튼을 눌렀을때 트랜지션 효과를 주고자 한다.
        // activity_main.xml의 로그인버튼(id가 transition_button인)을 가져옴
        transitionButton = findViewById(R.id.transition_button);

        // 로그인 버튼을 눌렀을때 호출되는 이벤트 리스너
        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the loading animation when the user tap the button
                // 로딩 애니메이션 시작
                transitionButton.startAnimation();

                // Do your networking task or background work here.
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccessful = true;

                        // Choose a stop animation if your call was succesful or not
                        if (isSuccessful) {
                            // 로그인버튼의 애니메이션을 멈추고
                            transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, new TransitionButton.OnAnimationStopEndListener() {
                                @Override
                                public void onAnimationStopEnd() {
                                    // 캘린더 다이어리 인텐트 객체 생성
                                    Intent intent = new Intent(getBaseContext(),CalendarDiaryActivity.class);
                                    // 액티비티 전환시 애니메이션 무시
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    // editText에서 입력한 비밀번호를 다음 액티비티로 전달하기 위해 엑스트라 필드에 값을 저장
                                    intent.putExtra("input",editPassword);
                                    // CalendarDiary 액티비티 시작
                                    startActivity(intent);
                                }
                            });
                        } else {
                            transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                        }
                    }
                }, 2000);
            }
        });
    }
}