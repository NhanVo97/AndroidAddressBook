package com.example.qlsll.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.Adapter.AdminManagementAdapter;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Management.Session;
import com.example.qlsll.Utils.Response;
import com.google.gson.Gson;

import android.support.design.widget.TabLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdminManagementActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    EditText searchBar;
    TextView tvUsername;
    ImageView imAvt;
    public static String accessToken;
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
        // send to fragment
        Bundle bundle = new Bundle();
        bundle.putString("accessToken",accessToken);
        FragmentListUser fragmentListUser = new FragmentListUser();
        fragmentListUser.setArguments(bundle);
        // init tablayout data
        init();
        // check role
        boolean isAdmin = intent.getBooleanExtra("isAdmin",false);
        if(isAdmin) handleAdmin(accessToken); else handleUser();
    }

    private void handleUser() {
        // user can't active in page admin,go back user to home
        backToHome();
    }
    private void init()
    {
        AdminManagementAdapter adminManagementAdapter = new AdminManagementAdapter(getSupportFragmentManager());
        adminManagementAdapter.addFragment(new FragmentListUser(),"Quản Lý User");
        adminManagementAdapter.addFragment(new FragmentListUser(),"Sổ địa chỉ");
        adminManagementAdapter.addFragment(new FragmentListUser(),"Nhóm địa chỉ");
        viewPager.setAdapter(adminManagementAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.usermanagement);
        tabLayout.getTabAt(1).setIcon(R.drawable.addressbook);
        tabLayout.getTabAt(2).setIcon(R.drawable.groupaddressbook);
    }
    private void handleAdmin(String accessToken) {

        if(!accessToken.isEmpty())
        {
            final APIResponse[] apiResponse = {new APIResponse()};
            UserService userService = APIBaseService.getUserAPIService();
            userService.getAdminProfile(accessToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<APIResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(APIResponse res) {
                            apiResponse[0] = res;
                        }

                        @Override
                        public void onError(Throwable e) {
                            Response.toastError(getApplicationContext(),"Fail to call API "+e.toString(), Constant.TOASTSORT);
                            backToHome();
                        }

                        @Override
                        public void onComplete() {
                            if(apiResponse[0].getStatus() == APIStatus.OK.getCode())
                            {
                                AdminResponse adminResponse = new Gson().fromJson(new Gson().toJson((apiResponse[0].getData())), (Type) AdminResponse.class);
                                tvUsername.setText(adminResponse.getFirstName());
                            }
                            else
                            {
                                Response.APIToastError(getApplicationContext(), apiResponse[0].getStatus(), Constant.TOASTSORT);
                            }
                        }
                    });
        }

    }
}
