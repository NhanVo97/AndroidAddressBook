package com.example.qlsll.API.Model.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AddressBookRequest implements Serializable {
    private String addressBookId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String userId;
    private int status;
    private Date createDate;
    private Date modifyDate;
}
