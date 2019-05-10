package com.example.qlsll.API.Service;

import com.example.qlsll.API.Model.APIResponse;
import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.API.Model.Request.PageAddressBookRequest;
import com.example.qlsll.API.Model.Request.PageRequest;
import com.example.qlsll.API.Model.Request.UserRequest;
import com.example.qlsll.API.Model.Response.AddressBookResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AddressBookService {
    @POST("address_book/list")
    Observable<APIResponse> getListAddressBook(@Header("X-Access-Token") String token, @Body PageAddressBookRequest pageRequest);
    @PUT("address_book")
    Observable<APIResponse> updateAddressBook(@Header("X-Access-Token") String token, @Body AddressBookRequest addressBookRequest);
    @POST("address_book")
    Observable<APIResponse> addAddressBook(@Header("X-Access-Token") String token, @Body AddressBookRequest addressBookRequest);
    @DELETE("address_book")
    Observable<APIResponse> delAddressBook(@Header("X-Access-Token") String token,  @Query("ids") String ids);

}
