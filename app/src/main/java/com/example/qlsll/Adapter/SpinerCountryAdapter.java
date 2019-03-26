package com.example.qlsll.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qlsll.Model.Country;
import com.example.qlsll.R;

import java.util.List;

public class SpinerCountryAdapter extends ArrayAdapter<Country> {
    List<Country> listCountry;
    Context mContext;
    int resource;
    LayoutInflater inflter;
    public SpinerCountryAdapter(Context context, int resource,  List<Country> objects) {
        super(context, resource, objects);
        this.listCountry = objects;
        this.mContext = context;
        this.resource = resource;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return listCountry.size();
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinnercountry, null);
        ImageView imIcon = convertView.findViewById(R.id.imCountry);
        TextView tvCountry = convertView.findViewById(R.id.tvCountry);
        Country country = listCountry.get(position);
        imIcon.setBackgroundResource(country.getIcon());
        tvCountry.setText(country.getName());
        return convertView;
    }
}
