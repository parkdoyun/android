package com.cookandroid.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

// 스피너에서 영화 제목을 선택하면
// 영화 포스터가 아래의 이미지뷰에 나오도록 하는 코드입니다.

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("스피너 테스트");

        final String[] movie =  {
                "뺑반", "블랙팬서", "캡틴 아메리카 : 윈터 솔져", "내 머리속의 지우개", "러브레터", "말레피센트", "말아톤",
                "괴물", "마더", "원데이", "예스터데이", "짱구는 못말려 : 어른 제국의 역습" };
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, movie);
        spinner.setAdapter(adapter);

        final Integer[] posterID = {R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
                R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){ // 리스너 작성
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                ImageView img = (ImageView) findViewById(R.id.ivPoster);
                img.setImageResource(posterID[position]);

            }
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때, 이게 꼭 있어야 함!! (onItemSelected와 쌍둥이 같은 존재!)
                Toast.makeText(getApplicationContext(), "선택된 항목이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });



    }






}
