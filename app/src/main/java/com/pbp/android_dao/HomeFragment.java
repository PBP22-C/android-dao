package com.pbp.android_dao;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.google.android.material.navigation.NavigationBarView;
import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;
import com.pbp.android_dao.entity.GedungWithRuangans;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    AppDatabase db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinner;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get database
        db = Room.databaseBuilder(getActivity().getApplicationContext(), AppDatabase.class, "Gedung").build();
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
                // Get gedung data from DB
                allGedung = db.gedungDAO().getAll();
                // Default option
                allGedung.add(0, new Gedung("", "Semua Gedung"));
//                allGedung.add(1, new Gedung("SLKF", "Hahahihi"));
//                allGedung.add(2, new Gedung("B","Matematika"));
//                allGedung.add(3, new Gedung("C","Fisika"));

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
            }
        });
    }

    private void setDaftarRuangBySpinnerSelect(String kodeGedung) {
        AsyncTask.execute(new Runnable() {
            List<GedungWithRuangans> gedungWithRuangans;
            ArrayList<Ruangan> ruangans = new ArrayList<>();

            @Override
            public void run() {
                // Fetch ruangan from db
                if (kodeGedung.equals("")) {
                    gedungWithRuangans = db.gedungDAO().getAllGedungWithRuangan();
                } else {
                    gedungWithRuangans = db.gedungDAO().getGedungWithRuangan(kodeGedung);
                }

                // Append every ruangan to ArrayList<Ruangan>
                for (GedungWithRuangans gedung : gedungWithRuangans) {
                    ruangans.addAll(gedung.ruangans);
                }

                // Insert ruangan to daftar ruang view
                if (!ruangans.isEmpty()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ListView daftarRuangView = (ListView) getView().findViewById(R.id.daftarRuangLayout);
                            RuanganListItemAdapter adapter = new RuanganListItemAdapter(ruangans, getActivity().getApplicationContext());
                            daftarRuangView.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }
}