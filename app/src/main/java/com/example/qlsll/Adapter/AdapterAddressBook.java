package com.example.qlsll.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qlsll.API.Model.Request.AddressBookRequest;
import com.example.qlsll.R;

import java.util.ArrayList;

public class AdapterAddressBook extends RecyclerView.Adapter<AdapterAddressBook.ViewHolder> {
    ArrayList<AddressBookRequest> AddressBookRequest;
    Context context;

    public AdapterAddressBook(ArrayList<AddressBookRequest> AddressBookRequest, Context context) {
        this.AddressBookRequest = AddressBookRequest;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.linearlayout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//            holder.ten.setText(AddressBookRequest.get(position).getTen());
//            holder.mail.setText(AddressBookRequest.get(position).getMail());
            holder.li1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public int getItemCount() {
        return AddressBookRequest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ten;
        TextView mail;
        LinearLayout li1;
        public ViewHolder(View itemView){
        super(itemView);
        ten=itemView.findViewById(R.id.ten);
        mail=itemView.findViewById(R.id.mail);
        li1= itemView.findViewById(R.id.li_ds);
        }
    }


}
