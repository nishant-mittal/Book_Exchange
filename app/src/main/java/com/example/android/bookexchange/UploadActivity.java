package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadActivity extends AppCompatActivity {
    private ImageView bookImage;
    private Button selectImageButton,submitButton;
    private Spinner semesterSpinner,branchSpinner;
    private EditText bookNamesEditText,phoneEditText;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private StorageTask mUploadTask;
    //private ProgressBar mProgressBar;
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
        //mProgressBar = findViewById(R.id.progress_bar);
        navbarDisplay();

        mStorageReference = FirebaseStorage.getInstance().getReference("images");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("images");

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

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
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String bookNames = bookNamesEditText.getText().toString();
        if(TextUtils.isEmpty(bookNames)) {
            bookNamesEditText.setError("Enter book names");
        }
        final String phoneNumber = phoneEditText.getText().toString().trim();
        if(TextUtils.isEmpty(phoneNumber) || !(phoneNumber.length() == 10)) {
            phoneEditText.setError("Invalid phone number");
        }
        if(imageUri == null) {
            Toast.makeText(UploadActivity.this, R.string.select_image, Toast.LENGTH_SHORT).show();
        }
        else {
            submitButton.setEnabled(false);
            Drawable drawable = getDrawable(R.drawable.log_in_button_disabled);
            submitButton.setBackground(drawable);
            final String semester = semesterSpinner.getSelectedItem().toString();
            final String branch = branchSpinner.getSelectedItem().toString();
                StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(imageUri));

               mUploadTask  = fileReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                               /* Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);*/


                               /* DateFormat simple = new SimpleDateFormat("dd MMMM yyyy,h:mm a");
                                Date result = new Date(System.currentTimeMillis());
                                String date = "" + simple.format(result);*/

                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String photoLink = uri.toString();
                                        DateFormat simple = new SimpleDateFormat("dd MMMM yyyy \nh:mm a");
                                        Date result = new Date(System.currentTimeMillis());
                                        String date = "" + simple.format(result);
                                        String email = firebaseUser.getEmail();
                                        Log.d("tag", "Email" + email);
                                        Books bookDataEntry = new Books(photoLink,semester,branch,bookNames,"" + System.currentTimeMillis(),date,phoneNumber,email);
                                /*Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                        taskSnapshot.getDownloadUrl().toString());*/
                                        String uploadId = mDatabaseReference.push().getKey();
                                        Log.d("tag", "onSuccess: " + date);
                                        mDatabaseReference.child(uploadId).setValue(bookDataEntry);
                                        Toasty.success(UploadActivity.this,"Upload successful",Toasty.LENGTH_SHORT).show();
                                        //Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                        submitButton.setEnabled(true);
                                        Drawable drawable = getDrawable(R.drawable.log_in_button);
                                        submitButton.setBackground(drawable);
                                        Intent intent = new Intent(UploadActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                });


                               /* Books bookDataEntry = new Books(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),semester,branch,bookNames,"" + System.currentTimeMillis(),date,phoneNumber);
                                *//*Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                        taskSnapshot.getDownloadUrl().toString());*//*
                                String uploadId = mDatabaseReference.push().getKey();
                                Log.d("tag", "onSuccess: " + date);
                                mDatabaseReference.child(uploadId).setValue(bookDataEntry);
                                Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                submitButton.setEnabled(true);
                                Drawable drawable = getDrawable(R.drawable.log_in_button);
                                submitButton.setBackground(drawable);
                                Intent intent = new Intent(UploadActivity.this,MainActivity.class);
                                startActivity(intent);*/
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toasty.error(UploadActivity.this, "Upload failed", Toasty.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                /*double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);*/
                            }
                        });

        }
    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(bookImage);
            //bookImage.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }




}
