package com.example.ekipaapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ekipaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        init();
        if (!checkIfLoggedIn()) {
            startLoginActivity();
            finish();
        }
    }

    private void init() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            logout();
            startLoginActivity();
        });

        Button eventsButton = findViewById(R.id.eventListButton);
        eventsButton.setOnClickListener(v -> startLocationActivity());
    }

    private boolean checkIfLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startLocationActivity() {
        Intent intent = new Intent(this, LocationsActivity.class);
        startActivity(intent);
    }

}
