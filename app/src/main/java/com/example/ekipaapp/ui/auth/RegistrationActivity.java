package com.example.ekipaapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
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

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        emailEditText = findViewById(R.id.newEmailEditText);
        TextInputLayout emailEditTextWrapper = findViewById(R.id.newEmailEditTextWrapper);
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

        passwordEditText = findViewById(R.id.newPasswordEditText);
        TextInputLayout passwordEditTextWrapper = findViewById(R.id.newPasswordEditTextWrapper);
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

        Button registrationButton = findViewById(R.id.registerConfirmButton);
        registrationButton.setOnClickListener(v -> registerNewUser());
    }

    private void registerNewUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (!validateEmail(email) || !validatePassword(password)) {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
            return;
        }

        showLoadingSpinner(true);
        firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showLoadingSpinner(false);
                    if (!task.isSuccessful()) {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startMainActivity();
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showLoadingSpinner(boolean enable) {
        if (enable) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            findViewById(R.id.loadingSpinner).setVisibility(View.VISIBLE);
            return;
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        findViewById(R.id.loadingSpinner).setVisibility(View.GONE);
    }
}
