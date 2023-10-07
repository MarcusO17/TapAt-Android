package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class LecturerActivity extends AppCompatActivity {
    List<RecyclerItem> courseList = new ArrayList<>();
    DrawerLayout sideNavigationLayout;
    Button sideNavigationButton;
    NavigationView sideNavigationView;
    RecyclerView homepageCourseListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectureractivity);

        homepageCourseListRecyclerView = findViewById(R.id.lecturerHomepageCourseList);

        //query and put the courses in the list
        RecyclerItem course1 = new RecyclerItem("Android Development Skill");
        RecyclerItem course2 = new RecyclerItem("Data Science");
        RecyclerItem course3 = new RecyclerItem("Software Engineering");
        RecyclerItem course4 = new RecyclerItem("Software Engineering");
        RecyclerItem course5 = new RecyclerItem("Software Engineering");
        RecyclerItem course6 = new RecyclerItem("Software Engineering");

        courseList.add(course1);
        courseList.add(course2);
        courseList.add(course3);
        courseList.add(course4);
        courseList.add(course5);
        courseList.add(course6);

        //set up the recycler view so that it shows in here
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false);
        homepageCourseListRecyclerView.setLayoutManager(linearLayoutManager);
        homepageCourseListRecyclerView.setHasFixedSize(true);

        RecyclerViewAdapter studentListAdapter = new RecyclerViewAdapter(courseList);
        homepageCourseListRecyclerView.setAdapter(studentListAdapter);






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
    }
}