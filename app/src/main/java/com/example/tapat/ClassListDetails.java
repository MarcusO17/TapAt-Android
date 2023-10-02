package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tapat.model.ClassListItem;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ClassListDetails extends AppCompatActivity {
    DrawerLayout sideNavigationLayout;
    Button sideNavigationButton;
    NavigationView sideNavigationView;
    FrameLayout frameLayoutFragment;
    Button backButton;
    TextView classListTitle;

    public List getIncomingIntent(){
        List<String> courseDetails = new ArrayList<>();
        if(getIntent().hasExtra("course_code")  && getIntent().hasExtra("course_name")) {
            String courseCode = getIntent().getStringExtra("course_code");
            String courseName = getIntent().getStringExtra("course_name");

            setTitle(courseCode, courseName);
            courseDetails.add(courseCode);
            courseDetails.add(courseName);

        }
        return courseDetails;
    }

    public void setTitle(String courseCode, String courseName){
        classListTitle = (TextView) findViewById(R.id.classlisttitle);
        classListTitle.setText(courseCode +" - " + courseName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list_details);
        List<String> courseDetails = getIncomingIntent();

        Bundle args = new Bundle();
        args.putString("course_code",courseDetails.get(0));
        args.putString("course_name",courseDetails.get(1));

        ClassListFragment classListFragment = new ClassListFragment();

        classListFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.classlistframelayout, classListFragment);
        fragmentTransaction.commit();


        backButton = (Button) findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implement back button here
            }
        });

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