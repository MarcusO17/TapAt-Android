package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class FragmentHolderActivity extends AppCompatActivity {

    NavigationView sideNavigationView;
    DrawerLayout sideNavigationLayout;
    ImageButton sideNavigationButton;
    ImageButton backButton;

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() >1) {
            // Pop the top fragment from the back stack to go back to the previous fragment
            fragmentManager.popBackStack();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

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
        actionBarTitle.setText("Homepage");

        CourseListFragment courseListFragment = new CourseListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.classlistframelayout, courseListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        // side navigation bar
        sideNavigationButton = (ImageButton) findViewById(R.id.sidebar_button);
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