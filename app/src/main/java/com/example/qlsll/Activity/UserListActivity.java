package com.example.qlsll.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Adapter.AdapterUser;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Management.Session;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    AdapterUser adapterUser;
    public static List<UserResponse> listUser = new ArrayList<>();

}
