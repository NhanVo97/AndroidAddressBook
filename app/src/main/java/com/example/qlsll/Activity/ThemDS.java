package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlsll.R;

public class ThemDS extends AppCompatActivity {

    private Button them;
    private EditText ten ;
    private EditText mail;
    public ThemDS() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ds);

        them = (Button) findViewById(R.id.bt_them);
         ten = (EditText) findViewById(R.id.etten);
         mail = (EditText) findViewById(R.id.etmail);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ThemDS.this, DSActivity.class);
                String ten1=ten.getText().toString();
                String mail1=mail.getText().toString();
                intent.putExtra("ten",ten1);
                intent.putExtra("mail",mail1);
                //startActivity(intent);
                ThemDS.this.startActivity(intent);
            }
        });
    }

    }

