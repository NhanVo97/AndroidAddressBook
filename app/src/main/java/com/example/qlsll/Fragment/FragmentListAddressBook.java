package com.example.qlsll.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.qlsll.Adapter.AdapterMember;
import com.example.qlsll.Model.AddMember;
import com.example.qlsll.R;

import java.util.ArrayList;

public class FragmentListAddressBook extends Fragment {
    View v;
    ListView listViewMenber;

    ArrayList<AddMember> listMember= new ArrayList<>();
     AdapterMember adapterMember;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_addressbook,container,false);
        AnhXa();
        adapterMember= new AdapterMember(this, R.layout.itemmenber);
        listViewMenber.setAdapter(adapterMember);
        return v;
     }
        private void AnhXa(){
        listViewMenber=v.findViewById(R.id.lv_addmenber);
        listMember.add(new AddMember("sang","sang0968991331",R.drawable.common_google_signin_btn_icon_dark));
     }
}
