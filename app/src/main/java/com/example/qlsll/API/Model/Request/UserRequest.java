package com.example.qlsll.API.Model.Request;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRequest {
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Date dob;

    public UserRequest(String mailAddress, String passwordHash, String firstName, String lastName, String phone, String address, Date dob) {
        this.email = mailAddress;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
    }
}
