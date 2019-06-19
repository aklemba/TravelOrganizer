package com.example.ekipaapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ekipaapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();
    }

    private void init() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> checkCredentials());
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> startRegisterActivity());
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void checkCredentials() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }
            startMenuActivity();
        });
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
