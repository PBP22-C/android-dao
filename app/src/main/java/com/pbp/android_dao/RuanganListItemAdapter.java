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
import com.pbp.android_dao.entity.Gedung;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;
import java.util.List;

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
        TextView tvNamaGedung = (TextView) convertView.findViewById(R.id.itemNamaGedung);
        Button btnUbah = (Button) convertView.findViewById(R.id.btnUbah);
        Button btnHapus = (Button) convertView.findViewById(R.id.btnHapus);

        // Populate the data into the template view using the data object
        tvNamaRuangan.setText(ruangan.getKodeRuangan() + " - " + ruangan.getNama());
        tvKapasitas.setText("Kapasitas: " + Integer.toString(ruangan.getKapasitas()));
        tvNamaGedung.setText("Gedung: " + db.gedungDAO().findNamaGedungByKode(ruangan.getKodeGedung()));

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
        getListGedung(ruangan.getKodeGedung());

        Button btnSimpan = (Button) formPopupView.findViewById(R.id.btnUbahData);
        Button btnBatal = (Button) formPopupView.findViewById(R.id.btnBatal);

        editKodeRuang.setText(ruangan.getKodeRuangan());
        editNamaRuang.setText(ruangan.getNama());
        editKapasitas.setText(Integer.toString(ruangan.getKapasitas()));

        btnSimpan.setOnClickListener(view -> {
            String kodeRuang = editKodeRuang.getText().toString().trim();
            String namaRuang = editNamaRuang.getText().toString().trim();
            String kapasitas = editKapasitas.getText().toString().trim();

            if (kodeRuang.isEmpty() || namaRuang.isEmpty() || kapasitas.isEmpty()) {
                Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }
            ruangan.setNama(namaRuang);
            ruangan.setKapasitas(Integer.parseInt(kapasitas));
            ruangan.setKodeGedung(spinner.getSelectedItem().toString().split(" - ")[0]);

            AsyncTask.execute(() -> {
                if (!ruangan.getKodeRuangan().equals(kodeRuang)) {
                    List<Ruangan> findRuanganWithKodeRuang = db.ruanganDAO().findByKode(kodeRuang);
                    if (findRuanganWithKodeRuang.size() > 0) {
                        ((MainActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Kode ruang sudah ada", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }
                ruangan.setKodeRuangan(kodeRuang);
                db.ruanganDAO().update(ruangan);

                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                        Toast.makeText(context, "Ruangan berhasil diubah", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            });
        });

        btnBatal.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialogBuilder.setView(formPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void getListGedung(String kodeGedung) {
        AsyncTask.execute(new Runnable() {
            List<String> allGedung = new ArrayList<>();
            @Override
            public void run() {
                List<Gedung> listGedung = db.gedungDAO().getAll();
                if(listGedung.size() > 0) {
                    int index = 0;
                    for (Gedung gedung : listGedung) {
                        if(gedung.getKodeGedung().equals(kodeGedung)) {
                            index = listGedung.indexOf(gedung);
                        }
                        allGedung.add(gedung.getKodeGedung() + " - " + gedung.getNamaGedung());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, allGedung);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(index);
                }
            }
        });
    }
}
