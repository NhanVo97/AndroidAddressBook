package com.example.qlsll.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.R;
import com.example.qlsll.Utils.CommonUtil;

import org.w3c.dom.Text;

public class FragmentDetailUser extends Fragment {
    View v;
    EditText edFirstName,edLastName,edEmail,edbirthday,edAddress,edPhone;
    Spinner spLanguage;
    TextView tvInfoOf,tvCreateDate,tvStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detailuser,container,false);
        UserResponse userResponse = (UserResponse) getArguments().getSerializable("User");
        // Anh Xa
        edFirstName = v.findViewById(R.id.firstNameUser);
        edLastName = v.findViewById(R.id.lastNameUser);
        edEmail = v.findViewById(R.id.emailUser);
        edPhone = v.findViewById(R.id.phoneUser);
        edbirthday = v.findViewById(R.id.birthdayUser);
        edAddress = v.findViewById(R.id.address);
        spLanguage = v.findViewById(R.id.spinnerLanguage);
        tvInfoOf = v.findViewById(R.id.detailUserName);
        tvCreateDate = v.findViewById(R.id.createDateUser);
        tvStatus = v.findViewById(R.id.statusUser);
        if(userResponse!=null)
        {
            edFirstName.setText(userResponse.getFirstName());
            edLastName.setText(userResponse.getLastName());
            edEmail.setText(userResponse.getMailAddress());
            edPhone.setText(userResponse.getPhone());
//            edAddress.setText(userResponse.getAddress());
            edbirthday.setText(userResponse.getDob());
            tvCreateDate.setText(userResponse.getSignupDate());
            tvInfoOf.setText(getResources().getString(R.string.informationOF) +" " + userResponse.getFirstName());
            tvStatus.setText(CommonUtil.getStatus(userResponse.getStatus()));
        }
        return v;
    }
}
