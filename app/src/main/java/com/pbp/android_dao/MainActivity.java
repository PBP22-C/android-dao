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

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
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
                    binding.bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                    break;
                case R.id.form:
                    replaceFragment(new FormFragment());
                    binding.bottomNavigationView.getMenu().findItem(R.id.form).setChecked(true);
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
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

//    @Override
//    public void replaceFragment(int id) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        switch (id){
//            case 1:
//                fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
//                break;
//            case 2:
//                fragmentTransaction.replace(R.id.fragment_container, new FormFragment());
//                break;
//            case 3:
//                fragmentTransaction.replace(R.id.fragment_container, new FormRuanganFragment());
//                break;
//        }
//        fragmentTransaction.commit();
//    }
}