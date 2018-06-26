package com.example.lhz.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class NoteMenuActivity extends Activity {
    private static final int ACTIVITY_CREATE = 0;
    private Context mcontext=this;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_menu);
        Button allButton = (Button) findViewById(R.id.all);
        Button newButton = (Button) findViewById(R.id.newinsert);
        Button exitButton = (Button) findViewById(R.id.exitp);

        allButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(mcontext, NoteMainActivity.class);
                startActivityForResult(i, ACTIVITY_CREATE);
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(mcontext, NoteEditActivity.class);
                startActivityForResult(i, ACTIVITY_CREATE);
            }

        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(mcontext, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}

