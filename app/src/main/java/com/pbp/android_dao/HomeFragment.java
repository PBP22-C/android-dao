package com.pbp.android_dao;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;
import com.pbp.android_dao.entity.GedungWithRuangans;
import com.pbp.android_dao.entity.Ruangan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        spinner = (Spinner) getView().findViewById(R.id.spinnerGedung);
        loadGedungToSpinner();

        setDaftarRuangBySpinnerSelect("Semua Gedung");
    }

    private void loadGedungToSpinner() {
        System.out.println("load spinner");
        AsyncTask.execute(new Runnable() {
            List<Gedung> allGedung;
            @Override
            public void run() {
                allGedung = db.gedungDAO().getAll();
                allGedung.add(0, new Gedung("All", "Semua Gedung"));
//                allGedung.add(1, new Gedung("SLKF", "Hahahihi"));
//                allGedung.add(2, new Gedung("B","Matematika"));
//                allGedung.add(3, new Gedung("C","Fisika"));
                // Create spinner with all available gedung
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<Gedung> adapter = new ArrayAdapter<Gedung>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGedung);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            }
        });
    }

    private void setDaftarRuangBySpinnerSelect(String gedungName) {
        AsyncTask.execute(new Runnable() {
            List<GedungWithRuangans> gedungWithRuangans;
            List<Gedung> gedungs;
            ArrayList<Ruangan> ruangans = new ArrayList<>();

            @Override
            public void run() {
                if (gedungName.equals("Semua Gedung")) {
                    gedungWithRuangans = db.gedungDAO().getAllGedungWithRuangan();
                } else {
                    Gedung currentGedung = db.gedungDAO().findByName(gedungName);
                    gedungWithRuangans = db.gedungDAO().getGedungWithRuangan(currentGedung.getKodeGedung());
                }

                gedungs = db.gedungDAO().getAll();

                System.out.println(gedungs.isEmpty());
                System.out.println(gedungWithRuangans.isEmpty());

                for (GedungWithRuangans x : gedungWithRuangans) {
                    System.out.println(x.gedung.getNama());
                    ruangans.addAll(x.ruangans);
                }

                for (Ruangan x : ruangans) {
                    System.out.println(x.getKodeRuangan() + ": " + x.getNama());
                }
            }
        });
    }
}