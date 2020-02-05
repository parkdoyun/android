package com.cookandroid.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    MyGraphicView graphicView;
    static float scaleX = 1, scaleY = 1;
    static float angle = 0;
    static float color = 1;
    static float satur = 1; // 채도 배수 1이면 기본, 0~1이면 채도가 낮게 보이며 1 이상이면 높게 보임 (0 : 회색조 이미지)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ***잘 확인하기!!! new MyGraphicView(this)에서 바꿔야 하는지 아닌지
        setTitle("연습문제 9-5");


        LinearLayout pictureLayout = (LinearLayout) findViewById(R.id.pictureLayout); // 인플레이트
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView); // 아래 레이아웃에는 MyGraphicView에서 설정한 내용 출력
        registerForContextMenu(pictureLayout); // 이미지 클릭 시 컨텍스트 메뉴 나오도록


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, 1, 0, "확대");
        menu.add(0, 2, 0, "축소");
        menu.add(0, 3, 0, "회전");
        menu.add(0, 4, 0, "밝게");
        menu.add(0, 5, 0, "어둡게");
        menu.add(0, 6, 0, "그레이영상");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f; // 축척 전역변수 0.2 증가
                graphicView.invalidate(); // onDraw 자동으로 호출
                return true;
            case 2:
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f; // 축척 전역변수 0.2 감소
                graphicView.invalidate(); // onDraw 자동으로 호출
                return true;
            case 3:
                angle = angle + 20;
                graphicView.invalidate(); // onDraw 자동으로 호출
                return true;
            case 4:
                color = color + 0.2f;
                graphicView.invalidate(); // onDraw 자동으로 호출
                return true;
            case 5:
                color = color - 0.2f;
                graphicView.invalidate(); // onDraw 자동으로 호출
                return true;
            case 6:
                if (satur == 0) satur = 1;
                else satur = 0;
                graphicView.invalidate();
                return true;
        }
        return true;
    }


    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.lenna);


            int picX = (this.getWidth() - pic.getWidth()) / 2;
            int picY = (this.getHeight() - pic.getHeight()) / 2;

            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;

            Paint paint = new Paint();


            float[] arr = {color, 0, 0, 0, 0,
                    0, color, 0, 0, 0,
                    0, 0, color, 0, 0,
                    0, 0, 0, 1, 0};
            ColorMatrix cm = new ColorMatrix(arr);
            if(satur == 0) cm.setSaturation(satur); // 채도 설정
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            canvas.scale(scaleX, scaleY, cenX, cenY);
            canvas.rotate(angle, cenX, cenY);
            canvas.drawBitmap(pic, picX, picY, paint);
            pic.recycle();
        }
    }


}