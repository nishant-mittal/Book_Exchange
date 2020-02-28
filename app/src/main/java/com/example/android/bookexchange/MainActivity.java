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
import com.google.firebase.auth.FirebaseUser;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {
    public String userBranch;
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    public static List<Books> mBooksList;
    public static List<UserData> mUserDataList;
    private bookAdapter mBookAdapter;
    private FirebaseUser firebaseUser;
    //private ProgressBar mProgressBar;
    //=========================================================

    public static void fetchingUploads(String branch, DatabaseReference mDatabaseReference, final bookAdapter mBookAdapter){

//
        Log.d(TAG, "onCreate: before fetching" + branch);
        while (branch == null){
            Log.d(TAG, "onCreate: inside while " + branch);
        }
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads/" + branch);
        String id = mDatabaseReference.getKey();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                mBooksList.clear();
                //
                //Hello Delete this later
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Collections.reverse(mBooksList);
                    Books books = snapshot.getValue(Books.class);
                    Log.d(TAG, "onDataChange: " + books.getBranch());
                    Log.d("main", "onDataChange: books " + books.getBranch());
                    mBooksList.add(books);
                }
                //mProgressBar.setVisibility(View.INVISIBLE);
                Collections.reverse(mBooksList);
                mBookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toasty.error(MainActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT,true).show();
                //mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    //=========================================================
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
        // ========================================================

        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData post = dataSnapshot.getValue(UserData.class);
                Log.d("main", "onDataChange: user branch" + post.getBranch());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });*/

        // =========================================================
//        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
//        String email = user.getEmail();
//        int position = email.indexOf("@");
//        String mail = email.substring(0,position);
//        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users/1dt17is094");
//        mDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
//                    //Collections.reverse(mBooksList);
//                    UserData data = snapshot.getValue(UserData.class);
//                    Log.d("main", "onDataChange: user branch" + data.getBranch());
//                    mUserDataList.add(data);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toasty.error(MainActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT,true).show();
//            }
//        });
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        int position = email.indexOf("@");
        String mail = email.substring(0,position);
        String actualMail = mail.replace(".","_");
        Log.d(TAG, "onCreate: mail " + actualMail);
        Query query= FirebaseDatabase.getInstance().getReference("users/" + actualMail);
        //Query query= FirebaseDatabase.getInstance().getReference("users/1dt17is078");
        query.addListenerForSingleValueEvent(value);

        // ========================================================
        /* START Log.d(TAG, "onCreate: before fetching" + userBranch);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads/" + userBranch);
        String id = mDatabaseReference.getKey();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                mBooksList.clear();
                //Hello Delete this later
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Collections.reverse(mBooksList);
                    Books books = snapshot.getValue(Books.class);
                    Log.d("main", "onDataChange: books" + books.getBranch());
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
        END
         */

    }

   ValueEventListener value =new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(dataSnapshot.exists()){
               userBranch = dataSnapshot.getValue(UserData.class).getBranch();
               Log.d(TAG, "onDataChange: user" + userBranch);
               Log.d("main", "onDataChange: user " + userBranch);
               fetchingUploads(userBranch, mDatabaseReference, mBookAdapter);
           }
       }

       @Override
       public void onCancelled(@NonNull DatabaseError databaseError) {
            Toasty.error(MainActivity.this,"Could not fetch data",Toasty.LENGTH_SHORT).show();
       }
   };
   /* public void signOutButton(View v) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);

    }*/

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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
