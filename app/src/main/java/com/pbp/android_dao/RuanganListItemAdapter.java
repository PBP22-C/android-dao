package com.pbp.android_dao;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;

public class RuanganListItemAdapter extends ArrayAdapter<Ruangan> {
    ArrayList<Ruangan> ruangans;
    Context context;
    private final AppDatabase db;
    private Spinner spinner;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public RuanganListItemAdapter(ArrayList<Ruangan> ruangans, Context context, AppDatabase db) {
        super(context, R.layout.list_item, ruangans);
        this.ruangans = ruangans;
        this.context = context;
        this.db = db;
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
        Button btnUbah = (Button) convertView.findViewById(R.id.btnUbah);
        Button btnHapus = (Button) convertView.findViewById(R.id.btnHapus);

        // Populate the data into the template view using the data object
        tvNamaRuangan.setText(ruangan.getNama());
        tvKapasitas.setText("Kapasitas: " + Integer.toString(ruangan.getKapasitas()));
        tvKodeGedung.setText(ruangan.getKodeGedung());

        btnUbah.setOnClickListener(view -> {
            showFormUbahRuangan(view, ruangan);
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask.execute(() -> {
                    db.ruanganDAO().delete(ruangan);
                    ruangans.remove(ruangan);
                });
                notifyDataSetChanged();
                Toast.makeText(context, "Ruangan berhasil dihapus", Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void showFormUbahRuangan(View v, Ruangan ruangan){
        dialogBuilder = new AlertDialog.Builder(context);
        final View formPopupView = LayoutInflater.from(getContext()).inflate(R.layout.edit_ruangan_modal, null);
        EditText editKodeRuang = (EditText) formPopupView.findViewById(R.id.editKodeRuang);
        EditText editNamaRuang = (EditText) formPopupView.findViewById(R.id.editNamaRuang);
        EditText editKapasitas = (EditText) formPopupView.findViewById(R.id.editKapasitas);
        spinner = (Spinner) formPopupView.findViewById(R.id.spinnerEditRuang);
        Button btnSimpan = (Button) formPopupView.findViewById(R.id.btnUbahData);
        Button btnBatal = (Button) formPopupView.findViewById(R.id.btnBatal);

        editKodeRuang.setText(ruangan.getKodeRuangan());
        editNamaRuang.setText(ruangan.getNama());
        editKapasitas.setText(Integer.toString(ruangan.getKapasitas()));

        btnSimpan.setOnClickListener(view -> {
            AsyncTask.execute(() -> {
                ruangan.setNama(editNamaRuang.getText().toString());
                ruangan.setKapasitas(Integer.parseInt(editKapasitas.getText().toString()));
                ruangan.setKodeGedung(editKodeRuang.getText().toString());
                db.ruanganDAO().update(ruangan);
                ((MainActivity) context).runOnUiThread(() -> {
                    notifyDataSetChanged();
                    Toast.makeText(context, "Ruangan berhasil diubah", Toast.LENGTH_SHORT).show();
                });
            });
            dialog.dismiss();
        });

        btnBatal.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialogBuilder.setView(formPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }
}
