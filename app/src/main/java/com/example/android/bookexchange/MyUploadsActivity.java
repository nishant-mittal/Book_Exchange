package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyUploadsActivity extends AppCompatActivity {
    private static final String TAG = "MyUploadsActivity";
    private RecyclerView mRecyclerView;
    public static List<Books> mBooksList;
    private myBookAdapter mBookAdapter;
    private DatabaseReference mDatabaseReference;

    public static void getMyUploads(final DatabaseReference mDatabaseReference, final List<Books> mBooksList, final myBookAdapter mBookAdapter){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                //mBooksList.clear();
                //Hello Delete this later
                for(DataSnapshot branchSnapshot: dataSnapshot.getChildren()) {
                    Books books = branchSnapshot.getValue(Books.class);
                    Log.d("main", "onDataChange: books and email " + books.getBranch() + books.getUserEmail());
                    if(email.equals(books.getUserEmail())){
                        mBooksList.add(books);
                    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_uploads);

        mRecyclerView = findViewById(R.id.my_book_recycler_view);
        //mProgressBar = findViewById(R.id.progress_bar_main);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mBooksList = new ArrayList<>();
        mBookAdapter = new myBookAdapter(MyUploadsActivity.this,mBooksList);
        mBookAdapter.setListener(new myBookAdapter.Listener() {
            @Override
            public void onClick(int position) {

            }
        });

        mRecyclerView.setAdapter(mBookAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        String id = mDatabaseReference.getKey();
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Might break
                mBooksList.clear();
                //Hello Delete this later
                for(DataSnapshot branchSnapshot: dataSnapshot.getChildren()) {
                    //Collections.reverse(mBooksList);
                    //DataSnapshot snapshot = branchSnapshot.getChildren();
                    DatabaseReference DatabaseReference = branchSnapshot.getRef();

                    getMyUploads(DatabaseReference, mBooksList, mBookAdapter);

                    /*Books books = branchSnapshot.getValue(Books.class);
                    Log.d("main", "onDataChange: books and email " + books.getBranch() + books.getUserEmail());
                    mBooksList.add(books);*/
                }
                //mProgressBar.setVisibility(View.INVISIBLE);
                //Collections.reverse(mBooksList);
                //mBookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toasty.error(MainActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT,true).show();
                //mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

}

