package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.qlsll.Adapter.AdapterAddressBook;
import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.R;

import java.util.ArrayList;

public class DSActivity extends AppCompatActivity {
    public static ArrayList<AddressBookRequest> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds);
        initView();
        RelativeLayout r1 = findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DSActivity.this, MainActivity.class), 0);
            }
        });
        RelativeLayout themds =  findViewById(R.id.themds);
        themds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DSActivity.this, ThemDS.class), 0);
            }
        });
    }

    public void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclervier);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent= getIntent();
        String ten1=intent.getStringExtra("ten");
        String mail1=intent.getStringExtra("mail");
        if(ten1!=null || mail1!=null)
        {
            //AddressBookRequest AddressBookRequest = new AddressBookRequest(ten1,mail1);
           // arrayList.add(AddressBookRequest);
        }

        AdapterAddressBook adapterAddressBook = new AdapterAddressBook(arrayList, this);
        recyclerView.setAdapter(adapterAddressBook);


    }
}


