package com.example.tapat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tapat.adminfragments.AdminDashboard;
import com.example.tapat.adminfragments.AdminList;
import com.example.tapat.adminfragments.AdminNFCReader;
import com.example.tapat.adminfragments.AdminNFCwriter;

public class AdminActivity extends AppCompatActivity {

    private LinearLayout navigationSection;
    private View overlayView;
    private Button buttonA;
    private boolean isMenuExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminactivity);

        // Initialize the navigationSection and buttonA
        navigationSection = findViewById(R.id.navigationSection);
        overlayView = findViewById(R.id.navigationSection);
        buttonA = findViewById(R.id.buttonA);

        // Set click listener for buttonA to expand/contract the menu
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMenu();
            }
        });

        // Initially, the menu is contracted
        navigationSection.setVisibility(View.GONE);

        // Initialize buttons
        Button profileButton = findViewById(R.id.profileButton);
        Button dashboardButton = findViewById(R.id.dashboardButton);
        Button studentButton = findViewById(R.id.studentButton);
        Button lecturerButton = findViewById(R.id.lecturerButton);
        Button courseButton = findViewById(R.id.courseButton);
        Button nfcReaderButton = findViewById(R.id.nfcReaderButton);
        Button nfcWriterButton = findViewById(R.id.nfcWriterButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set click listener for the overlay view to contract the menu
        overlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMenuExpanded) {
                    toggleMenu();
                }
            }
        });

        // Set click listener for profileButton
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle profile button click
                // Replace the fragment with UserProfile
                replaceFragment(new UserProfile());
            }
        });

        // Set click listener for dashboardButton
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace the fragment with AdminDashboard
                replaceFragment(new AdminDashboard());
                // Close the side menu
                toggleMenu();
            }
        });

        // Set click listener for studentButton
        studentButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view) {
                 // Replace the fragment with AdminDashboard
                 replaceFragment(AdminList.newInstance("Student"));
                 // Close the side menu
                 toggleMenu();
             }
        });

        // Set click listener for lecturerButton
        lecturerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Replace the fragment with AdminDashboard
                replaceFragment(AdminList.newInstance("Lecturer"));
                // Close the side menu
                toggleMenu();
            }
        });

        // Set click listener for courseButton
        courseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Replace the fragment with AdminDashboard
                replaceFragment(AdminList.newInstance("Course"));
                // Close the side menu
                toggleMenu();
            }
        });

        // Set click listener for nfcReaderButton
        nfcReaderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Replace the fragment with AdminDashboard
                replaceFragment(new AdminNFCReader());
                // Close the side menu
                toggleMenu();
            }
        });

        // Set click listener for nfcWriterButton
        nfcWriterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Replace the fragment with AdminDashboard
                replaceFragment(new AdminNFCwriter());
                // Close the side menu
                toggleMenu();
            }
        });

        // Set click listener for logoutButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout button click
                // Implement your logout logic here
                // Close the side menu
                toggleMenu();
            }
        });


        // Initially, show AdminDashboard
        replaceFragment(new AdminDashboard());
    }

    // Function to toggle the menu (expand/contract)
    private void toggleMenu() {
        if (isMenuExpanded) {
            navigationSection.setVisibility(View.GONE);
        } else {
            navigationSection.setVisibility(View.VISIBLE);
        }
        isMenuExpanded = !isMenuExpanded;
    }

    // Function to replace fragments
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}
