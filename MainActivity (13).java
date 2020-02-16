package com.cookandroid.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

// 콘텐트 프로바이더
// 통화 기록 가져오는 코드
// 예제 14-10, 14-11

public class MainActivity extends AppCompatActivity {

    Button btnCall;
    EditText edtCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        //퍼미션 직접 허용하는 코드
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.READ_CALL_LOG}, MODE_PRIVATE);
        btnCall = (Button) findViewById(R.id.btnCall);
        edtCall = (EditText) findViewById(R.id.edtCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCall.setText(getCallHistory());
            }
        });



    }

    public String getCallHistory(){ // 통화 기록의 내용 검색해서 통화 기록을 문자열로 만들어 반환하는 함수.
        //통화 날짜, 발신 또는 착신 여부, 전화번호, 통화시간에 대한 문자열 배열
        String[] callSet = new String[] {CallLog.Calls.DATE, CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        // 통화 기록에 대해 callSet에서 설정한 내용 조회.
        // getContentResolver()는 ContentResolver 반환, ContentResolver는 query(), insert(), delete(), update() 사용하여 데이터 접근 및 변경 가능.
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, callSet, null, null, null);
        if(c == null) return "통화기록 없음"; // 통화 기록이 한 건도 없다면 종료한다.
        StringBuffer callBuff = new StringBuffer(); // 통화 기록의 문자열 저장할 변수
        callBuff.append("\n날짜 : 구분 : 전화번호 : 통화시간\n\n"); // 제목
        c.moveToFirst(); // 통화 기록의 처음 행으로 이동
        do{
            long callDate = c.getLong(0); // 처음 필드(0번째)를 가져와서
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = datePattern.format(new Date(callDate));
            callBuff.append(date_str + ":");// 날짜 형식으로 버퍼에 저장.
            if(c.getInt(1) == CallLog.Calls.INCOMING_TYPE) callBuff.append("착신 : "); // 첫번째 필드의 데이터 문자열을 버퍼에 저장. (발신인지 착신인지)
            else callBuff.append("발신 : ");
            callBuff.append(c.getString(2) + ":"); // 두번째 필드의 내용(전화번호) 버퍼에 저장.
            callBuff.append(c.getString(3) + "초\n"); // 세번째 필드의 내용(통화시간) 버퍼에 저장.
        } while (c.moveToNext());

        c.close();
        return callBuff.toString(); // 저장한 모든 정보 반환.
    }


}