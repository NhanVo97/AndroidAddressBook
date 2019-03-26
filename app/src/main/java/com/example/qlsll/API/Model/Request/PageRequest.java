package com.example.qlsll.API.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageRequest {
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
    private String pageNumber;
    @SerializedName("pageSize")
    @Expose
    private String pageSize;

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

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
