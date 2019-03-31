package com.example.qlsll.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlsll.Fragment.FragmentListGroupAddressBook;
import com.example.qlsll.Model.AddMember;
import com.example.qlsll.Model.Group;
import com.example.qlsll.R;

import java.util.ArrayList;

public class AdapterGroup extends BaseAdapter {
    public AdapterGroup(Context context, LayoutInflater inflater, ArrayList<Group> listGroup) {
        this.context = context;
        this.inflater = inflater;
        this.listGroup = listGroup;
    }

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Group> listGroup;

    public AdapterGroup(FragmentListGroupAddressBook fragmentListGroupAddressBook, int itemgroup) {
    }

    @Override
    public int getCount() {
        return listGroup.size();
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
            holder= new ViewHolder();
            convertView =inflater.inflate(R.layout.itemgroup,parent,false);
            holder.img_group=(ImageView) convertView.findViewById(R.id.img_hinhnhom);
            holder.tv_nhom=convertView.findViewById(R.id.tv_tennhom);
            convertView.setTag(holder);
        }
        return convertView;
    }
    public class ViewHolder{
        ImageView img_group;
        TextView tv_nhom;
    }
}
