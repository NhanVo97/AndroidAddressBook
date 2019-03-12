package com.example.qlsll.API.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("mailAddress")
    @Expose
    private String mailAddress;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("setting")
    @Expose
    private String setting;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("lang")
    @Expose
    private String lang;
}
