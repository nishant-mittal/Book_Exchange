package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UploadActivity extends AppCompatActivity {
    private ImageView bookImage;
    private Button selectImageButton,submitButton;
    private Spinner semesterSpinner,branchSpinner;
    private EditText bookNamesEditText,phoneEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bookImage = findViewById(R.id.selected_image_image_view);
        selectImageButton = findViewById(R.id.select_image_button);
        submitButton = findViewById(R.id.upload_submit_button);
        semesterSpinner = findViewById(R.id.select_semester_spinner);
        branchSpinner = findViewById(R.id.select_branch_spinner);
        bookNamesEditText = findViewById(R.id.book_names_edit_text);
        phoneEditText = findViewById(R.id.enter_phone_edit_text);
        navbarDisplay();



    }

    public void navbarDisplay() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
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

    public void uploadSubmitButtonClick(View v) {
        String bookNames = bookNamesEditText.getText().toString();
        if(TextUtils.isEmpty(bookNames)) {
            bookNamesEditText.setError("Enter book names");
        }
        String phoneNumber = phoneEditText.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
            phoneEditText.setError("Invalid phone number");
        }
        else {
            String semester = semesterSpinner.getSelectedItem().toString();
            String branch = branchSpinner.getSelectedItem().toString();
        }
    }
}
