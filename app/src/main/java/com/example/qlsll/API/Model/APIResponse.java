package com.example.qlsll.API.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
// lấy dữ liệu backend về
public class APIResponse {// class trả về của API
    // cai nay
    @SerializedName("status")// báo lỗi
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data") //
    @Expose
    private Object data; // dâta trả về

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
