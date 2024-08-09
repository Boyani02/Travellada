package com.example.travelada.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelada.R;
import com.example.travelada.databinding.ActivitySignUpBinding;

public class SignUpActivity extends BaseActivity {

    ActivitySignUpBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String name = binding.nameEdt.getText().toString();
            String email = binding.userEdt.getText().toString();
            String username = binding.usernameEdt.getText().toString();
            String password = binding.passEdt.getText().toString();

            if(password.length()<6){
                Toast.makeText(SignUpActivity.this,"Password must be 6 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, task -> {
                if(task.isSuccessful()){
                    Log.i(TAG,"onComplete: ");
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                }else{
                    Log.i(TAG, "failure: " +task.getException());
                    Toast.makeText(SignUpActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                }
            });

        });
    }
}