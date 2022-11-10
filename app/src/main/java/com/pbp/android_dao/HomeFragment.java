package com.pbp.android_dao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;
import com.pbp.android_dao.entity.GedungWithRuangans;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    AppDatabase db;
    private Spinner spinner;

    public HomeFragment(AppDatabase db) {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load gedung data to spinner
        spinner = (Spinner) getView().findViewById(R.id.spinnerGedung);
        loadGedungToSpinner();

        // Change list ruangan when spiner item is selected
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Gedung selectedGedung = (Gedung) adapterView.getItemAtPosition(i);
                setDaftarRuangBySpinnerSelect(selectedGedung.getKodeGedung());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void loadGedungToSpinner() {
        AsyncTask.execute(new Runnable() {
            List<Gedung> allGedung;
            @Override
            public void run() {
                try {
                    // Get gedung data from DB
                    allGedung = db.gedungDAO().getAll();
                    // Default option
                    allGedung.add(0, new Gedung("All", "Semua Gedung"));

                    // Create spinner with all available gedung
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<Gedung> adapter = new ArrayAdapter<Gedung>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGedung);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Apply the adapter to the spinner
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setAdapter(adapter);
                        }
                    });
                }catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }

    private void setDaftarRuangBySpinnerSelect(String kodeGedung) {
        AsyncTask.execute(new Runnable() {
            List<GedungWithRuangans> gedungWithRuangans;
            ArrayList<Ruangan> ruangans = new ArrayList<>();

            @Override
            public void run() {
                try{
                    // Fetch ruangan from db
                    if (kodeGedung.equals("All")) {
                        gedungWithRuangans = db.gedungDAO().getAllGedungWithRuangan();
                    } else {
                        gedungWithRuangans = db.gedungDAO().getGedungWithRuangan(kodeGedung);
                    }

                    // Append every ruangan to ArrayList<Ruangan>
                    for (GedungWithRuangans gedung : gedungWithRuangans) {
                        ruangans.addAll(gedung.ruangans);
                    }

                    // Insert ruangan to daftar ruang view

                    ListView daftarRuangView = (ListView) getView().findViewById(R.id.daftarRuangLayout);
                    RuanganListItemAdapter adapter = new RuanganListItemAdapter(ruangans, getContext(), db);
//                add uithread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            daftarRuangView.setAdapter(adapter);
                        }
                    });
                }catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }
}