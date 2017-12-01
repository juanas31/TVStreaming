package com.giviews.tvstreaming;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

    String channel[];
    String nama[];
    int gambar[];

    Activity act;

    public CustomListAdapter(MainActivity mainActivity, String[] channel, int[] gambar, String[] nama) {
        channel=channel;
        this.gambar = gambar;
        this.nama = nama;
        act=mainActivity;


    }

    @Override
    public int getCount() {
        //berapa banyak data yg di tampilkan
        return gambar.length;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.tampilancustomlist,null);

        ImageView gambarTV = (ImageView)convertView.findViewById(R.id.gambar);
        TextView namaTV = (TextView)convertView.findViewById(R.id.nama);

        namaTV.setText(nama[i]);
        gambarTV.setImageResource(gambar[i]);

        return convertView;
    }

    //Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}


