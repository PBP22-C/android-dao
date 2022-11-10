package com.pbp.android_dao;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormFragment extends Fragment {
    private Button btnTambahGedung, btnTambahRuangan;
    private LinearLayout formLayout;
    private final AppDatabase db;
    private Spinner spinner;

    public FormFragment(AppDatabase db) {
        this.db = db;
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
        btnTambahGedung = getView().findViewById(R.id.btnTambahGedung);
        btnTambahRuangan = getView().findViewById(R.id.btnTambahRuangan);
        formLayout = getView().findViewById(R.id.layoutForm);
        createFormTambahGedung();

        btnTambahGedung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                set button text style bold
                btnTambahGedung.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btnTambahRuangan.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                createFormTambahGedung();
            }
        });

        btnTambahRuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTambahGedung.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                btnTambahRuangan.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
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
                String namaGedung = etNamaGedung.getText().toString().trim();
                String kodeGedung = etKodeGedung.getText().toString().trim();
                Gedung gedung = new Gedung(namaGedung, kodeGedung);
                if(namaGedung.isEmpty() || kodeGedung.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Gedung> listGedung = db.gedungDAO().getGedungByKode(kodeGedung);
                        if(listGedung.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "Kode Gedung sudah ada", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                        db.gedungDAO().insertOne(gedung.getKodeGedung(), gedung.getNamaGedung());
                        Log.i("GEDUNG", "BERHASIL");
                    }
                });
                Toast.makeText(getContext(), "Gedung berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                etKodeGedung.setText("");
                etNamaGedung.setText("");
            }
        });
    }

    private void createFormTambahRuang() {
        formLayout.removeAllViews();
        formLayout.addView(getLayoutInflater().inflate(R.layout.tambah_ruang, null));
        EditText etNamaRuang = getView().findViewById(R.id.namaRuang);
        EditText etKodeRuang = getView().findViewById(R.id.kodeRuang);
        EditText etKapasitas = getView().findViewById(R.id.kapasitas);
        Button btnSimpan = getView().findViewById(R.id.insertRuangButton);
        spinner = (Spinner) getView().findViewById(R.id.spinnerTambahRuang);

        getListGedung();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaRuang = etNamaRuang.getText().toString();
                String kodeRuang = etKodeRuang.getText().toString();
                String kapasitas = etKapasitas.getText().toString();
                String kodeGedung = spinner.getSelectedItem().toString().split(" - ")[0];

                Log.i("RUANGAN", kodeGedung);
                if(namaRuang.isEmpty() || kodeRuang.isEmpty() || kodeGedung.isEmpty() || kapasitas.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Ruangan> listRuangan = db.ruanganDAO().findByKode(kodeRuang);
                        System.out.println(listRuangan.size());
                        if(listRuangan.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(), "Kode Ruangan sudah ada", Toast.LENGTH_SHORT).show();
                                }
                            });
                            return;
                        }
                        db.ruanganDAO().insertOne(kodeRuang, namaRuang, Integer.parseInt(kapasitas), kodeGedung);
                        Log.i("RUANGAN", "BERHASIL");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(), "Ruangan berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        });
                        etKodeRuang.setText("");
                        etNamaRuang.setText("");
                        etKapasitas.setText("");
                    }
                });
            }
        });
    }

    private void getListGedung(){
        AsyncTask.execute(new Runnable() {
            List<String> allGedung = new ArrayList<>();
            @Override
            public void run() {
                List<Gedung> listGedung = db.gedungDAO().getAll();
                if(listGedung.size() > 0) {
                    for (Gedung gedung : listGedung) {
                        allGedung.add(gedung.getKodeGedung() + " - " + gedung.getNamaGedung());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGedung);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setAdapter(adapter);
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "Data Gedung Kosong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}