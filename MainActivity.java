package com.cookandroid.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

// 먼저 투표하는 코드
// 투표 수 저장한 배열과 파일명 저장한 배열 ResultActivity에 넘겨줌
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("명화 투표");


        final int voteCount[] = new int[9];
        for (int i = 0; i < 9; i++) {
            voteCount[i] = 0;
        }

        ImageView image[] = new ImageView[9];
        Integer imageId[] = {R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9};
        //final Integer imageFileId[] = {R.drawable.renoir1, R.drawable.renoir2, R.drawable.renoir3, R.drawable.renoir4,R.drawable.renoir5, R.drawable.renoir6,
        //R.drawable.renoir7, R.drawable.renoir8, R.drawable.renoir9}; -> Integer 배열로 넘기면 값 안 넘겨짐!
        final ArrayList<Integer> imageFileId = new ArrayList<Integer>(); // ArryList로 넘거야함!
        imageFileId.add(R.drawable.renoir1);
        imageFileId.add(R.drawable.renoir2); // 파일 이름들 추가.
        imageFileId.add(R.drawable.renoir3);
        imageFileId.add(R.drawable.renoir4);
        imageFileId.add(R.drawable.renoir5);
        imageFileId.add(R.drawable.renoir6);
        imageFileId.add(R.drawable.renoir7);
        imageFileId.add(R.drawable.renoir8);
        imageFileId.add(R.drawable.renoir9);

        final String imgName[] = {"도시의 무도회", "푸르네즈에서의 점심 식사", "물뿌리개를 든 소녀",
                "뱃놀이 일행의 오찬", "아르장퇴유의 정원에서", "작은 배", "잔 사마리의 초상",
                "해변에서", "호숫가 근처 물가에서"};

        for (int i = 0; i < imageId.length; i++) {
            final int index; // **주의!! 꼭 필요함!!**
            index = i;
            image[index] = (ImageView) findViewById(imageId[index]); // 각각 매칭
            image[index].setOnClickListener(new View.OnClickListener() { // 배열 이용해 클릭 리스너 작성
                @Override
                public void onClick(View view) {
                    voteCount[index]++; // 클릭할 때마다 투표수 증가
                    Toast.makeText(getApplicationContext(), imgName[index] + " : 총 " + voteCount[index] + " 표", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button btnResult = (Button) findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("VoteCount", voteCount); // 정수형 배열 voteCount를 VoteCount라는 이름으로 넘긴다 -> getIntArrayExtra()
                //intent.putExtra("ImageName", imgName); // -> getStringArrayExtra()
                intent.putIntegerArrayListExtra("FileName", imageFileId); // ***Integer는 이렇게 넘겨야 함!!!
                startActivity(intent);
            }
        });
    }


}