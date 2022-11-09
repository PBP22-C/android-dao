package com.pbp.android_dao;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;

public class FormFragment extends Fragment {
    private Button btnTambahGedung, btnTambahRuangan;
    private LinearLayout formLayout;
    private AppDatabase db;

    public FormFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTambahGedung = getView().findViewById(R.id.btn_tambah_gedung);
        btnTambahRuangan = getView().findViewById(R.id.btn_tambah_ruangan);
        formLayout = getView().findViewById(R.id.linear_layout_form);

        btnTambahGedung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFormTambahGedung();
            }
        });

        btnTambahRuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFormTambahRuang();
            }
        });
    }

    private void createFormTambahGedung() {
        formLayout.removeAllViews();
        formLayout.addView(getLayoutInflater().inflate(R.layout.tambah_gedung, null));
        EditText etNamaGedung = getView().findViewById(R.id.namaGedung);
        EditText etKodeGedung = getView().findViewById(R.id.kodeGedung);
        Button btnSimpan = getView().findViewById(R.id.insertGedungButton);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaGedung = etNamaGedung.getText().toString();
                String kodeGedung = etKodeGedung.getText().toString();
                Gedung gedung = new Gedung(namaGedung, kodeGedung);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.gedungDAO().insertOne(gedung.getKodeGedung(),gedung.getNamaGedung());
                        Toast.makeText(getContext(), "Gedung berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        etKodeGedung.setText("");
                        etNamaGedung.setText("");
                        }
                    });
                }
        });
    }

    private void createFormTambahRuang() {
        formLayout.removeAllViews();
        formLayout.addView(getLayoutInflater().inflate(R.layout.tambah_ruang, null));
        EditText etNamaRuang = getView().findViewById(R.id.namaRuang);
        EditText etKodeRuang = getView().findViewById(R.id.kodeRuang);
        Spinner spGedung = getView().findViewById(R.id.spinnerGedung);
        Button btnSimpan = getView().findViewById(R.id.insertRuangButton);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaRuang = etNamaRuang.getText().toString();
                String kodeRuang = etKodeRuang.getText().toString();
                String namaGedung = spGedung.getSelectedItem().toString();
                Toast.makeText(getContext(), "Ruang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                etKodeRuang.setText("");
                etNamaRuang.setText("");
            }
        });
    }
}