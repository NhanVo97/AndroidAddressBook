package com.example.qlsll.API.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionReponse {
    @SerializedName("tokenId")
    @Expose
    private String tokenId;
    @SerializedName("accountLoginId")
    @Expose
    private String accountLoginId;
    @SerializedName("expirationDate")
    @Expose
    private String expirationDate;
    @SerializedName("loginDate")
    @Expose
    private String loginDate;
    @SerializedName("sessionData")
    @Expose
    private String sessionData;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAccountLoginId() {
        return accountLoginId;
    }

    public void setAccountLoginId(String accountLoginId) {
        this.accountLoginId = accountLoginId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }
}
