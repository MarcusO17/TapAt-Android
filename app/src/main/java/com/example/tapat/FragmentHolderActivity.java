package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tapat.helpers.dbHelper;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.Stack;

// this class holds all the fragments that in the lecturer view
public class FragmentHolderActivity extends AppCompatActivity {

    NavigationView sideNavigationView;
    DrawerLayout sideNavigationLayout;
    ImageButton sideNavigationButton;
    TextView sideNavigationUsername;
    dbHelper db;
    String sessionID = "";
    ImageButton backButton;
    // this is a function for back button
    // when the button is triggered the stack is popped, then the top is the stack is the current fragment
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.classlistframelayout);
        Log.d("Fragment Type", currentFragment.getClass().getName());
        if (fragmentManager.getBackStackEntryCount() >1) {
            //dialog triggered for attendancelistfragment
            if (currentFragment instanceof AttendanceListFragment) {
                Log.d("inside on backpress", "it should trigger exit confirmation dialog");
                ((AttendanceListFragment) currentFragment).showExitConfirmationDialog();
            }
            else {
                fragmentManager.popBackStack();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        //init DB
        db = new dbHelper(this);

        backButton = (ImageButton) findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Setting Text
        TextView title = (TextView) findViewById(R.id.fragmentholdertitle);
        TextView actionBarTitle = (TextView) findViewById(R.id.titleactionbar);
        title.setText("Course List");
        actionBarTitle.setText("");

        CourseListFragment courseListFragment = new CourseListFragment();

        //Transfer SessionID
        sessionID = getIntent().getStringExtra("sessionID");
        Bundle args = new Bundle();
        args.putString("sessionID",sessionID);
        courseListFragment.setArguments(args);

        //showing the first fragment at the beginning
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.classlistframelayout, courseListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        // side navigation bar
        sideNavigationButton = (ImageButton) findViewById(R.id.sidebar_button); // in actionbar
        sideNavigationLayout = (DrawerLayout) findViewById(R.id.side_navigation_layout);
        sideNavigationView = (NavigationView) findViewById(R.id.side_navigation_view);

        View sidebarHeader = sideNavigationView.getHeaderView(0);
        sideNavigationUsername = sidebarHeader.findViewById(R.id.side_navigation_user_name);
        sideNavigationUsername.setText(db.getNamefromID("Lecturer",sessionID));

        // function for opening the side navigation bar
        sideNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sideNavigationLayout.openDrawer(GravityCompat.START);
            }
        });
        // setting side navigation bar buttons
        sideNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation item clicks here
                if (item.getItemId() == R.id.side_navigation_home_button) {
                    int size = fragmentManager.getBackStackEntryCount();
                    for (int i = 0; i<(size-1);i++) {
                        onBackPressed();
                    }

                }
                else if (item.getItemId() == R.id.logout_button) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                sideNavigationLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}