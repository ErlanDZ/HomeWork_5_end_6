package com.example.work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.btn_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        fragmentTransaction.replace(R.id.container_fragment, HomeFragment.newInstance("key10", "key11")).commit();
                        item.setChecked(true);
                        break;
                    case R.id.navigation_list:
                        fragmentTransaction.replace(R.id.container_fragment,ListFragment.newInstance("key20", "key21")).commit();
                        item.setChecked(true);
                        break;
                    case R.id.navigation_person:
                        fragmentTransaction.replace(R.id.container_fragment, PersonFragment.newInstance("key30", "key31")).commit();
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });

    }
}