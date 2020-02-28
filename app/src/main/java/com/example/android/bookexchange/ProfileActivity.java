package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    public String userBranch, userUSN, userName;
    private TextView emailTextView,nameTextView,usntextview,branchtextview;
    Button myUploads,signOutTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        navbarDisplay();

        // ===================================================

        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String tempEmail = user.getEmail();
        int position = tempEmail.indexOf("@");
        String mail = tempEmail.substring(0,position);
        String actualMail = mail.replace(".","_");
        Log.d(TAG, "onCreate: mail " + actualMail);
        Query query= FirebaseDatabase.getInstance().getReference("users/" + actualMail);
        query.addListenerForSingleValueEvent(value);


        // ===================================================

        nameTextView = findViewById(R.id.user_profile_name);
        emailTextView = findViewById(R.id.user_profile_email);
        myUploads=findViewById(R.id.your_uploads_text_view);
        signOutTextView = findViewById(R.id.sign_out_text_view);
        usntextview=findViewById(R.id.user_profile_usn);
        branchtextview=findViewById(R.id.user_profile_branch);
        String email = firebaseUser.getEmail();
        emailTextView.setText(email);

        myUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,MyUploadsActivity.class);
                startActivity(intent);
            }
        });


        signOutTextView.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    ValueEventListener value =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                userBranch = dataSnapshot.getValue(UserData.class).getBranch();
                userUSN = dataSnapshot.getValue(UserData.class).getUsn();
                userName = dataSnapshot.getValue(UserData.class).getName();
                Log.d(TAG, "onDataChange: user data" + userBranch + "\t" + userName + "\t" + userUSN);
                nameTextView.setText(userName);
                usntextview.setText(userUSN);
                branchtextview.setText(userBranch);


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void navbarDisplay() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_button:
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.add_button:
                        Intent intent1 = new Intent(ProfileActivity.this, UploadActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.profile_button:
                        Intent intent2 = new Intent(ProfileActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }
}
