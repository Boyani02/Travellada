package com.example.travelada.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelada.Fragment.HomeFragment;
import com.example.travelada.Fragment.NotificationFragment;
import com.example.travelada.Fragment.ProfileFragment;
import com.example.travelada.R;


import com.example.travelada.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends BaseActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ReplaceFragment(new HomeFragment());

        binding.navBottomBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.notification){
                    ReplaceFragment(new NotificationFragment());

                }else if(id == R.id.profile){
                    ReplaceFragment(new ProfileFragment());
                }
                return true;
            }
        });

    }

    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}