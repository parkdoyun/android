package com.cookandroid.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

// 스피너에서 영화 제목을 선택하면
// 영화 포스터가 아래의 MyGraphicView에 나오도록 하는 코드입니다.
// MyGraphicView에는 컨텍스트 메뉴가 추가됩니다.
// 컨텍스트 메뉴 : 회전, 확대, 축소, 기울기 증가/감소

public class MainActivity extends AppCompatActivity {

    MyGraphicView graphicView;
    static float scaleX = 1, scaleY = 1; // 확대 / 축소 비율 결정할 전역 변수.
    static float angle = 0; // 기울기
    static float angle_size = 20; // 기울기 어느 정도로 할 건지

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("영화 포스터(스피너 이용)");

        final String[] movie =  {
                "뺑반", "블랙팬서", "캡틴 아메리카 : 윈터 솔져", "내 머리속의 지우개", "러브레터", "말레피센트", "말아톤",
                "괴물", "마더", "원데이", "예스터데이", "짱구는 못말려 : 어른 제국의 역습" };
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, movie);
        spinner.setAdapter(adapter);

        final Integer[] posterID = {R.drawable.mov01, R.drawable.mov02, R.drawable.mov03, R.drawable.mov04, R.drawable.mov05,
                R.drawable.mov06, R.drawable.mov07, R.drawable.mov08, R.drawable.mov09, R.drawable.mov10, R.drawable.mov11, R.drawable.mov12};

        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.picLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView);
        registerForContextMenu(graphicView); // 그래픽뷰에 컨텍스트 메뉴 추가.

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){ // 리스너 작성
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                //이미지 바꿔주기
                graphicView.draw(posterID[position]);
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때, 이게 꼭 있어야 함!! (onItemSelected와 쌍둥이 같은 존재!)
                Toast.makeText(getApplicationContext(), "선택된 항목이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private static class MyGraphicView extends View{
        private Bitmap image; // 이렇게 따로 변수로 빼야 이미지 변경 가능!
        public MyGraphicView(Context context){
            super(context);
            Resources r = context.getResources();
            image = BitmapFactory.decodeResource(r, R.drawable.mov01); // 처음에는 뺑반 포스터로 설정(디폴트)
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int picX = (this.getWidth() - image.getWidth()) / 2;
            int picY = (this.getHeight() - image.getHeight()) / 2;
            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;

            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(angle, cenX, cenY);
            canvas.drawBitmap(image, picX, picY, null); // image 캔버스에 그리기
            super.onDraw(canvas);


        }

        public void draw(Integer posterID) { // 포스터 id 가지고 이미지 그린다.
            image = BitmapFactory.decodeResource(getResources(),posterID); // 이미지 리소스 변경한 다음
            invalidate(); // onDraw() 다시 호출
        }

        public void rotate() // 이미지 회전시키는 함수.
        {
            angle = angle + angle_size;
            invalidate(); // onDraw() 호출.
        }

        public void expand() // 확대
        {
            scaleX += 0.2f;
            scaleY += 0.2f;
            invalidate();
        }
        public void reduce() // 축소
        {
            scaleX -= 0.2f;
            scaleY -= 0.2f;
            invalidate();
        }
        public void lean_expand() // 기울기 증가
        {
            angle_size += 10f;
            invalidate();
        }
        public void lean_reduce() // 기울기 감소
        {
            angle_size -= 10f;
            invalidate();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 1, 0, "회전");
        menu.add(0, 2, 0, "확대");
        menu.add(0, 3, 0, "축소");
        menu.add(0, 4, 0, "기울기 증가");
        menu.add(0, 5, 0, "기울기 감소");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) { // 컨텍스트 메뉴
        switch (item.getItemId())
        {
            case 1:
                //회전
                graphicView.rotate();
                return true;
            case 2:
                // 확대
                graphicView.expand();
                return true;
            case 3:
                // 축소
                graphicView.reduce();
               return true;
            case 4:
                // 기울기 증가
                graphicView.lean_expand();
                return true;
            case 5:
                //기울기 감소
                graphicView.lean_reduce();
                return true;

        }
        return false;
    }
}
