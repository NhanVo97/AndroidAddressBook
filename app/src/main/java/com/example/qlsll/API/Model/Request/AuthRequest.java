package com.example.qlsll.API.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("passwordHash")
    @Expose
    private String passwordHash;
    @SerializedName("keepLogin")
    @Expose
    private Boolean keepLogin;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean getKeepLogin() {
        return keepLogin;
    }

    public void setKeepLogin(Boolean keepLogin) {
        this.keepLogin = keepLogin;
    }

}
