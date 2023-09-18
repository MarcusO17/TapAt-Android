package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageAdminActivity extends AppCompatActivity {

    Button studentListButton;
    Button lecturerListButton;
    Button courseListButton;
    Button nfcReadButton;
    Button nfcWriteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_admin_activity);

        studentListButton = (Button) findViewById(R.id.student_list_button_admin);
        lecturerListButton = (Button) findViewById(R.id.lecturer_list_button_admin);
        courseListButton = (Button) findViewById(R.id.course_list_button_admin);
        nfcReadButton = (Button) findViewById(R.id.nfc_reader_button_admin);
        nfcWriteButton = (Button) findViewById(R.id.nfc_writer_button_admin);

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