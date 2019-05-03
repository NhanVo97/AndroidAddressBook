package com.example.qlsll.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.R;

public class add_Group extends AppCompatActivity {
    EditText group_name,group_thongtin;
    Button btn_group;
    APIResponse apiResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_group);
        AnhXa();
    }
    void AnhXa(){
        group_name=findViewById(R.id.addgroup_name);
        group_thongtin=findViewById(R.id.addgroup_thongtin);
        btn_group=findViewById(R.id.addgroup_book);
    }
}
