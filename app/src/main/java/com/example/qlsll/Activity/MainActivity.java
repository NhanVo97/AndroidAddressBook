package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qlsll.R;

public class MainActivity extends AppCompatActivity {
    EditText ettk ;
    EditText etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ettk= findViewById(R.id.ettk);
        etpass=findViewById(R.id.etpass);
        String user = "sang";
        String pass ="123";


        Button btdn= (Button)this.findViewById(R.id.btdn) ;
        btdn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ettk.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập Email", Toast.LENGTH_SHORT).show();
                }
                if(etpass.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }

                if(ettk.getText().toString().equals("")&&etpass.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                if (!ettk.getText().toString().equals("")&&!etpass.getText().toString().equals("")){

                         if(ettk.getText().toString().equals("sang")&&etpass.getText().toString().equals("123")) {
                                 Intent myIntent = new Intent(MainActivity.this, DSActivity.class);
                                 MainActivity.this.startActivity(myIntent);
                         }
                        else{
                        Toast.makeText(MainActivity.this, "Thông tin sai", Toast.LENGTH_SHORT).show();
                    }
                }
                }

        });
        Button btdk= (Button)this.findViewById(R.id.btdk) ;
        btdk.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                 Intent myIntent = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(myIntent);

            }
        });
    }
}
