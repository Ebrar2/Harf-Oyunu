package com.example.yazlab22;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SonEkran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_son_ekran);
        Intent intent=getIntent();
        String alinanSkor=String.valueOf(intent.getIntExtra("puan",0));
        TextView skor=findViewById(R.id.puantext);
        skor.setText("PUANINIZ:"+alinanSkor);

        Button button=findViewById(R.id.yeniOyun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SonEkran.this,OyunEkrani.class);
                startActivity(intent);
            }
        });
    }
}