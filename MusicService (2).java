package com.cookandroid.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {

    MediaPlayer mp;
    String mp3Path = Environment.getExternalStorageDirectory().getPath() + "/"; // 음악 파일 경로
    ArrayList<String> mp3List;
    int list_index; // 전체 재생목록 돌기 위해서 만든 인덱스.

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        android.util.Log.i("서비스 테스트", "onCreate()");
        super.onCreate();

        mp3List = new ArrayList<String>();

        File[] listFiles = new File(mp3Path).listFiles();
        String fileName, extName;
        for (File file : listFiles) {
            fileName = file.getName(); // 파일 전체 이름
            extName = fileName.substring(fileName.length() - 3); // 확장자 구함
            if (extName.equals((String) "mp3"))
                mp3List.add(fileName); // 확장자가 "mp3"라면 mp3List에 파일명 넣는다.
        }
        list_index = 0;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        android.util.Log.i("서비스 테스트", "onStartCommand()");
        mp = new MediaPlayer();
        try {
            mp.setDataSource(mp3Path + mp3List.get(list_index));
            mp.prepare();
            mp.start();
        } catch (IOException e) {
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // MediaPlayer의 재생이 끝났는지 확인하는 이벤트리스너.
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                android.util.Log.i("서비스 테스트", "다음 음악 재생()");
                try {
                    if (list_index == mp3List.size() - 1) list_index = 0; // 맨 마지막 음악이었으면 처음 음악이 나오도록!
                    else list_index++;
                    mp.reset(); // ***꼭 reset하고 다시 음악 지정해야 함!! 안 그러면 프로그램 죽는다***
                    mp.setDataSource(mp3Path + mp3List.get(list_index));
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                }

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        android.util.Log.i("서비스 테스트", "onDestroy()");
        mp.stop();
        super.onDestroy();
    }
}
