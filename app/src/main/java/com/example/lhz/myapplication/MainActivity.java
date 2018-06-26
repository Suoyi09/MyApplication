package com.example.lhz.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;
    Button bt1,bt2,bt3,bt4,bt5,bt6,bt7,bt8;
    private Context mcontext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout=  findViewById(R.id.root);
        gridLayout.setColumnCount(2);
        gridLayout.setRowCount(4);
        bt1=findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,calculateActivity.class);
                startActivity(i);
            }
        });
        bt2=findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,weatherActivity.class);
                startActivity(i);
            }
        });
        bt3=findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,musicActivity.class);
                startActivity(i);
            }
        });
        bt4=findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,DeliveryActivity.class);
                startActivity(i);
            }
        });
        bt5=findViewById(R.id.bt5);
        bt5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,NoteMainActivity.class);
                startActivity(i);
            }
        });
        bt6=findViewById(R.id.bt6);
        bt6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,mobilephoneActivity.class);
                startActivity(i);
            }
        });
        bt7=findViewById(R.id.bt7);
        bt7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,Activity2048.class);
                startActivity(i);
            }
        });
        bt8=findViewById(R.id.bt8);
        bt8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mcontext,pinballActivity.class);
                startActivity(i);
            }
        });

    }
}
