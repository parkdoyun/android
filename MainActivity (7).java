package com.cookandroid.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// 데이터베이스 이용 예제.
// 초기화, 입력, 수정, 삭제, 조회 기능
// 새로 데이터베이스 생성하기.
// 연습문제 12장 5

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtName, edtProduct, edtCount, edtNameResult, edtNumResult, edtProductResult, edtCountResult;
    Button btnInit, btnInsert, btnSelect, btnModify, btnDelete;
    SQLiteDatabase sqlDB;
    static int count = 1; // 자동으로 순번 올리기 위해 설정한 전역 변수.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("데이터베이스 예제");

        edtName = (EditText) findViewById(R.id.edtName);
        edtCount = (EditText) findViewById(R.id.edtCount);
        edtProduct = (EditText) findViewById(R.id.edtProduct);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumResult = (EditText) findViewById(R.id.edtNumResult);
        edtCountResult = (EditText) findViewById(R.id.edtCountResult);
        edtProductResult = (EditText) findViewById(R.id.edtProductResult);

        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnModify = (Button) findViewById(R.id.btnModify);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        myHelper = new myDBHelper(this);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int count1 = count;
                sqlDB = myHelper.getWritableDatabase(); // 읽고 쓰기 용으로 db open.
                sqlDB.execSQL("INSERT INTO prodTable VALUES ( " + count1 + ", '" + edtName.getText().toString() + "', '"
                        + edtProduct.getText().toString() + "', " + edtCount.getText().toString() + ");"); // ***INTEGER는 따옴표 안 씀 유의!***
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                btnSelect.callOnClick(); // 버튼 클릭 후 정보들 조회되도록! (자동으로 에디트텍스트 바뀜)
                count++; // 순번 올리기
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase(); // 읽기 전용으로 db open.
                Cursor cursor; // 커서 선언
                cursor = sqlDB.rawQuery("SELECT * FROM prodTable;", null); // 모든 테이블 조회한 후 커서에 대입
                // 테이블에 입력된 모든 행 데이터가 커서 변수에 들어있는 상태. 현재는 첫번째 행 가리키고 있다.
                String strNames = "이름" + "\r\n" + "---------" + "\r\n"; // \r\n -> 행을 넘겨서 다음 행부터 출력
                String strCount = "순번" + "\r\n" + "---------" + "\r\n";
                String strProduct = "물품명" + "\r\n" + "---------" + "\r\n";
                String strProductCount = "수량" + "\r\n" + "---------" + "\r\n";
                while (cursor.moveToNext()) { // moveToNext -> 커서 변수의 다음 행으로 넘어감
                    strCount += cursor.getString(0) + "\r\n";
                    strNames += cursor.getString(1) + "\r\n"; // 0은 0번째 열(그룹 이름), 1은 1번째 열(인원)
                    strProduct += cursor.getString(2) + "\r\n";
                    strProductCount += cursor.getString(3) + "\r\n";
                }

                edtProductResult.setText(strProduct);
                edtNameResult.setText(strNames);
                edtNumResult.setText(strCount);
                edtCountResult.setText(strProductCount);

                cursor.close();
                sqlDB.close();
            }
        });
        btnModify.setOnClickListener(new View.OnClickListener() { // 수정(인원 수)
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();// db open
                sqlDB.execSQL("UPDATE prodTable SET product = '" + edtProduct.getText().toString() + "' WHERE uName = '" +
                        edtName.getText().toString() + "';"); // 수정
                sqlDB.close();
                btnSelect.callOnClick();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM prodTable WHERE uName = '" + edtName.getText().toString() + "';");
                sqlDB.close();
                btnSelect.callOnClick();
            }
        });

    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "ex12_5DB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE prodTable (num INTEGER PRIMARY KEY, uName CHAR(15), product CHAR(15), count INTEGER);"); // **대문자 쓰는 것 유의!**
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // 초기화하는 함수
            db.execSQL("DROP TABLE IF EXISTS prodTable"); // 지우고
            onCreate(db); // 다시 생성
        }
    }

}
