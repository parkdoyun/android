package com.cookandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

// 투표 결과 보여주는 액티비티.
public class ResultActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");

        Intent intent = getIntent();
        int[] voteRes = intent.getIntArrayExtra("VoteCnt");
        String[] picName = intent.getStringArrayExtra("PicName");

        TextView tv[] = new TextView[picName.length];
        RatingBar rbar[] = new RatingBar[picName.length];

        Integer tvID[] = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9}; // 텍스트뷰 아이디 배열
        Integer rbarID[] = {R.id.rBar1, R.id.rBar2, R.id.rBar3, R.id.rBar4, R.id.rBar5, R.id.rBar6,
                R.id.rBar7, R.id.rBar8, R.id.rBar9}; // rating bar 아이디 배열
        for(int i = 0; i < voteRes.length; i++)
        {
            tv[i] = (TextView) findViewById(tvID[i]); // 대입
            rbar[i] = (RatingBar) findViewById(rbarID[i]);
        }
        for(int i = 0; i < voteRes.length; i++)
        {
            tv[i].setText(picName[i]); // 텍스트뷰에 그림 제목 넣기
            rbar[i].setRating((float) voteRes[i]); // 별 갯수 설정
        }

        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
