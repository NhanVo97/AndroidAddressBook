package com.example.qlsll.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.qlsll.API.Model.Response.UserResponse;
import com.example.qlsll.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class AdapterAutoCompleteAddressBook extends ArrayAdapter<UserResponse> {
    public List<UserResponse> listUser;

    public AdapterAutoCompleteAddressBook(@NonNull Context context,@NonNull List<UserResponse> objects) {
        super(context, 0, objects);
        listUser = new ArrayList<>(objects);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return  UserFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.itemsearch, parent, false
            );
        }
        TextView tvName = convertView.findViewById(R.id.tvUserName);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);

        ImageView imageViewFlag = convertView.findViewById(R.id.imAvt);

        UserResponse user = new Gson().fromJson(new Gson().toJson((listUser.get(position))),(Type) UserResponse.class);

        if (user != null) {
            tvName.setText(user.getFirstName() + " " + user.getLastName());
            tvEmail.setText(user.getMailAddress());
//            if(!user.getAvatar().equals("")) {
//                Picasso.get().load(user.getAvatar()).into(imageViewFlag);
//            }
//            else
//            {
//                imageViewFlag.setImageResource(R.drawable.noavt);
//            }
        }

        return convertView;
    }
    Filter UserFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            List<UserResponse> suggestions = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(listUser);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (int i = 0;i<listUser.size();i++) {
                    UserResponse userResponse = new Gson().fromJson(new Gson().toJson((listUser.get(i))),(Type) UserResponse.class);
                    if (userResponse.getFirstName().toLowerCase().contains(filterPattern) ||
                            userResponse.getLastName().toLowerCase().contains(filterPattern) ||
                            userResponse.getMailAddress().toLowerCase().contains(filterPattern) ) {
                        suggestions.add(userResponse);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            UserResponse user = new Gson().fromJson(new Gson().toJson(resultValue),(Type) UserResponse.class);
            return (user.getFirstName());
        }
    };

}