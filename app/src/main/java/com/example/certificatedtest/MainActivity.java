package com.example.certificatedtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{
    Button btn1, btn2, btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.btn_1);btn1.setOnClickListener(this);
        btn2 = (Button)findViewById(R.id.btn_2);btn2.setOnClickListener(this);
        btn3 = (Button)findViewById(R.id.btn_3);btn3.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1: Intent intent1 = new Intent(getApplicationContext(), VideoviewActivity.class); startActivity(intent1);break;
            case R.id.btn_2: Intent intent2 = new Intent(getApplicationContext(), TextviewActivity.class); startActivity(intent2);break;
            case R.id.btn_3: Intent intent3 = new Intent(getApplicationContext(), MylistviewActivity.class); startActivity(intent3);break;
        }
    }
}
