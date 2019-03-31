package com.example.qlsll.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlsll.Fragment.FragmentListAddressBook;
import com.example.qlsll.Fragment.FragmentListGroupAddressBook;
import com.example.qlsll.Model.AddMember;
import com.example.qlsll.R;

import java.util.ArrayList;

public class AdapterMember extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AddMember> listMenber;

    public AdapterMember(Context context, LayoutInflater inflater, ArrayList<AddMember> listMenber) {
        this.context = context;
        this.inflater = inflater;
        this.listMenber = listMenber;
    }

    public AdapterMember(FragmentListAddressBook fragmentListAddressBook, int itemmenber) {
    }

    @Override
    public int getCount() {
        return listMenber.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView =inflater.inflate(R.layout.itemmenber,parent,false);
            holder.img_hinh=(ImageView) convertView.findViewById(R.id.img_hinh);
            holder.tv_ten=convertView.findViewById(R.id.tv_Name);
            holder.tv_mail=convertView.findViewById(R.id.tv_Email);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        AddMember menber=listMenber.get(position);
        holder.img_hinh.setImageResource(menber.getImg());
        holder.tv_ten.setText(menber.getName());
        holder.tv_mail.setText(menber.getEmail());
        return convertView;
    }
    public class ViewHolder{
        ImageView img_hinh;
        TextView tv_ten;
        TextView tv_mail;
    }
}
