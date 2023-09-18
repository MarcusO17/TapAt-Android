package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class HomePageAdminActivity extends AppCompatActivity {

    Button studentListButton;
    Button lecturerListButton;
    Button courseListButton;
    Button nfcReadButton;
    Button nfcWriteButton;

    //this part is for side navigation layout
    DrawerLayout sideNavigationLayout;
    Button sideNavigationButton;
    NavigationView sideNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_admin_activity);

        studentListButton = (Button) findViewById(R.id.student_list_button_admin);
        lecturerListButton = (Button) findViewById(R.id.lecturer_list_button_admin);
        courseListButton = (Button) findViewById(R.id.course_list_button_admin);
        nfcReadButton = (Button) findViewById(R.id.nfc_reader_button_admin);
        nfcWriteButton = (Button) findViewById(R.id.nfc_writer_button_admin);

        sideNavigationLayout = (DrawerLayout) findViewById(R.id.side_navigation_layout);
        sideNavigationButton = (Button) findViewById(R.id.side_navigation_button);

        sideNavigationView = (NavigationView) findViewById(R.id.side_navigation_view);

        // side navigation button should slide out the drawer
        sideNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationLayout.openDrawer(GravityCompat.START);
            }
        });
        //change the activity/fragment when button in the side navigation drawer is tapped
        sideNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation item clicks here
                if (item.getItemId() == R.id.side_navigation_home_button) {
                    //switch to homepage activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.side_navigation_student_list) {
                    //switch to student list activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.side_navigation_lecturer_list) {
                    //switch to lecturer list activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.side_navigation_course_list) {
                    //switch to course list activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.side_navigation_nfc_read) {
                    //switch to nfc read activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.side_navigation_nfc_write) {
                    //switch to nfc write activity/fragment
                    ;
                }
                else if(item.getItemId() == R.id.logout_button) {
                    //switch to log out activity
                    ;
                }
                sideNavigationLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        studentListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to student list fragment/activity
            }
        });

        lecturerListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to lcturer list fragment/activity
            }
        });

        courseListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to course list fragment/activity
            }
        });

        nfcReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to NFC read fragment/activity
            }
        });

        nfcWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to NFC write fragment/activity
            }
        });
    }
}