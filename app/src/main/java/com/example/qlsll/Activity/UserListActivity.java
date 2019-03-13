package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Adapter.AdapterAddressBook;
import com.example.qlsll.Adapter.AdapterUser;
import com.example.qlsll.R;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    AdapterUser adapterUser;
    public static ArrayList<UserResponse> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_user);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundleUser");
        UserResponse userResponse = (UserResponse) bundle.getSerializable("user");
        Toast.makeText(getApplicationContext(),userResponse.getAddress(),Toast.LENGTH_SHORT).show();

    }
}
