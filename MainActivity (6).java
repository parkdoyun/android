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

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnInit, btnInsert, btnSelect, btnModify, btnDelete;
    SQLiteDatabase sqlDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("sqlite 데이터베이스 연습");

        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumberResult = (EditText) findViewById(R.id.edtNumberResult);
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
                sqlDB = myHelper.getWritableDatabase(); // 읽고 쓰기 용으로 db open.
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ( '" + edtName.getText().toString() + "', "
                + edtNumber.getText().toString() + ");");
                sqlDB.close();
                Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                btnSelect.callOnClick(); // 버튼 클릭 후 정보들 조회되도록! (자동으로 에디트텍스트 바뀜)
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getReadableDatabase(); // 읽기 전용으로 db open.
                Cursor cursor; // 커서 선언
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null); // 모든 테이블 조회한 후 커서에 대입
                // 테이블에 입력된 모든 행 데이터가 커서 변수에 들어있는 상태. 현재는 첫번째 행 가리키고 있다.
                String strNames = "그룹 이름" + "\r\n" + "---------" + "\r\n"; // \r\n -> 행을 넘겨서 다음 행부터 출력
                String strNumbers = "인원" + "\r\n" + "---------" + "\r\n";
                while(cursor.moveToNext()){ // moveToNext -> 커서 변수의 다음 행으로 넘어감
                    strNames += cursor.getString(0) + "\r\n"; // 0은 0번째 열(그룹 이름), 1은 1번째 열(인원)
                    strNumbers += cursor.getString(1) + "\r\n";
                }

                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);

                cursor.close();
                sqlDB.close();
            }
        });
        btnModify.setOnClickListener(new View.OnClickListener() { // 수정(인원 수)
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();// db open
                sqlDB.execSQL("UPDATE groupTBL SET gNumber = " + edtNumber.getText().toString() + " WHERE gName = '" +
                        edtName.getText().toString() + "';"); // 수정
                sqlDB.close();
                btnSelect.callOnClick();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '" + edtName.getText().toString() + "';");
                sqlDB.close();
                btnSelect.callOnClick();
            }
        });

    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY, gNumber INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // 초기화하는 함수
            db.execSQL("DROP TABLE IF EXISTS groupTBL"); // 지우고
            onCreate(db); // 다시 생성
        }
    }

}
