package com.example.qlsll.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Adapter.AdminManagementAdapter;
import com.example.qlsll.Fragment.FragmentDetailUser;
import com.example.qlsll.Fragment.FragmentListAddressBook;
import com.example.qlsll.Fragment.FragmentListGroupAddressBook;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.Fragment.HideModule;
import com.example.qlsll.Fragment.UpdateAPI;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Constant;
import com.google.gson.Gson;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ManagementActivity extends AppCompatActivity implements UpdateAPI, View.OnKeyListener, View.OnClickListener  {

    public TabLayout tabLayout;
    public ViewPager viewPager;
    EditText searchBar;
    TextView tvUsername;
    ImageView imAvt;
    FragmentListUser fragmentListUser;
    FragmentListAddressBook fragmentListAddressBook;
    FragmentListGroupAddressBook fragmentListGroupAddressBook;
    UserResponse currentUser;
    int keySearch = 1;
    public LinearLayout linearLayout;
    private void backToHome()
    {
        Intent backHome = new Intent(ManagementActivity.this,MainActivity.class);
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
        linearLayout = findViewById(R.id.layoutSearch);
        fragmentListUser = new FragmentListUser();
        fragmentListAddressBook = new FragmentListAddressBook();
        fragmentListGroupAddressBook = new FragmentListGroupAddressBook();
        // init data for viewpager & tablayout
        initData();
        // event onKey search
        searchBar.setOnKeyListener(this::onKey);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    keySearch = Constant.SEARCH_USER;
                } else if(tab.getPosition() == 1){
                    keySearch = Constant.SEARCH_ADDRESSBOOK;
                } else if(tab.getPosition() == 2){
                    keySearch = Constant.SEARCH_GROUP;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imAvt.setOnClickListener(this::onClick);

    }

    private void initData()
    {
        AdminManagementAdapter adminManagementAdapter = new AdminManagementAdapter(getSupportFragmentManager());
        SharedPreferences mPrefs = getSharedPreferences("User",MODE_PRIVATE);
        if(mPrefs!=null){
            String json = mPrefs.getString("User", "");
            try {
                JSONObject jsonObject = new JSONObject(json);
                Log.e("JSON",jsonObject.toString());
                String role = jsonObject.getString("role");
                if(!role.isEmpty()){
                    currentUser = new Gson().fromJson(json,UserResponse.class);
                    // set data
                    tvUsername.setText(currentUser.getFirstName());
                    if(role.equals("USER")){
                        // set data for view pager
                        adminManagementAdapter.addFragment(fragmentListAddressBook,getResources().getString(R.string.addressbook));
                        adminManagementAdapter.addFragment(fragmentListGroupAddressBook,getResources().getString(R.string.group_addressbook));
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setAdapter(adminManagementAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setCurrentItem(0);
                        viewPager.setOffscreenPageLimit(2);
                        tabLayout.getTabAt(0).setIcon(R.drawable.addressbook);
                        tabLayout.getTabAt(1).setIcon(R.drawable.groupaddressbook);
                    } else {
                        // set data for view pager
                        adminManagementAdapter.addFragment(fragmentListUser,getResources().getString(R.string.user_management));
                        adminManagementAdapter.addFragment(fragmentListAddressBook,getResources().getString(R.string.addressbook));
                        adminManagementAdapter.addFragment(fragmentListGroupAddressBook,getResources().getString(R.string.group_addressbook));
                        viewPager.setAdapter(adminManagementAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setCurrentItem(0);
                        viewPager.setOffscreenPageLimit(3);
                        tabLayout.getTabAt(0).setIcon(R.drawable.usermanagement);
                        tabLayout.getTabAt(1).setIcon(R.drawable.addressbook);
                        tabLayout.getTabAt(2).setIcon(R.drawable.groupaddressbook);
                    }

                } else {
                    // user not found
                    backToHome();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            backToHome();
        }


    }


    @Override
    public void checkUpdate(boolean isCheck) {
       fragmentListUser.initData("");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
                switch (keySearch){
                    case Constant.SEARCH_USER :
                        fragmentListUser.initData(searchBar.getText().toString());
                        break;
                    case Constant.SEARCH_ADDRESSBOOK:
                        fragmentListAddressBook.initData(searchBar.getText().toString());
                        break;
                    case Constant.SEARCH_GROUP:
                        fragmentListGroupAddressBook.initData(searchBar.getText().toString());
                        break;
                }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.avatar:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentDetailUser fragmentDetailUser = new FragmentDetailUser();
                Bundle bundle = new Bundle();
                bundle.putSerializable("User",currentUser);
                fragmentDetailUser.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.MainFrame,fragmentDetailUser).addToBackStack("tag").commit();
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        List<Fragment> frags = getSupportFragmentManager().getFragments();
        if(frags.size()>0){
            for(Fragment f : frags) {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.title_back_dialog));
            builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    System.exit(0);


                }
            });
            builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);


    }
}
