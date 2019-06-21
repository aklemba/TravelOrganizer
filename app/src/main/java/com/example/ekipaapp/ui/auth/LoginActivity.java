package com.example.ekipaapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ekipaapp.R;
import com.example.ekipaapp.ui.MenuActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.ekipaapp.ui.auth.InputValidation.validateEmail;
import static com.example.ekipaapp.ui.auth.InputValidation.validatePassword;

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
        TextInputLayout emailEditTextWrapper = findViewById(R.id.emailEditTextWrapper);
        emailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                return;
            }
            Editable text = ((EditText) v).getText();
            if (validateEmail(text) || text.length() == 0) {
                emailEditTextWrapper.setError(null);
                return;
            }
            emailEditTextWrapper.setError("Invalid email address");
        });

        passwordEditText = findViewById(R.id.passwordEditText);
        TextInputLayout passwordEditTextWrapper = findViewById(R.id.passwordEditTextWrapper);
        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                return;
            }
            Editable text = ((EditText) v).getText();
            if (validatePassword(text) || text.length() == 0) {
                passwordEditTextWrapper.setError(null);
                return;
            }
            passwordEditTextWrapper.setError("Password needs to be at least 8 characters");
        });
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
        if (!validateEmail(email) || !validatePassword(password)) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
            return;
        }
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
