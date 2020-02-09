package com.cookandroid.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// 갤러리 이용하여 영화 포스터 보는 코드
// 갤러리의 포스터 터치하면 밑의 이미지뷰에 포스터 크게 나온다
// 나오면서 고급 토스트 생성 (영화 제목 띄우는)
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("영화 포스터");

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        MyGalleryAdapter galAdapter = new MyGalleryAdapter(this);
        gallery.setAdapter(galAdapter);



    }
    public class MyGalleryAdapter extends BaseAdapter{
        Context context;
        Integer[] posterID = {R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
                R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12};
        String[] posterName = {
                "뺑반", "블랙팬서", "캡틴 아메리카 : 윈터 솔져", "내 머리속의 지우개", "러브레터", "말레피센트", "말아톤",
                "괴물", "마더", "원데이", "예스터데이", "짱구는 못말려 : 어른 제국의 역습" };

        public MyGalleryAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 300)); // 200 * 300 크기 지정
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // 중앙에 오도록
            imageView.setPadding(5, 5, 5, 5);

            imageView.setImageResource(posterID[position]);

            final int pos = position;
            imageView.setOnTouchListener(new View.OnTouchListener() { // 클릭 리스너가 아닌 터치 리스너 사용
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);

                    // 토스트 띄우기
                    //TextView toastText = (TextView) findViewById(R.id.textName); ***여기서 텍스트 찾으면 프로그램 죽음!***
                    View toastView = (View) View.inflate(MainActivity.this, R.layout.movie_toast, null); // movie_toast.xml 인플레이트해서 toastView에 대입
                    Toast toast = new Toast(MainActivity.this); // 토스트 생성
                    TextView toastText = (TextView) toastView.findViewById(R.id.textName); // ***이렇게 해야함!***
                    toastText.setText(posterName[pos]); // 글자 변경(영화 제목으로)
                    toast.setView(toastView);
                    toast.show(); // 띄우기

                    return false;
                }
            });

            return imageView;
        }
    }




}
