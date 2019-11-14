package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    public static List<Books> mBooksList;
    private bookAdapter mBookAdapter;
    //private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbarDisplay();
        mRecyclerView = findViewById(R.id.book_recycler_view);
        //mProgressBar = findViewById(R.id.progress_bar_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mBooksList = new ArrayList<>();
        mBookAdapter = new bookAdapter(MainActivity.this,mBooksList);
        mBookAdapter.setListener(new bookAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this,BookDetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mBookAdapter);
        //Might break
        /*Collections.reverse(mBooksList);*/
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("images");
        String id = mDatabaseReference.getKey();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                mBooksList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Collections.reverse(mBooksList);
                    Books books = snapshot.getValue(Books.class);
                    mBooksList.add(books);
                }
                //mProgressBar.setVisibility(View.INVISIBLE);
                Collections.reverse(mBooksList);
                mBookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(MainActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT,true).show();
                //mProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void signOutButton(View v) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);

    }

    public void navbarDisplay() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_button:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.add_button:
                        Intent intent1 = new Intent(MainActivity.this, UploadActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.profile_button:
                        Intent intent2 = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
    }
}
