package com.example.qlsll.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Response.GroupAddressBookResponse;
import com.example.qlsll.API.Model.Response.PageResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.GroupAddressBookService;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Adapter.AdapterGroupAddressBook;
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

public class FragmentListGroupAddressBook extends Fragment {
    View v;
    private List<GroupAddressBookResponse> groupAddressBookResponseList;
    private AdapterGroupAddressBook adapterGroupAddressBook;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_group_addressbook,container,false);
        initData("");
        return v;
    }
    public void initData(String searchKey){
        recyclerView = v.findViewById(R.id.listGroup);
        groupAddressBookResponseList = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GroupAddressBookService groupAddressBookAPIService = APIBaseService.getGroupAddressBookAPIService();
                    // Param send to server
                    PageRequest pageRequest = new PageRequest();
                    pageRequest.setAscSort(true);
                    pageRequest.setPageNumber("1");
                    pageRequest.setSearchKey(searchKey);
                    pageRequest.setPageSize("10");
                    pageRequest.setSortCase(1);
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                    String accessToken = sharedPreferences.getString("Token", "");
                    // Get Data
                    if(!accessToken.isEmpty()){
                        groupAddressBookAPIService.getListGroup(accessToken, pageRequest)
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
                                        Log.e("APIGETLISTGROUP", e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
                                        if (apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                            PageResponse pageResponse = new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) PageResponse.class);
                                            List<Object> listObject = pageResponse.getContent();
                                            groupAddressBookResponseList = (List<GroupAddressBookResponse>) (List<?>) listObject;
                                            adapterGroupAddressBook = new AdapterGroupAddressBook(groupAddressBookResponseList, getContext(), getFragmentManager(), accessToken);
                                            recyclerView.setHasFixedSize(true);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);
                                            recyclerView.setAdapter(adapterGroupAddressBook);
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
}
