package com.pbp.android_dao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "Buku").build();

        insertButton = findViewById(R.id.insertButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ey");
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.bukuDAO().insertBuku();
                    }
                });
            }
        });
    }


}