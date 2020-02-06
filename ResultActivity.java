package com.cookandroid.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.List;

//값 받아서 1등부터 9등까지 순서대로 이미지뷰에 넣고
//뷰 플리퍼로 자동보기 기능 넣어야한다.
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle("투표 결과");

        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount"); // 넘겨받은 배열 변수 저장
        List<Integer> fileName = intent.getIntegerArrayListExtra("FileName"); // int형이 아니라 Integer였다는 거 조심!!
        // ***Integer형은 받을 때 getIntegerArrayListExtra() 즉, 배열이 아닌 List(ArrayList)로 받아야 한다!!!(아님 에러)***

        int[] order = new int[9]; // 1등부터 투표수대로 인덱스 담을 배열 생성
        int[] cntCheck = new int[9]; // 해당 인덱스가 이미 order[]배열에 있는 인덱스인지 아닌지 확인하기 위해 생성 -1 : check x, 0 : check
        for (int i = 0; i < 9; i++) {
            cntCheck[i] = -1; // 초기화
        }
        int max = -1; // 투표 수 최대값 저장할 변수.
        int max_idx = -1; // 최댓값의 인덱스 저장할 변수.

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cntCheck[j] == 0) continue; // 이미 체크된 항목 제외
                if (voteResult[j] > max) {
                    max = voteResult[j]; // 최댓값 갱신
                    max_idx = j; // 인덱스 갱신
                }
            }
            order[i] = max_idx;
            cntCheck[max_idx] = 0; // 체크
            max = -1; // 최댓값 초기화
        }
        int[] imgName = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6, R.id.img7, R.id.img8, R.id.img9}; // 이미지 아이디 배열

        ImageView[] img = new ImageView[9];

        for (int i = 0; i < 9; i++) {
            img[i] = (ImageView) findViewById(imgName[i]); // mapping.
        }

        for (int i = 0; i < 9; i++) // 순서대로 이미지 파일에 넣어줌.
        {
            img[i].setImageResource(fileName.get(order[i])); // order 배열의 인덱스 순서대로 파일 집어넣기
        }


        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        final ViewFlipper vFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.setFlipInterval(2000); // ms이므로 (1000ms = 1s)
                vFlipper.startFlipping();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.stopFlipping();
            }
        });
    }
}
