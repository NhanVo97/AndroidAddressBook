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

public class SpinnerCountryAdapter extends ArrayAdapter<Country> {
    List<Country> listCountry;
    Context mContext;
    LayoutInflater inflter;
    int resource;
    public SpinnerCountryAdapter(Context mContext, int resource,List<Country> listCountry) {
        super(mContext,resource,listCountry);
        this.mContext = mContext;
        this.listCountry = listCountry;
        this.resource = resource;
        inflter = (LayoutInflater.from(mContext));
    }

    @Override
    public int getCount() {
        return listCountry.size();
    }

    @Override
    public Country getItem(int position) {
        return listCountry.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinnercountry, parent, false
            );
        }
        ImageView imIcon = convertView.findViewById(R.id.imCountry);
        TextView tvCountry = convertView.findViewById(R.id.tvLanguage);
        Country country = listCountry.get(position);
        imIcon.setBackgroundResource(country.getIcon());
        tvCountry.setText(country.getName());
        return convertView;
    }
}
