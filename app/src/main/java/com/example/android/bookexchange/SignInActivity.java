package com.example.android.bookexchange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email_edit_text_sign_in);
        passwordEditText = findViewById(R.id.password_edit_text_sign_in);
        loginButton = findViewById(R.id.log_in_button);




    }

    public void logInClick(View v) {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || !(email.contains("@")) || !(email.contains("."))) {
            emailEditText.setError("Enter email");
        } else if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Enter password");
        } else {
            loginButton.setEnabled(false);
            loginButton.setBackgroundResource(R.drawable.log_in_button_disabled);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toasty.success(getApplicationContext(), R.string.log_in_successful, Toasty.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toasty.error(getApplicationContext(), "Authentication failed", Toasty.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                                loginButton.setBackgroundResource(R.drawable.log_in_button);
                            }
                        }
                    });
        }
    }

    public void createAccountClick(View v) {
        Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void resetPassword(View v) {
        Intent intent = new Intent(SignInActivity.this,ResetPasswordActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
