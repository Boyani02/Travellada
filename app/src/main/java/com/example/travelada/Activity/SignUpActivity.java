package com.example.travelada.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelada.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth and Database Reference
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("users");

        setVariable();
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String name = binding.nameEdt.getText().toString();
            String email = binding.userEdt.getText().toString();
            String username = binding.usernameEdt.getText().toString();
            String password = binding.passEdt.getText().toString();

            if (password.length() < 6) {
                Toast.makeText(SignUpActivity.this, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        // Create a user profile and save it to Realtime Database
                        UserProfile userProfile = new UserProfile(name, username, email);
                        db.child(user.getUid()).setValue(userProfile)
                                .addOnSuccessListener(aVoid -> {
                                    Log.i("SignUpActivity", "User profile successfully written!");
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                })
                                .addOnFailureListener(e -> {
                                    Log.i("SignUpActivity", "Error writing user profile", e);
                                    Toast.makeText(SignUpActivity.this, "Failed to save user details", Toast.LENGTH_SHORT).show();
                                });
                    }
                } else {
                    Log.i("SignUpActivity", "failure: " + task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    // Inner class to represent the user profile data
    public static class UserProfile {
        private String name;
        private String username;
        private String email;

        public UserProfile(String name, String username, String email) {
            this.name = name;
            this.username = username;
            this.email = email;
        }

        // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
        public UserProfile() {
        }

        // Getters and setters (if needed)
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
