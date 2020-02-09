package com.cookandroid.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

// 각 그리드뷰에 있는 명화를 클릭했을 때 투표가 되도록 한다.
// 투표 종료 버튼을 누르면 투표가 종료된다.

public class MainActivity extends AppCompatActivity {
    final int voteCnt[] = new int[9]; // 투표 수 저장할 배열 -> 전역으로 선언
    final String picName[] = {"도시의 무도회", "푸르네즈에서의 점심 식사", "물뿌리개를 든 소녀",
            "뱃놀이 일행의 오찬", "아르장퇴유의 정원에서", "작은 배", "잔 사마리의 초상",
            "해변에서", "호숫가 근처 물가에서"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("명화 선호도 투표");

        final GridView gv = (GridView) findViewById(R.id.gridView1); // 그리드뷰
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() { // 투표 종료를 눌렀을 때
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("VoteCnt",voteCnt);
                intent.putExtra("PicName", picName);
                startActivity(intent);
            }
        });


    }
    public class MyGridAdapter extends BaseAdapter{
        Context context;
        public MyGridAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return picID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        Integer[] picID = {R.drawable.renoir1, R.drawable.renoir2, R.drawable.renoir3,
                R.drawable.renoir4, R.drawable.renoir5, R.drawable.renoir6,
                R.drawable.renoir7, R.drawable.renoir8, R.drawable.renoir9};

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 150)); // 크기
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // 중앙에 배치
            imageView.setPadding(5, 5, 5, 5); // 패딩

            imageView.setImageResource(picID[position]);
            // 각 명화 클릭했을 시 나타나는 이벤트 리스너 -> 투표해야함
            final int pos = position; // 이렇게 해줘야함! -> 문법
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    voteCnt[pos]++;
                    Toast.makeText(getApplicationContext(), picName[pos]  + " : 총 " + voteCnt[pos] + " 표", Toast.LENGTH_SHORT).show(); // 현재 투표 수 토스트
                }
            });


            return imageView;
        }
    }






}
