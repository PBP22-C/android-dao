package com.pbp.android_dao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button insertGedungButton;
    Spinner spinner;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get all views
        spinner = (Spinner) findViewById(R.id.spinnerGedung);
//        insertGedungButton = findViewById(R.id.insertGedungButton);

        // Get database
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Buku").build();

        loadGedungToSpinner();

//        insertGedungButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                EditText kodeGedungView = findViewById(R.id.kodeGedung);
////                EditText namaGedungView = findViewById(R.id.namaGedung);
//
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(view.getContext(), MainActivity.class);
//                        startActivityForResult(intent,0);
////                        db.gedungDAO().insertOne(new Gedung("SLKF", "Hahahihi"));
////                        db.gedungDAO().insertOne(new Gedung(kodeGedungView.getText().toString(), namaGedungView.getText().toString()));
//                    }
//                });
//            }
//        });
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

    private void loadGedungToSpinner() {
        System.out.println("load spinner");
        AsyncTask.execute(new Runnable() {
            List<Gedung> allGedung;
            @Override
            public void run() {
                allGedung = db.gedungDAO().getAll();
                allGedung.add(0, new Gedung("All", "Semua Gedung"));
                // Create spinner with all available gedung
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<Gedung> adapter = new ArrayAdapter<Gedung>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, allGedung);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);
            }
        });
    }
}