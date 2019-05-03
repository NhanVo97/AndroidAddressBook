package com.example.qlsll.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.qlsll.API.APIStatus;
import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.API.Model.Request.PageAddressBookRequest;
import com.example.qlsll.API.Model.Response.AddressBookResponse;
import com.example.qlsll.API.Model.Response.PageResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.API.Service.APIBaseService;
import com.example.qlsll.API.Service.AddressBookService;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Activity.ManagementActivity;
import com.example.qlsll.Activity.add_addreadbook;
import com.example.qlsll.Adapter.AdapterAddressBook;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;
import com.example.qlsll.Utils.Constant;
import com.example.qlsll.Utils.Response;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class FragmentListAddressBook extends Fragment {


    View v;
    private FloatingActionButton btn_addbook;
    private List<AddressBookResponse> listAddressBook;
    private AdapterAddressBook adapterAddressBook;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    private Dialog dialogAddAdressBook;
    private String accessToken;
    // Dialog
    Button btn_them;
    EditText ed_ho, ed_ten, ed_mail, ed_sdt, ed_congty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_addressbook, container, false);
        //        // Anh Xa
        btn_addbook = v.findViewById(R.id.fabAddressBook);
        recyclerView = v.findViewById(R.id.listAddressBook);
        // Get Data
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("Token", "");
        initData("");
        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddAdressBook = new Dialog(Objects.requireNonNull(getActivity()));
                dialogAddAdressBook.setContentView(R.layout.dialog_add_addreadbook);
                AnhXaDialog(dialogAddAdressBook);
                dialogAddAdressBook.show();
                HandleDialog();
            }
        });
        return v;

    }

    private void HandleDialog() {
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addHo, addTen, addMail, addSdt, addCongty;
                addHo = ed_ho.getText().toString();
                addTen = ed_ten.getText().toString();
                addMail = ed_mail.getText().toString();
                addSdt = ed_sdt.getText().toString();
                addCongty = ed_congty.getText().toString();

                if (checkError(addHo, addTen, addMail, addSdt, addCongty)) {
                    AddressBookRequest addressBookRequest = new AddressBookRequest(addHo, addTen, addMail, addSdt, addCongty);
                    if (!accessToken.isEmpty()) {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AddressBookService addressBookService = APIBaseService.getAddressBookAPIService();
                                addressBookService.addAddressBook(accessToken, addressBookRequest)
                                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<APIResponse>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(APIResponse res) {
                                        apiResponse = res;

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("API_ADD_ADDRESS", e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
                                        if (apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                            initData("");
                                            dialogAddAdressBook.dismiss();

                                        } else {
                                            Response.APIToastError(getContext(), apiResponse.getStatus(), Constant.TOASTSORT);
                                        }
                                    }
                                });
                            }
                        });
                        thread.start();

                    } else {
                        backToHome();
                    }
                }
            }
        });
    }

    private boolean checkError(String addHo, String addTen, String addMail, String addSdt, String addCongty) {
        if (addHo.isEmpty() || addTen.isEmpty() || addMail.isEmpty()) {
            Response.toastError(getContext(), "Nhập đủ thông tin", Constant.TOASTSORT);
            return false;
        } else {
            if (!CommonUtil.isEmailFormat(addMail)) {
                Response.toastError(getContext(), "Nhập lại Email", Constant.TOASTSORT);
                return false;
            }
            if (!addSdt.isEmpty()) {
                if (!CommonUtil.isPhoneNumberFormat(addSdt)) {
                    Response.toastError(getContext(), "Nhập lại số điện thoại", Constant.TOASTSORT);
                    return false;
                }
            }
        }
        return true;
    }

    private void AnhXaDialog(Dialog dialogAddAdressBook) {

        ed_ho = dialogAddAdressBook.findViewById(R.id.ed_firstName);
        ed_ten = dialogAddAdressBook.findViewById(R.id.ed_lastName);
        ed_mail = dialogAddAdressBook.findViewById(R.id.ed_email);
        ed_sdt = dialogAddAdressBook.findViewById(R.id.ed_phone);
        ed_congty = dialogAddAdressBook.findViewById(R.id.ed_company);
        btn_them = dialogAddAdressBook.findViewById(R.id.btn_addread);
    }

    public void initData(String searchKey) {
        listAddressBook = new ArrayList<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AddressBookService addressBookService = APIBaseService.getAddressBookAPIService();
                    // Param send to server
                    PageAddressBookRequest pageRequest = new PageAddressBookRequest();
                    pageRequest.setAscSort(true);
                    pageRequest.setPageNumber(1);
                    pageRequest.setSearchKey(searchKey);
                    pageRequest.setPageSize(10);
                    pageRequest.setSortCase(1);

                    if (!accessToken.isEmpty()) {
                        addressBookService.getListAddressBook(accessToken, pageRequest)
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
                                        Log.e("APIGETLISTADDRESSBOOK", e.toString());
                                    }

                                    @Override
                                    public void onComplete() {
                                        if (apiResponse.getStatus() == APIStatus.OK.getCode()) {
                                            PageResponse pageResponse = new Gson().fromJson(new Gson().toJson((apiResponse.getData())), (Type) PageResponse.class);
                                            List<Object> listObject = pageResponse.getContent();
                                            listAddressBook = (List<AddressBookResponse>) (List<?>) listObject;
                                            adapterAddressBook = new AdapterAddressBook(listAddressBook, getContext(), getFragmentManager(), accessToken);
                                            recyclerView.setHasFixedSize(true);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);
                                            recyclerView.setAdapter(adapterAddressBook);
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

    private void backToHome() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                break;
            case 3:
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", MODE_PRIVATE);
                String token = sharedPreferences.getString("Token", "");
                AddressBookResponse addressBookResponse = new Gson().fromJson(new Gson().toJson((listAddressBook.get(item.getGroupId()))),(Type) AddressBookResponse.class);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AddressBookService addressBookService = APIBaseService.getAddressBookAPIService();
                        addressBookService.delAddressBook(token, addressBookResponse.getAddressBookId())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object o) {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("AAA", "loi " + e);


                            }

                            @Override
                            public void onComplete() {
                                if(apiResponse.getStatus() == APIStatus.OK.getCode()){
                                    initData("");
                                } else {
                                    Response.APIToastError(getContext(),apiResponse.getStatus(),Constant.TOASTSORT);
                                }


                            }
                        });
                    }
                });
                thread.start();


                break;
        }
        return super.onContextItemSelected(item);


    }
}
