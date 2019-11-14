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
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class ResetPasswordActivity extends AppCompatActivity {
    private Button resetButton;
    private FirebaseAuth mAuth;
    private EditText resetPasswordEditText;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetButton = findViewById(R.id.reset_button);
        resetPasswordEditText = findViewById(R.id.reset_email_edit_text);


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = resetPasswordEditText.getText().toString();
                Log.d("tag", "onClick: " + email);
                if(!(isValid(email))) {
                    resetPasswordEditText.setError("Enter correct email");
                }
                else {
                    mAuth = FirebaseAuth.getInstance();
                    resetButton.setEnabled(false);
                    resetButton.setBackgroundResource(R.drawable.log_in_button_disabled);
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("tag", "onComplete: " + email);
                                Toasty.info(ResetPasswordActivity.this, R.string.check_email, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                                startActivity(intent);
                            } else {
                                Toasty.error(ResetPasswordActivity.this, R.string.check_email_error, Toast.LENGTH_SHORT).show();
                                resetButton.setEnabled(true);
                                resetButton.setBackgroundResource(R.drawable.log_in_button);
                            }
                        }
                    });
                }
            }
        });

    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
