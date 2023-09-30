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
import android.widget.FrameLayout;

import com.example.tapat.adapter.CourseItemViewAdapter;
import com.example.tapat.model.CourseItem;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class LecturerHomepageActivity extends AppCompatActivity {
    List<CourseItem> courseList = new ArrayList<>();
    DrawerLayout sideNavigationLayout;
    Button sideNavigationButton;
    NavigationView sideNavigationView;
    RecyclerView homepageCourseListRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturerhomepageactivity);

        homepageCourseListRecyclerView = findViewById(R.id.lecturerHomepageCourseList);

        //query and put the courses in the list
        CourseItem course1 = new CourseItem("Android Development Skill", "A202SGI");
        CourseItem course2 = new CourseItem("Data Science", "INT5005CEM");
        CourseItem course3 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course4 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course5 = new CourseItem("Software Engineering","INT5001CEM");
        CourseItem course6 = new CourseItem("Software Engineering","INT5001CEM");

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

        CourseItemViewAdapter studentListAdapter = new CourseItemViewAdapter(courseList);
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