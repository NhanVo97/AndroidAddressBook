package com.example.qlsll.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.Activity.MainActivity;
import com.example.qlsll.Fragment.FragmentDetailUser;
import com.example.qlsll.Fragment.FragmentListUser;
import com.example.qlsll.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {
    List<UserResponse> listUser;
    Context context;
    FragmentManager fragmentManager;
    String accessToken;
    public AdapterUser(List<UserResponse> listUser, Context context, FragmentManager fragmentManager,String accessToken) {
        this.listUser = listUser;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.accessToken = accessToken;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.itemuser,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UserResponse userResponse = new Gson().fromJson(new Gson().toJson((listUser.get(position))),(Type) UserResponse.class);
        holder.username.setText(userResponse.getFirstName());
        holder.mail.setText(userResponse.getMailAddress());
        int index = position + 1;
        holder.count.setText(String.valueOf(index));
        holder.layoutUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("User", (Serializable) userResponse);
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
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView mail;
        TextView count;
        ConstraintLayout layoutUserClick;
        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.tvUserName);
            mail= itemView.findViewById(R.id.tvEmail);
            count = itemView.findViewById(R.id.tvSTT);
            layoutUserClick = itemView.findViewById(R.id.layoutUserClick);
        }
    }
    public interface OnCallBack{
        void onItemClick(int position);
    }


}
