package com.example.tapat;

import android.content.Intent;
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

public class AdminActivity extends AppCompatActivity {

    private LinearLayout navigationSection;
    private View overlayView;
    private Button buttonA;
    private boolean isMenuExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminactivity);

        // Initialize the navigationSection
        navigationSection = findViewById(R.id.navigationSection);
        overlayView = findViewById(R.id.navigationSection);
        buttonA = findViewById(R.id.buttonA);

        // Retrieve the fragmentToLoad data from the intent
        String fragmentToLoad = getIntent().getStringExtra("fragmentToLoad");

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
                Intent readnfcIntent = new Intent(AdminActivity.this, AdminNFCReader.class);
                startActivity(readnfcIntent);
                finish();
            }
        });

        // Set click listener for nfcWriterButton
        nfcWriterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent writenfcIntent = new Intent(AdminActivity.this, AdminNFCwriter.class);
                startActivity(writenfcIntent);
                finish();
            }
        });

        // Set click listener for logoutButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(AdminActivity.this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });

        if ("Student".equals(fragmentToLoad)) {
            replaceFragment(AdminList.newInstance("Student"));
        } else if ("Lecturer".equals(fragmentToLoad)) {
            replaceFragment(AdminList.newInstance("Lecturer"));
        } else if ("Course".equals(fragmentToLoad)) {
            replaceFragment(AdminList.newInstance("Course"));
        } else {
            //default
            replaceFragment(new AdminDashboard());
        }
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