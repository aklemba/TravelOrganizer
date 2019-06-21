package com.example.ekipaapp.ui.auth;

import android.util.Patterns;

class InputValidation {
    static boolean validateEmail(CharSequence s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }
    static boolean validatePassword(CharSequence s) {
        return s.length() >= 8;
    }
}
