package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by meeladsd on 11/5/2015.
 */
public class ImageAdapter extends BaseAdapter {

    private Context _cx;
    private ArrayList<Bitmap> _imgs;

    public ImageAdapter(Context cx, ArrayList<Bitmap> imgs)
    {
        _cx = cx;
        _imgs = imgs;
    }

    @Override
    public int getCount() {
        return _imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return _imgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(_cx);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(_imgs.get(position));
        return imageView;

    }
}
