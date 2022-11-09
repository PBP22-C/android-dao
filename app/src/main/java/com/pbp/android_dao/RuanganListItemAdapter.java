package com.pbp.android_dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;

public class RuanganListItemAdapter extends ArrayAdapter<Ruangan> {
    ArrayList<Ruangan> ruangans;
    Context context;

    public RuanganListItemAdapter(ArrayList<Ruangan> ruangans, Context context) {
        super(context, R.layout.list_item, ruangans);
        this.ruangans = this.ruangans;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Ruangan ruangan = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvNamaRuangan = (TextView) convertView.findViewById(R.id.itemNamaRuangan);
        TextView tvKapasitas = (TextView) convertView.findViewById(R.id.itemKapasitas);
        TextView tvKodeGedung = (TextView) convertView.findViewById(R.id.itemKodeGedung);
        // Populate the data into the template view using the data object
        tvNamaRuangan.setText(ruangan.getNama());
        tvKapasitas.setText(Integer.toString(ruangan.getKapasitas()));
        tvKodeGedung.setText(ruangan.getKodeGedung());
        // Return the completed view to render on screen
        return convertView;
    }
}
