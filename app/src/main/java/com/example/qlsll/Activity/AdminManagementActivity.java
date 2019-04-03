package com.example.qlsll.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.Adapter.AdminManagementAdapter;
import com.example.qlsll.Fragment.FragmentListAddressBook;
import com.example.qlsll.Fragment.FragmentListGroupAddressBook;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.Fragment.UpdateAPI;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Management.Session;
import com.google.gson.Gson;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminManagementActivity extends AppCompatActivity implements UpdateAPI {

    TabLayout tabLayout;
    ViewPager viewPager;
    EditText searchBar;
    TextView tvUsername;
    ImageView imAvt;
    FragmentListUser fragmentListUser;
    FragmentListAddressBook fragmentListAddressBook;
    FragmentListGroupAddressBook fragmentListGroupAddressBook;
    Gson gson = new Gson();
    String accessToken;
    private void backToHome()
    {
        Intent backHome = new Intent(AdminManagementActivity.this,MainActivity.class);
        startActivity(backHome);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_management_activity);
        // Anh Xa
        tabLayout = findViewById(R.id.tablayour);
        viewPager = findViewById(R.id.viewpager);
        tvUsername = findViewById(R.id.username);
        imAvt = findViewById(R.id.avatar);
        searchBar = findViewById(R.id.searchBar);
        Intent intent = getIntent();
        accessToken = intent.getStringExtra("accessToken");
        // init tablayout data
        fragmentListUser = new FragmentListUser();
        Bundle bundle = new Bundle();
        bundle.putString("accessToken",accessToken);
        fragmentListUser.setArguments(bundle);
        fragmentListAddressBook = new FragmentListAddressBook();
        fragmentListAddressBook.setArguments(bundle);
        fragmentListGroupAddressBook = new FragmentListGroupAddressBook();
        fragmentListGroupAddressBook.setArguments(bundle);
        init();
        // check role
        boolean isAdmin = intent.getBooleanExtra("isAdmin",false);
        if(isAdmin) handleAdmin(); else handleUser();
    }

    private void handleUser() {
        // user can't active in page admin,go back user to home
        backToHome();
    }
    private void handleAdmin()
    {
        Session.initAdmin(getApplicationContext(),accessToken);
        SharedPreferences mPrefs = getSharedPreferences("Admin",MODE_PRIVATE);
        if(mPrefs!=null){
            String json = mPrefs.getString("Admin", "");
            AdminResponse adminResponse = gson.fromJson(json, AdminResponse.class);
            if(adminResponse!=null){
                Bundle bundleAdmin = new Bundle();
                bundleAdmin.putSerializable("Admin",adminResponse);
                bundleAdmin.putString("accessToken",accessToken);
                fragmentListGroupAddressBook.setArguments(bundleAdmin);
                fragmentListAddressBook.setArguments(bundleAdmin);
                fragmentListUser.setArguments(bundleAdmin);
            }
            else {
                backToHome();
            }
        }
    }
    private void init()
    {
        AdminManagementAdapter adminManagementAdapter = new AdminManagementAdapter(getSupportFragmentManager());
        adminManagementAdapter.addFragment(fragmentListUser,getResources().getString(R.string.user_management));
        adminManagementAdapter.addFragment(fragmentListAddressBook,getResources().getString(R.string.addressbook));
        adminManagementAdapter.addFragment(fragmentListGroupAddressBook,getResources().getString(R.string.group_addressbook));
        viewPager.setAdapter(adminManagementAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.usermanagement);
        tabLayout.getTabAt(1).setIcon(R.drawable.addressbook);
        tabLayout.getTabAt(2).setIcon(R.drawable.groupaddressbook);
    }


    @Override
    public void checkUpdate(boolean isCheck) {
       fragmentListUser.initData(accessToken,"");
    }
}
