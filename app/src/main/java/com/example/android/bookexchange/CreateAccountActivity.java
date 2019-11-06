package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText nameEditText,emailEditText,passwordEditText,rePasswordEditText,usnEditText;
    private Spinner branchSpinner;
    private FirebaseAuth mAuth;
    private static final String TAG = "CreateAccountActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.name_edit_text_create_account);
        emailEditText = findViewById(R.id.email_edit_text_create_account);
        passwordEditText = findViewById(R.id.password_edit_text_create_account);
        rePasswordEditText = findViewById(R.id.re_password_edit_text_create_account);
        usnEditText = findViewById(R.id.usn_edit_text_create_account);

        branchSpinner = findViewById(R.id.branch_spinner_create_account);
    }
    public void createAccount(View v) {
        String name,email,password,repassword,usn;

        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        repassword = rePasswordEditText.getText().toString();
        usn = usnEditText.getText().toString();

        if(TextUtils.isEmpty(name)) {
            nameEditText.setError("Enter name");
        }
        if(TextUtils.isEmpty(email)) {
            emailEditText.setError("Enter email");
        }
        if(!(TextUtils.isEmpty(email))) {
            if(!(email.contains("@") && email.contains("."))) {
                emailEditText.setError("This isn't an email");
            }
        }
        if(TextUtils.isEmpty(password)) {
            passwordEditText.setError("Enter password");
        }
        if(TextUtils.isEmpty(repassword)) {
            rePasswordEditText.setError("Enter password");
        }
        if(!(password.equals(repassword))) {
            rePasswordEditText.setError("Passwords do not match");
        }
        if(password.length() < 6) {
            passwordEditText.setError("Password should be longer");
        }
        if(repassword.length() < 6) {
            rePasswordEditText.setError("Password should be longer");
        }
        if(TextUtils.isEmpty(usn)) {
            usnEditText.setError("Enter usn");
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(CreateAccountActivity.this, R.string.account_could_not_be_created,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }
}
