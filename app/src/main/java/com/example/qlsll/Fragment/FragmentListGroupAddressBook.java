package com.example.qlsll.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageAddressBookRequest;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Response.AddressBookResponse;
import com.example.qlsll.API.Model.Response.AdminResponse;
import com.example.qlsll.API.Model.Response.GroupAddressBookResponse;
import com.example.qlsll.API.Model.Response.PageResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AddressBookService;
import com.example.qlsll.API.Service.AdminService;
import com.example.qlsll.API.Service.GroupAddressBookService;
import com.example.qlsll.Activity.AdminManagementActivity;
import com.example.qlsll.Adapter.AdapterAddressBook;
import com.example.qlsll.Adapter.AdapterGroupAddressBook;
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

public class FragmentListGroupAddressBook extends Fragment implements View.OnKeyListener {
    View v;
    private List<GroupAddressBookResponse> groupAddressBookResponseList;
    private AdapterGroupAddressBook adapterGroupAddressBook;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    TextView tvName;
    EditText edSearch;
    ImageView imAvt;
    String accessToken;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_group_addressbook,container,false);
        // Anh Xa
        tvName = v.findViewById(R.id.username);
        edSearch = v.findViewById(R.id.searchBar);
        imAvt = v.findViewById(R.id.avatar);
        Bundle bundle = getArguments();
        // Get data from activity
        accessToken = bundle.getString("accessToken");
        AdminResponse adminResponse = (AdminResponse) bundle.getSerializable("Admin");
        if(adminResponse!=null)
        {
            tvName.setText(adminResponse.getFirstName());
        }
        edSearch.setOnKeyListener(this::onKey);
        imAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentDetailUser fragmentDetailUser = new FragmentDetailUser();
                bundle.putSerializable("Admin",adminResponse);
                bundle.putBoolean("isAdmin",true);
                fragmentDetailUser.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.layout_fragmentuser,fragmentDetailUser).addToBackStack("tag").commit();
            }
        });
        initData(accessToken,"");
        return v;
    }
    private void initData(String accessToken,String searchKey){
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
                    // Get Data
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)) {
            // Perform action on key press
            String searchKey = edSearch.getText().toString();
            this.initData(accessToken,searchKey);
            return true;
        }
        return false;
    }
}
