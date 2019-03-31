package com.example.qlsll.Model;

public class Group  {
    private int hinh;
    private String ten;

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Group(int hinh, String ten) {
        this.hinh = hinh;
        this.ten = ten;
    }
}
