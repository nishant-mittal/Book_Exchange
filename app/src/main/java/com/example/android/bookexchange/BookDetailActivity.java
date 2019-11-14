package com.example.android.bookexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    private ImageView bookImage;
    private TextView branchText,semesterText,booksTextView,phoneTextView;
    private FloatingActionButton phoneFAB,messageFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        int position = getIntent().getExtras().getInt("position");
        final Books book = MainActivity.mBooksList.get(position);
        Log.d(TAG, "Position " + position);
        Log.d(TAG, "email" + MainActivity.mBooksList.get(position).getPhoneNumber());
        bookImage = findViewById(R.id.book_detail_image_view);
        branchText = findViewById(R.id.book_detail_branch_text_view);
        semesterText = findViewById(R.id.book_detail_semester_text_view);
        booksTextView = findViewById(R.id.book_detail_books_text_view);
        phoneTextView = findViewById(R.id.book_detail_phone_text_view);
        phoneFAB = findViewById(R.id.phone_FAB);
        messageFAB = findViewById(R.id.message_FAB);
        final String phoneNumber = book.getPhoneNumber();
        Picasso.get().load(book.getImagePath()).fit().centerInside().into(bookImage);
        branchText.setText(book.getBranch());
        semesterText.setText(book.getSemester());
        booksTextView.setText(book.getBookNames());
        phoneTextView.setText(book.getPhoneNumber());


        phoneFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber ));
                startActivity(intent);
            }
        });

        messageFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null));
                startActivity(intent);
            }
        });
    }

}
