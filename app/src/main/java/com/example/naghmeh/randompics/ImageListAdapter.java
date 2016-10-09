package com.example.naghmeh.randompics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naghmeh on 10/9/16.
 */
public class ImageListAdapter extends ArrayAdapter<String> {
    List<String> items;

    public ImageListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        Picasso.with(getContext())
                .load(items.get(position))
                .into((ImageView) convertView);

        return convertView;
    }
}
