package com.example.qlsll.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Model.Response.PageResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.UserService;
import com.example.qlsll.Activity.AdminManagementActivity;
import com.example.qlsll.Adapter.AdapterUser;
import com.example.qlsll.R;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Response;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FragmentListUser extends Fragment implements AdapterUser.OnCallBack {
    View v;
    private List<UserResponse> listUser;
    private AdapterUser adapterUser;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user,container,false);
        String accessToken = AdminManagementActivity.accessToken;
        // Anh Xa
        recyclerView = v.findViewById(R.id.listUser);
        initData(accessToken);
        return v;
    }
    public void initData(String accessToken) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
        listUser = new ArrayList<>();
        UserService userService = APIBaseService.getUserAPIService();
        // Param send to server
        PageRequest pageRequest = new PageRequest();
        pageRequest.setAscSort(true);
        pageRequest.setPageNumber("1");
        pageRequest.setSearchKey("");
        pageRequest.setPageSize("10");
        pageRequest.setSortCase(1);
        // Get Data
        final APIResponse[] apiResponse = new APIResponse[1];
        userService.getListUserByAdmin(accessToken,pageRequest)
                .observeOn(Schedulers.io())
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
                        Response.toastError(getContext(),e.toString(), Constant.TOASTSORT);
                    }

                    @Override
                    public void onComplete() {
                        PageResponse pageResponse = new Gson().fromJson(new Gson().toJson((apiResponse[0].getData())),(Type) PageResponse.class);
                        List<Object> listObject = pageResponse.getContent();
                        listUser = (List<UserResponse>)(List<?>)listObject;
                        Log.e("AAA",listUser.toString());
                        adapterUser = new AdapterUser(listUser,getContext(), getFragmentManager(),accessToken);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapterUser);
                    }
                });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public void onItemClick(int position) {

    }


}
