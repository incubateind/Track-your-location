package com.example.mapsincubate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GetLoader extends BaseAdapter {
    LayoutInflater inf;
    Context cnt;
    ArrayList<String>nm,mb;


    public GetLoader(Context context,ArrayList<String >name,ArrayList<String>mobile){

        nm=name;
        mb=mobile;

        cnt = context;
        inf= LayoutInflater.from(cnt);

    }
    public int getCount() {
        return nm.size();
    }

    @Override
    public Object getItem(int i) {
        return nm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewGroup =(ViewGroup)inf.inflate(R.layout.lv_layout,null);
        TextView t_1,t_2;
        t_1=(TextView)viewGroup.findViewById(R.id.tv_name);
        t_2=(TextView)viewGroup.findViewById(R.id.tv_mob);

//Ask about how the string literal is transfered to the list when the arraylist taken is in Integer format/...?

        t_1.setText("Name: "+nm.get(i));
        t_2.setText("Mobile: "+mb.get(i));

        return viewGroup;
    }
}
