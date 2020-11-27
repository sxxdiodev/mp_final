package kr.ac.konkuk.a201812331_mp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarDiaryActivity extends AppCompatActivity {
    // 버튼
    Button btnBack,btnSave;
    // 캘린더뷰
    CalendarView calView;
    // 입력할 일기내용
    EditText editDiary;
    // 선택한 년월일 출력할 텍스트뷰
    TextView tvYear, tvMonth, tvDay;
    int selectYear, selectMonth, selectDay;
    // 입력한 비밀번호
    String password;
    // 입력한 일기내용을 저장할 파일명 정의
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // calendar_diary_activity.xml 배치
        setContentView(R.layout.calendar_diary_activity);
        // 액티비티 타이틀 지정
        setTitle("나의 한줄 일기장");

        // 버튼
        btnBack = (Button)findViewById(R.id.btnBack);
        btnSave = (Button)findViewById(R.id.btnSave);
        // Calendar 위젯
        calView = (CalendarView) findViewById(R.id.calView);
        // 표시할 연월일
        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        // 해당 날짜에 입력하는 일기 문장
        editDiary = (EditText)findViewById(R.id.editDiary);

        // 비밀번호를 확인하기 위해 엑스트라필드에서 값을 추출
        Intent intent = new Intent(this.getIntent());
        password = intent.getStringExtra("input");

        // 비밀번호가 맞다면
        // 코드상에서 비밀번호 설정하게 함(현재는 디폴트 상태)
        if(password.toString().equals(""))
            // 로그인 성공 토스트창 출력
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        // 비밀번호가 아니라면
        else {
            // 로그인 실패 토스트창 출력후 종료
            Toast.makeText(this, "잘못된 비밀번호 입니다. 다시 로그인하세요.", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 일기를 저장할 파일명
        fileName = Integer.toString(selectYear) + "_" + Integer.toString(selectMonth+1)
                + "_" + Integer.toString(selectDay) + ".txt";
        // 파일명으로 editText에 입력한 내용을 str에 저장함
        String str = readDiary(fileName);
        // 저장된 일기 내용을 보여줌
        editDiary.setText(str);

        // 캘린더에서 지정한 날짜를 표시해주는 함수
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            // 선택한 년월일을 출력해준다.
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectYear =  year;
                selectMonth = month + 1;
                selectDay = dayOfMonth;

                tvYear.setText(Integer.toString(selectYear));
                tvMonth.setText(Integer.toString(selectMonth));
                tvDay.setText(Integer.toString(selectDay));
                // 선택한 년월일에 따라 파일이름 저장
                fileName = Integer.toString(year) + "_"
                        + Integer.toString(month + 1) + "_"
                        + Integer.toString(dayOfMonth) + ".txt";
                String str = readDiary(fileName);
                editDiary.setText(str);
            }
        });
        // 입력한 내용을 장치 내부에 저장함
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    // 혼자 사용하는 모드니까 MODE_PRIVATE
                    FileOutputStream outFs = openFileOutput(fileName,
                            Context.MODE_PRIVATE);
                    String str = editDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),
                            fileName + " 저장", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                }
            }
        });
        // 로그아웃 버튼을 눌렀을 때
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 토스트 창 출력 후 종료
                Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    // 디바이스 내부에 일기 내용을 입력하기 위한 함수
    String readDiary(String fName) {
        String diaryStr = null;
        FileInputStream inFs;
        try {
            // openFileInput으로 inputStream을 가져와 저장
            inFs = openFileInput(fName);
            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            diaryStr = (new String(txt)).trim();
            btnSave.setText("수정");
        } catch (IOException e) {
            editDiary.setHint("여기에 입력하세요.");
            btnSave.setText("저장");
        }
        return diaryStr;
    }
}
