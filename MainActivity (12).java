package com.cookandroid.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

// 브로드캐스트 리시버
// 배터리 상태에 따라 토스트 메시지 보낸다.
// 직접 풀어보기 14-2

public class MainActivity extends AppCompatActivity {

    ImageView ivBattery;
    EditText edtBattery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        ivBattery = (ImageView) findViewById(R.id.ivBattery);
        edtBattery = (EditText) findViewById(R.id.edtBattery);



    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) { // 배터리가 변하면 onReceive() 메소드 자동으로 호출.
            String action = intent.getAction(); // onResume() 메소드를 호출한 액션을 가져온다.

            if(action.equals(Intent.ACTION_BATTERY_CHANGED)){ // 액션이 ACTION_BATTERY_CHANGED라면 실행
                int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0); // 배터리의 잔량 가져옴
                edtBattery.setText("현재 충전량 : " + remain + "%\n");

                if(remain >= 90) ivBattery.setImageResource(R.drawable.battery100);
                else if (remain >= 70) ivBattery.setImageResource(R.drawable.battery75);
                else if (remain >= 50) ivBattery.setImageResource(R.drawable.battery50);
                else if (remain >= 10) ivBattery.setImageResource(R.drawable.battery25);
                else ivBattery.setImageResource(R.drawable.battery0);

                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0); // 배터리의 외부 전원 연결 상태 구함
                switch (plug){
                    case 0: // 연결이 안 된 상태
                        edtBattery.append("전원 연결 : 안 됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC: // 어댑터 연결 상태
                        edtBattery.append("전원 연결 : 어댑터 연결됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB: // USB 연결 상태
                        edtBattery.append("전원 연결 : USB 연결됨");
                        break;

                }

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0); // 배터리의 상태 구함
                switch (status){
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        Toast.makeText(getApplicationContext(), "배터리 상태 : 헌재 충전 중임", Toast.LENGTH_SHORT).show();
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        Toast.makeText(getApplicationContext(), "배터리 상태 : 헌재 충전 중이 아님", Toast.LENGTH_SHORT).show();
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        Toast.makeText(getApplicationContext(), "배터리 상태 : 충전 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        Toast.makeText(getApplicationContext(), "배터리 상태 : 헌재 충전 중이 아님", Toast.LENGTH_SHORT).show();
                        break;
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        Toast.makeText(getApplicationContext(), "배터리 상태 : 알 수 없음", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    };

    @Override
    protected void onPause() { // 등록된 BR을 해제한다
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    protected void onResume() { // 인텐트 필터를 생성하고 ACTION_BATTERY_CHANGED 액션을 추가한 뒤 BR에 등록한다.
        super.onResume();
        IntentFilter iFilter = new IntentFilter(); // 인텐트 필터 객체 생성.
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED); // 액션 추가
        registerReceiver(br, iFilter); // 인텐트 필터를 BR에 등록
    }
}