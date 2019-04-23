package com.example.qlsll.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Response.PageResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AdminService;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Activity.add_addreadbook;
import com.example.qlsll.Activity.add_user;
import com.example.qlsll.Adapter.AdapterAutoCompleteAddressBook;
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
    private FloatingActionButton btn_adduser;
    private List<UserResponse> listUser;
    private AdapterUser adapterUser;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user, container, false);
        initData("");

        btn_adduser=v.findViewById(R.id.adduser_fab);
        recyclerView = v.findViewById(R.id.listUser);
        initData("");
        btn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), add_user.class);
                startActivity(intent);

            }
        });
        return v;


    }

    public void initData(String searchKey) {
        recyclerView = v.findViewById(R.id.listUser);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listUser = new ArrayList<>();
                    AdminService adminService = APIBaseService.getAdminAPIService();
                    // Param send to server
                    PageRequest pageRequest = new PageRequest();
                    pageRequest.setAscSort(true);
                    pageRequest.setPageNumber("1");
                    pageRequest.setSearchKey(searchKey);
                    pageRequest.setPageSize("10");
                    pageRequest.setSortCase(1);
                    // Get Data
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                    String accessToken = sharedPreferences.getString("Token", "");
                    String json = sharedPreferences.getString("User","");
                    if (!accessToken.isEmpty() && !json.isEmpty()) {
                        adminService.getListUserByAdmin(accessToken, pageRequest)
                                .observeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<APIResponse>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(APIResponse res) {
                                        apiResponse = res;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Response.toastError(getContext(), getResources().getString(R.string.err_get_data), Constant.TOASTSORT);
                                        Log.e("APIGETUSERBYADMIN", e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
                                        if (apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                            PageResponse pageResponse = new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) PageResponse.class);
                                            List<Object> listObject = pageResponse.getContent();
                                            listUser = (List<UserResponse>) (List<?>) listObject;
                                            // remove admin in list
                                            UserResponse currentUser = new Gson().fromJson(json,(Type) UserResponse.class);
                                            for(int i =0;i<listUser.size();i++){
                                                UserResponse userResponse = new Gson().fromJson(new Gson().toJson((listUser.get(i))),(Type) UserResponse.class);
                                                if(userResponse.getUserId().equals(currentUser.getUserId())){
                                                    listUser.remove(i);
                                                    break;
                                                }
                                            }
                                            adapterUser = new AdapterUser(listUser, getContext(), getFragmentManager(), accessToken);
                                            recyclerView.setHasFixedSize(true);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);
                                            recyclerView.setAdapter(adapterUser);
                                        } else {
                                            Response.APIToastError(getContext(), apiResponse.getStatus(), Constant.TOASTSORT);
                                        }

                                    }
                                });
                    } else {
                        backToHome();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }
    private void backToHome(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }
    @Override
    public void onItemClick(int position) {

    }

}
