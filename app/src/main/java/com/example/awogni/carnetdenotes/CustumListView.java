package com.example.awogni.carnetdenotes;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Awogni on 18/02/2018.
 */

/**
 * Callse permettant d'afficher des informations dans une listView
 */
public class CustumListView extends BaseAdapter {
    private Context context;
    private ArrayList<String> Liste = new ArrayList<String>();
    private LayoutInflater inflater;

    public CustumListView(Context  context , ArrayList<String> Liste)
    {
        this.Liste = Liste ;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return this.Liste.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.listview_layout ,null);

        TextView txt = (TextView)v.findViewById(R.id.txtUe);
        txt.setText(this.Liste.get(i));


        return v;

    }

}
