package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LecturerHomepageActivity extends AppCompatActivity {

    RecyclerView homepageCourseListRecyclerView;
    List<RecyclerItem> courseList;

    DrawerLayout sideNavigationLayout;
    Button sideNavigationButton;
    NavigationView sideNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturerhomepageactivity);

        //when you log in you have to change update the side navigation menu lecturer name

        sideNavigationButton = (Button) findViewById(R.id.side_navigation_button);
        sideNavigationLayout = (DrawerLayout) findViewById(R.id.side_navigation_layout);
        sideNavigationView = (NavigationView) findViewById(R.id.side_navigation_view);

        sideNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationLayout.openDrawer(GravityCompat.START);
            }
        });

        sideNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation item clicks here
                if (item.getItemId() == R.id.side_navigation_home_button) {
                    //switch to homepage activity/fragment
                    ;
                }
                else if (item.getItemId() == R.id.logout_button) {
                    //switch to logout activity
                    ;
                }
                sideNavigationLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        homepageCourseListRecyclerView.findViewById(R.id.lecturerHomepageCourseList);

        //query and put the courses in the list


        RecyclerViewAdapter studentListAdapter = new RecyclerViewAdapter(courseList);
        homepageCourseListRecyclerView.setAdapter(studentListAdapter);
    }
}