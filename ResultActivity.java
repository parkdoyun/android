package com.cookandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
// 투표한 후 결과 보여준다.
// 결과랑 최다 득표한 그림 같이 ImageView와 TextView에 띄운다.
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");

        Intent intent = getIntent(); // 인텐트 받음
        int[] voteResult = intent.getIntArrayExtra("VoteCount"); // 넘겨받은 배열 변수 저장
        String[] imageName = intent.getStringArrayExtra("ImageName");
        List<Integer> fileName = intent.getIntegerArrayListExtra("FileName"); // int형이 아니라 Integer였다는 거 조심!!
        // ***Integer형은 받을 때 getIntegerArrayListExtra() 즉, 배열이 아닌 List(ArrayList)로 받아야 한다!!!(아님 에러)***

        int maxVoteIdx = 0; // 최다 득표 그림의 인덱스
        int maxVote = 0; // 최다 득표 수

        TextView tv[] = new TextView[imageName.length];
        RatingBar rbar[] = new RatingBar[imageName.length];

        Integer tvID[] = {R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9};
        Integer rbarID[] = {R.id.rBar1, R.id.rBar2, R.id.rBar3, R.id.rBar4, R.id.rBar5, R.id.rBar6, R.id.rBar7, R.id.rBar8, R.id.rBar9};

        for (int i = 0; i < voteResult.length; i++) {
            tv[i] = (TextView) findViewById(tvID[i]); // mapping
            rbar[i] = (RatingBar) findViewById(rbarID[i]);
        }

        for (int i = 0; i < voteResult.length; i++) {
            tv[i].setText(imageName[i]); // 그림 파일 이름 적용
            rbar[i].setRating((float) voteResult[i]); // 투표수 적용
        }
        for (int i = 0; i < voteResult.length; i++) { // 최다 득표한 그림 찾기.
            if (maxVote < voteResult[i]) {
                maxVoteIdx = i;
                maxVote = voteResult[i];
            }
        }
        TextView tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText(imageName[maxVoteIdx]);
        ImageView imgResult = (ImageView) findViewById(R.id.imgResult);
        imgResult.setImageResource(fileName.get(maxVoteIdx)); // **이렇게 받아야 된다!


        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
