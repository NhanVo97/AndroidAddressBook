package com.example.qlsll.API.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageAddressBookRequest {
    @SerializedName("searchKey")
    @Expose
    private String searchKey;
    @SerializedName("sortCase")
    @Expose
    private Integer sortCase;
    @SerializedName("ascSort")
    @Expose
    private Boolean ascSort;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("idGroup")
    @Expose
    private String idGroup;
    @SerializedName("action")
    @Expose
    private Integer action;

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getSortCase() {
        return sortCase;
    }

    public void setSortCase(Integer sortCase) {
        this.sortCase = sortCase;
    }

    public Boolean getAscSort() {
        return ascSort;
    }

    public void setAscSort(Boolean ascSort) {
        this.ascSort = ascSort;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
