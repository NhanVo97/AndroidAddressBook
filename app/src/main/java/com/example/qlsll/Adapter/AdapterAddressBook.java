package com.example.qlsll.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlsll.API.Model.Response.AddressBookResponse;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Fragment.FragmentDetaiAddressBook;
import com.example.qlsll.Fragment.FragmentDetailUser;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class AdapterAddressBook extends RecyclerView.Adapter<AdapterAddressBook.ViewHolder> {
    List<AddressBookResponse> listAddressBook;
    Context context;
    FragmentManager fragmentManager;
    String accessToken;
    public AdapterAddressBook(List<AddressBookResponse> listAddressBook, Context context, FragmentManager fragmentManager,String accessToken) {
        this.listAddressBook = listAddressBook;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.accessToken = accessToken;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.itemaddressbook,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AddressBookResponse addressBookResponse = new Gson().fromJson(new Gson().toJson((listAddressBook.get(position))),(Type) AddressBookResponse.class);
        holder.username.setText(addressBookResponse.getFirstName()+" "+addressBookResponse.getLastName());
        holder.mail.setText(addressBookResponse.getEmail());
        int index = position + 1;
        holder.count.setText(String.valueOf(index));
        holder.layoutAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAA",addressBookResponse.getEmail()+"");
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("AddressBook", addressBookResponse);
                bundle.putString("accessToken",accessToken);
                FragmentDetaiAddressBook fragmentDetaiAddressBook = new FragmentDetaiAddressBook();
                fragmentDetaiAddressBook.setArguments(bundle);
                fragmentTransaction.replace(R.id.layout_fragmentAddressBook,fragmentDetaiAddressBook).addToBackStack("tag").commit();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return listAddressBook.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder   implements View.OnCreateContextMenuListener{
        TextView username;
        TextView mail;
        TextView count;
        ConstraintLayout layoutAddressBook;
        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.tvUserName);
            mail= itemView.findViewById(R.id.tvEmail);
            count = itemView.findViewById(R.id.tvSTT);
            layoutAddressBook = itemView.findViewById(R.id.layoutAddressBook);
            layoutAddressBook.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(), 2, 2, "Chỉnh sửa");
            menu.add(getAdapterPosition(), 3, 3, "Xóa");

        }
    }
    public interface OnCallBack{
        void onItemClick(int position);
    }


}
