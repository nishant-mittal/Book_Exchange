package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        navbarDisplay();
    }

    public void navbarDisplay() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_button:
                        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.add_button:
                        Intent intent1 = new Intent(UploadActivity.this, UploadActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.profile_button:
                        Intent intent2 = new Intent(UploadActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }
}
