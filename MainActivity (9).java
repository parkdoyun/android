package com.cookandroid.myapplication;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


// 스레드 이용한 mp3 플레이어
// 시크바를 이용하여 원하는 지점에서 음악 재생할 수 있도록 만든 코드.
public class MainActivity extends AppCompatActivity {
    ListView listViewMP3;
    TextView tvMP3, tvTime;
    ProgressBar pbMP3;
    Switch switchPlay;
    SeekBar seekBar1;

    ArrayList<String> mp3List;
    String selectedMP3;

    String mp3Path = Environment.getExternalStorageDirectory().getPath() + "/"; // 파일 경로
    MediaPlayer mPlayer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE); // 퍼미션

        mp3List = new ArrayList<String>();

        File[] listFiles = new File(mp3Path).listFiles();
        String fileName, extName;
        for (File file : listFiles) {
            fileName = file.getName(); // 파일 이름
            extName = fileName.substring(fileName.length() - 3); // 확장명 변수
            if (extName.equals((String) "mp3")) mp3List.add(fileName); // 확장명이 mp3라면 mp3List에 파일명 추가
        }

        listViewMP3 = (ListView) findViewById(R.id.listViewMP3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mp3List);
        listViewMP3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewMP3.setAdapter(adapter);
        listViewMP3.setItemChecked(0, true);

        listViewMP3.setOnItemClickListener( // 리스트뷰의 각 항목 클릭할 때마다
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedMP3 = mp3List.get(i); // 파일 이름이 selectedMP3에 저장.
                    }
                }
        );
        selectedMP3 = mp3List.get(0);

        switchPlay = (Switch) findViewById(R.id.switchPlay);
        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        tvMP3 = (TextView) findViewById(R.id.tvMP3);
        tvTime = (TextView) findViewById(R.id.tvTime);
        pbMP3 = (ProgressBar) findViewById(R.id.pbMP3);

        switchPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // 스위치는 이 이벤트리스너 주로 사용!
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchPlay.isChecked()) // ***스위치가 켜지면(음악 재생)*** 헷갈리지 않기!
                {
                    try {
                        mPlayer = new MediaPlayer();
                        mPlayer.setDataSource(mp3Path + selectedMP3);
                        mPlayer.prepare();
                        mPlayer.start();
                        tvMP3.setText("실행 중인 음악 : " + selectedMP3);
                        new Thread() { // UI 스레드 이용
                            SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss"); // 진행 시간을 분:초 형식으로 표현하기 위해

                            public void run() {
                                if (mPlayer == null) return;
                                pbMP3.setMax(mPlayer.getDuration());
                                seekBar1.setMax(mPlayer.getDuration()); // 시크바 최대치 설정(끝 설정)
                                while (mPlayer.isPlaying()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pbMP3.setProgress(mPlayer.getCurrentPosition()); // 음악의 현재 진행 위치를 getCurrentPosition으로 가져옴
                                            tvTime.setText("진행 시간 : " + timeFormat.format(mPlayer.getCurrentPosition())); // 출력
                                            seekBar1.setProgress(mPlayer.getCurrentPosition()); // 시크바 변경되도록
                                        }
                                    });
                                    SystemClock.sleep(200); // 0.2초마다 진행 상태 변경되도록!
                                }
                            }
                        }.start();
                    } catch (IOException e) {
                    }


                } else // 스위치가 꺼지면(음악 중지)
                {
                    mPlayer.stop();
                    mPlayer.reset();
                    tvMP3.setText("실행 중인 음악 : ");
                    pbMP3.setProgress(0);  // 초기화
                    tvTime.setText("진행 시간 : ");
                }
            }
        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //시크바 움직이면 그 상태에서 재생하도록
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


}