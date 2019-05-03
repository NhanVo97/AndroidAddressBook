package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.PageAddressBookRequest;
import com.example.qlsll.API.Model.Request.PageRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GroupAddressBookService {
    @POST("group_addressbook/list")
    Observable<APIResponse> getListGroup(@Header("X-Access-Token") String token, @Body PageRequest pageRequest);
    @DELETE("group_addressbook")
    Observable<APIResponse> delGroup(@Header("X-Access-Token") String token,  @Query("ids") String ids);
}
