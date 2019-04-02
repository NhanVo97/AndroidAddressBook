package com.example.qlsll.API.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GroupAddressBookResponse implements Serializable {
    @SerializedName("groupAddressId")
    @Expose
    private String groupAddressId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("totalmember")
    @Expose
    private Integer totalmember;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("modifyDate")
    @Expose
    private String modifyDate;

    public String getGroupAddressId() {
        return groupAddressId;
    }

    public void setGroupAddressId(String groupAddressId) {
        this.groupAddressId = groupAddressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTotalmember() {
        return totalmember;
    }

    public void setTotalmember(Integer totalmember) {
        this.totalmember = totalmember;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }
}
