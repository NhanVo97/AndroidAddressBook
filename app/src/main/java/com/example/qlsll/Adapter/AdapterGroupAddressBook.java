package com.example.qlsll.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlsll.API.Model.Response.AddressBookResponse;
import com.example.qlsll.API.Model.Response.GroupAddressBookResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Fragment.FragmentDetailUser;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class AdapterGroupAddressBook extends RecyclerView.Adapter<AdapterGroupAddressBook.ViewHolder> {
    List<GroupAddressBookResponse> listGroup;
    Context context;
    FragmentManager fragmentManager;
    String accessToken;
    public AdapterGroupAddressBook(List<GroupAddressBookResponse> listGroup, Context context, FragmentManager fragmentManager,String accessToken) {
        this.listGroup = listGroup;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.accessToken = accessToken;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.itemgroup,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GroupAddressBookResponse groupAddressBookResponse = new Gson().fromJson(new Gson().toJson((listGroup.get(position))),(Type) GroupAddressBookResponse.class);
        holder.nameGroup.setText(groupAddressBookResponse.getName());
        holder.description.setText(groupAddressBookResponse.getDescription());
        int index = position + 1;
        holder.count.setText(String.valueOf(index));
        holder.layoutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("AddressBook", groupAddressBookResponse);
                bundle.putString("accessToken",accessToken);
                FragmentDetailUser fragmentDetailUser = new FragmentDetailUser();
                fragmentDetailUser.setArguments(bundle);
                fragmentTransaction.replace(R.id.layout_fragmentuser,fragmentDetailUser).addToBackStack("tag").commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listGroup.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView nameGroup;
        TextView description;
        TextView count;
        ConstraintLayout layoutGroup;
        public ViewHolder(View itemView){
            super(itemView);
            nameGroup = itemView.findViewById(R.id.tvNameGroup);
            description= itemView.findViewById(R.id.tvDescription);
            layoutGroup = itemView.findViewById(R.id.layoutGroupAddressBook);
            count = itemView.findViewById(R.id.tvSTT);
            layoutGroup.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 4, 4, "Chỉnh sửa");
            menu.add(getAdapterPosition(), 5, 5, "Xóa");
        }
    }


}
