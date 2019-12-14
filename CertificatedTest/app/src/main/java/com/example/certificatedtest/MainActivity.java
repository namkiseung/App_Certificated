package com.example.certificatedtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1, btn2;
        btn1 = (Button)findViewById(R.id.btn_1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(this,).show();
                Intent intent = new Intent(getApplicationContext(), Videoview.class);
                startActivity(intent);
            }
        });
    }
}
