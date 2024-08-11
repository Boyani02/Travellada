package com.example.travelada.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.example.travelada.R;
import com.example.travelada.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // Set click listeners for the buttons
        binding.updateAccount.setOnClickListener(v -> updateAccount());
        binding.deleteAccount.setOnClickListener(v -> deleteAccount());

        // Set click listener for the upload button
        binding.upload.setOnClickListener(v -> {
            Fragment fragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment)
                    .addToBackStack("name")
                    .setReorderingAllowed(true)
                    .commit();
        });
    }

    private void updateAccount() {
        if (user != null) {
            String newEmail = binding.emailEditText.getText().toString();
            String newPassword = binding.passwordEditText.getText().toString();
            String newUsername = binding.usernameEditText.getText().toString();
            String newName = binding.nameEditText.getText().toString();

            if (!newEmail.isEmpty()) {
                user.updateEmail(newEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Update", "Email updated successfully.");
                            } else {
                                Toast.makeText(getContext(), "Email update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            if (!newPassword.isEmpty()) {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("Update", "Password updated successfully.");
                            } else {
                                Toast.makeText(getContext(), "Password update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            if (!newUsername.isEmpty()) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(newUsername)
                        .build();
                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Username updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Username update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            if (!newName.isEmpty()) {
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userRef = databaseRef.child("users").child(user.getUid());
                userRef.child("name").setValue(newName)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Name updated successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Name update failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(getContext(), "No user is currently signed in.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAccount() {
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                            // Navigate back to the login screen or handle the post-deletion process
                        } else {
                            Toast.makeText(getContext(), "Delete failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "No user is currently signed in.", Toast.LENGTH_SHORT).show();
        }
    }
}
