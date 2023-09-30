package com.example.tapat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminactivity);

        // Initialize buttons
        Button buttonA = findViewById(R.id.buttonA);
        Button profileButton = findViewById(R.id.profileButton);

        // Set click listeners for buttons
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button A click
                // Replace the fragment with AdminDashboard
                replaceFragment(new AdminDashboard());
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle profile button click
                // Replace the fragment with UserProfile
                replaceFragment(new UserProfile());
            }
        });

        // Initially, show AdminDashboard
        replaceFragment(new AdminDashboard());
    }

    // Function to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}

