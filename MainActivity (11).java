package com.cookandroid.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


// 서비스 테스트 예제.
// 토스트로 나타냄(로그캣 대신)
// 음악 서비스 시작 누르면 응용 프로그램은 자동으로 종료되고
// 음악은 계속 재생.
// 직접 풀어보기 14-1

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Button btnStart, btnStop;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("서비스 테스트 예제");

        intent = new Intent(this, MusicService.class); // MusicService.class 파일을 인텐트로 생성 -> 이것도 manifest에 등록해야함!
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intent);
                Toast.makeText(getApplicationContext(), "startService()", Toast.LENGTH_SHORT).show();
                finish(); // 음악서비스 시작 누르면 응용 프로그램 자동으로 종료.
                // 음악은 계속 나온다.
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intent);
                Toast.makeText(getApplicationContext(), "stopService()", Toast.LENGTH_SHORT).show();
            }
        });

    }


}