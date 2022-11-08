package com.pbp.android_dao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pbp.android_dao.databinding.ActivityMainBinding;
import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    Button insertGedungButton;

    AppDatabase db;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.form:
                    replaceFragment(new FormFragment());
                    break;
            }
            return false;
        });
        // Get all views


//        insertGedungButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText kodeGedungView = findViewById(R.id.kodeGedung);
//                EditText namaGedungView = findViewById(R.id.namaGedung);
//
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(view.getContext(), MainActivity.class);
//                        startActivityForResult(intent,0);
//                        db.gedungDAO().insertOne(new Gedung("SLKF", "Hahahihi"));
//                        db.gedungDAO().insertOne(new Gedung(kodeGedungView.getText().toString(), namaGedungView.getText().toString()));
//                    }
//                });
//            }
//        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Test");
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Testing lagi");
    }

//    private void loadGedungToSpinner() {
//        System.out.println("load spinner");
//        AsyncTask.execute(new Runnable() {
//            List<Gedung> allGedung;
//            @Override
//            public void run() {
//                allGedung = db.gedungDAO().getAll();
//                allGedung.add(0, new Gedung("All", "Semua Gedung"));
//                allGedung.add(1, new Gedung("SLKF", "Hahahihi"));
//                allGedung.add(2, new Gedung("B","Matematika"));
//                allGedung.add(3, new Gedung("C","Fisika"));
//                // Create spinner with all available gedung
//                // Create an ArrayAdapter using the string array and a default spinner layout
//                ArrayAdapter<Gedung> adapter = new ArrayAdapter<Gedung>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGedung);
//                // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                // Apply the adapter to the spinner
//                spinner.setAdapter(adapter);
//            }
//        });
//    }
}