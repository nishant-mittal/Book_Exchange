package com.example.android.bookexchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {
    private static final String TAG = "BookDetailActivity";
    private ImageView bookImage;
    private TextView branchText,semesterText,booksTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        int position = getIntent().getExtras().getInt("position");
        Books book = MainActivity.mBooksList.get(position);
        Log.d(TAG, "Position " + position);
        Log.d(TAG, "email" + MainActivity.mBooksList.get(position).getPhoneNumber());
        bookImage = findViewById(R.id.book_detail_image_view);
        branchText = findViewById(R.id.book_detail_branch_text_view);
        semesterText = findViewById(R.id.book_detail_semester_text_view);
        booksTextView = findViewById(R.id.book_detail_books_text_view);

        Picasso.get().load(book.getImagePath()).fit().centerInside().into(bookImage);
        branchText.setText(book.getBranch());
        semesterText.setText(book.getSemester());
        booksTextView.setText(book.getBookNames());
    }
}
