package com.example.ekipaapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ekipaapp.R;
import com.example.ekipaapp.ui.location.LocationsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        usernameEditText = findViewById(R.id.newUsernameEditText);
        passwordEditText = findViewById(R.id.newPasswordEditText);
        Button registrationButton = findViewById(R.id.registerConfirmButton);
        registrationButton.setOnClickListener(v -> registerNewUser());
    }

    private void registerNewUser() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        firebaseAuth
                .createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    startMainActivity();
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, LocationsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
