package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by alem1324 on 12/1/2015.
 */
public class MemoriesAdapter extends BaseAdapter {

    private Context _con;
    private ArrayList<String> _memTitles, _memIds, _memUrl;

    public MemoriesAdapter(Context c, ArrayList<String> titles, ArrayList<String> urls, ArrayList<String> ids) {
        _memIds = ids;
        _memTitles = titles;
        _memUrl = urls;
        _con = c;
    }

    @Override
    public int getCount() {
        return _memIds.size();
    }

    @Override
    public Object getItem(int position) {
        return _memTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(_con).
                inflate(R.layout.two_line_icon, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.text1);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        if(_memUrl.get(position) != "")
        {
            Picasso.with(rowView.getContext()).load("https://mybuckpics.s3.amazonaws.com/"+_memUrl.get(position)).into(icon);
        }

        text1.setText(_memTitles.get(position));


        rowView.setTag(_memIds.get(position));


        return rowView;
    }
}
