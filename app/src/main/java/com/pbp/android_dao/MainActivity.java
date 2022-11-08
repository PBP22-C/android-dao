package com.pbp.android_dao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pbp.android_dao.entity.AppDatabase;
import com.pbp.android_dao.entity.Gedung;

public class MainActivity extends AppCompatActivity {
    Button insertGedungButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Buku").build();

        insertGedungButton = findViewById(R.id.insertGedungButton);

        insertGedungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText kodeGedungView = findViewById(R.id.kodeGedung);
                EditText namaGedungView = findViewById(R.id.namaGedung);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        db.gedungDAO().insertOne(new Gedung(kodeGedungView.getText().toString(), namaGedungView.getText().toString()));
                    }
                });
            }
        });
    }


}