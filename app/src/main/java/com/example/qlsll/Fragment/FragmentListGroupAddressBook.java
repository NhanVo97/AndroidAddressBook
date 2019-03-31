package com.example.qlsll.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.qlsll.Adapter.AdapterGroup;
import com.example.qlsll.Adapter.AdapterMember;
import com.example.qlsll.Model.AddMember;
import com.example.qlsll.Model.Group;
import com.example.qlsll.R;

import java.util.ArrayList;

public class FragmentListGroupAddressBook extends Fragment {
    View v;
    ListView listViewGroup;

    ArrayList<Group> listGroup= new ArrayList<>();
    AdapterGroup adapterGroup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_group_addressbook,container,false);
        AnhXa();
        adapterGroup= new AdapterGroup(this, R.layout.itemgroup);
        listViewGroup.setAdapter(adapterGroup);
        return v;
    }
    public void AnhXa(){
        listViewGroup=(ListView) v.findViewById(R.id.lv_group);
        listGroup.add(new Group(R.drawable.common_google_signin_btn_icon_dark_normal,"gia dinh"));
    }
}
