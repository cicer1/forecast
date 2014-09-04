package com.example.cicerone.first.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cicerone.first.Domain.Weather;
import com.example.cicerone.first.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by francesco on 04/09/14.
 */
public class ItemAdapter extends BaseAdapter {

    private
            Activity _activity;

    private
            ArrayList<Weather> _items;

    private static
            LayoutInflater _inflater = null;

    public ItemAdapter(Activity activity)
    {
        this._activity = activity;
        this._items = new ArrayList<Weather>();
        this._inflater =(LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ItemAdapter(Activity activity, ArrayList<Weather> data)
    {
        this._activity = activity;
        this._items = data;
        this._inflater =(LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear(){
        this._items.clear();
    }

    public ArrayList<Weather> get_items() {
        return _items;
    }

    public void addItem(Weather data){
        this._items.add(data);
    }

    @Override
    public int getCount() {
        return _items.size();
    }

    @Override
    public Object getItem(int position) {
        return this._items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;

        if (convertView == null) {
            vi = ItemAdapter._inflater.inflate(R.layout.list_item_forecast, null);
        }

        TextView title =(TextView) vi.findViewById(R.id.list_item_forecast_textview);

        title.setText(this._items.get(position).getDatetime() + ": " + this._items.get(position).getDescription());

        return vi;
    }
}
