package com.cookandroid.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// 간단한 음악 재생
// 듣기, 중지, 일시정지 구현
// 핸들러 이용
// 연습문제 13장 6번

public class MainActivity extends AppCompatActivity {

    ListView listViewMP3;
    Button btnPlay, btnStop, btnPause;
    TextView tvMP3, tvTime;
    SeekBar seekBar1;


    ArrayList<String> mp3List;
    String selectedMP3;

    String mp3Path = Environment.getExternalStorageDirectory().getPath() + "/";
    MediaPlayer mPlayer;
    Handler mp3Handler; // 여기에 전역변수로 선언하고 써야 에러 X.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 MP3 플레이어");

        //파일 읽어오는 것 퍼미션
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        mp3List = new ArrayList<String>(); // 리스트뷰에 출력할 변수 생성(파일명 변수)

        File[] listFiles = new File(mp3Path).listFiles();
        String fileName, extName;
        for (File file : listFiles) { // listFiles에 들어 있는 파일 또는 폴더를 하나씩 file 변수에 넣고 for문 실행.
            fileName = file.getName(); // 파일 이름 추출
            extName = fileName.substring(fileName.length() - 3); // 확장명 추출
            if (extName.equals((String) "mp3"))
                mp3List.add(fileName); // 확장명이 .mp3이면 파일 이름을 mp3List에 추가.
        }

        listViewMP3 = (ListView) findViewById(R.id.listViewMP3); // 리스트뷰에 mp3List 배열의 내용 출력.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mp3List);
        listViewMP3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewMP3.setAdapter(adapter);
        listViewMP3.setItemChecked(0, true);

        listViewMP3.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 리스트뷰의 각 항목을 클릭할 때마다 파일 이름이
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                selectedMP3 = mp3List.get(arg2); // selectedMP3 변수에 저장 (파일명)
            }
        });
        selectedMP3 = mp3List.get(0); // 처음 실행되면 선택된 MP3 파일을 리스트뷰의 첫번째 MP3 파일로 설정
        // -> 리스트뷰를 클릭하지 않고 바로 듣기 클릭했을 때 오류 생기지 않게 하려고!

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnPause = (Button) findViewById(R.id.btnPause);
        tvMP3 = (TextView) findViewById(R.id.tvMP3);
        tvTime = (TextView) findViewById(R.id.tvTime);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);

        //진행 시간 분:초 형식으로 표현하기 위해 SimpleDateFormat 객체 생성.
        final SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");

        //Handler 개체 생성
        mp3Handler = new Handler() {
            public void handleMessage(Message msg) {
                //변경할 내용을 이 부분에 코딩

                seekBar1.setProgress(mPlayer.getCurrentPosition()); // 음악의 현재 진행 위치 가져옴
                tvTime.setText("진행 시간 : " + timeFormat.format(mPlayer.getCurrentPosition()));


                mp3Handler.sendEmptyMessageDelayed(0, 200); // 핸들러가 0.2초마다 진행하게 함.
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() { // 시작
            @Override
            public void onClick(View view) {
                try {
                    mPlayer = new MediaPlayer(); // MediaPlayer 생성
                    mPlayer.setDataSource(mp3Path + selectedMP3); // 시작할 파일 지정 (selectedMP3에 대입되어 있는 파일명)
                    mPlayer.prepare();
                    mPlayer.start(); // 시작
                    btnPlay.setClickable(false);
                    btnStop.setClickable(true);
                    btnPause.setClickable(true);
                    tvMP3.setText("실행 중인 음악 : " + selectedMP3);
                    seekBar1.setMax(mPlayer.getDuration());
                    mp3Handler.sendEmptyMessage(0);
                } catch (IOException e) {
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() { // 중지
            @Override
            public void onClick(View view) {
                mPlayer.stop(); // 중지
                mPlayer.reset(); // 초기화
                btnPlay.setClickable(true);
                btnStop.setClickable(false);
                btnPause.setClickable(false);
                btnPause.setText("일시정지");
                tvMP3.setText("실행 중인 음악 : ");

            }
        });
        btnStop.setClickable(false); // MediaPlayer가 시작되지 않은 상태에서 중지, 일시정지를 클릭했을 때 발생하는 오류 방지
        btnPause.setClickable(false);

        btnPause.setOnClickListener(new View.OnClickListener() { // 일시정지
            @Override
            public void onClick(View view) {
                if (mPlayer.isPlaying()) // 노래가 재생되고 있다면 -> 일시정지
                {
                    mPlayer.pause(); // 일시정지
                    btnPause.setText("이어듣기");

                } else { // 일시정지 상태라면 -> 이어듣기
                    mPlayer.start();
                    btnPause.setText("일시정지");

                }

            }
        });
    }


}