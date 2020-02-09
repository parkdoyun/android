package com.cookandroid.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

// 영화 포스터를 클릭하면 나오는 대화상자의 제목 창에
// 영화 제목이 보이도록 하는 코드 예제.
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("영화 포스터");

        final GridView gv = (GridView) findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);


    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length; // 그리드에 보일 이미지의 개수 반환
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        Integer[] posterID = {
                R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05, R.drawable.mov06,
                R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12};
        String[] posterName = {
                "뺑반", "블랙팬서", "캡틴 아메리카 : 윈터 솔져", "내 머리속의 지우개", "러브레터", "말레피센트", "말아톤",
                "괴물", "마더", "원데이", "예스터데이", "짱구는 못말려 : 어른 제국의 역습" };


        public View getView(int position, View convertView, ViewGroup parent) { // 실제 영화 포스터의 개수만큼 반복된다고 생각하면 됨!(영화 포스터를 그리드뷰의 각 칸마다 보여줌)
            ImageView imageView = new ImageView(context); // 이미지뷰 변수 생성
            imageView.setLayoutParams(new GridView.LayoutParams(200, 300)); // 이미지뷰의 크기 200 * 300
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // 이미지뷰를 각 그리드뷰 칸의 중앙에 배치
            imageView.setPadding(5, 5, 5, 5);

            imageView.setImageResource(posterID[position]); // 넘어온 position 위치 적용

            final int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null); // dialog.xml 파일 인플레이트하여 dialogView에 대입
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this); // 대화상자 생성
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster); // dialog.xml의 이미지뷰에 접근
                    ivPoster.setImageResource(posterID[pos]); // 이미지 변경
                    dlg.setTitle(posterName[pos]); // 대화상자 제목 변경
                    dlg.setIcon(R.drawable.picture); // 아이콘
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null); // 버튼은 닫기 버튼 하나만, 리스터 없음.
                    dlg.show(); // 대화상자 보여주기
                }
            });
            return imageView;
        }
    }

}
