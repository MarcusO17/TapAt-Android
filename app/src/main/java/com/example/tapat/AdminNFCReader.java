package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tapat.helpers.dbHelper;

import java.io.UnsupportedEncodingException;


public class AdminNFCReader extends AppCompatActivity {

    //Init NFC Adapter
    NfcAdapter nfcAdapter;
    String contentMessage;

    //self declared
    private dbHelper db;
    private LinearLayout navigationSection;
    private View overlayView;
    private Button buttonA;
    private boolean isMenuExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminnfcreader);

        //Start NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Initialize the navigationSection
        navigationSection = findViewById(R.id.navigationSection);
        overlayView = findViewById(R.id.navigationSection);
        buttonA = findViewById(R.id.buttonA);

        // Initialize the dbHelper
        db = new dbHelper(this);

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
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for studentButton
        studentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                // Pass any necessary data to AdminActivity using extras
                intent.putExtra("fragmentToLoad", "Student");
                startActivity(intent);
                // Close the current activity
                finish();
            }
        });

        // Set click listener for lecturerButton
        lecturerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                // Pass any necessary data to AdminActivity using extras
                intent.putExtra("fragmentToLoad", "Lecturer");
                startActivity(intent);
                // Close the current activity
                finish();
            }
        });

        // Set click listener for courseButton
        courseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                // Pass any necessary data to AdminActivity using extras
                intent.putExtra("fragmentToLoad", "Course");
                startActivity(intent);
                // Close the current activity
                finish();
            }
        });

        // Set click listener for nfcReaderButton
        nfcReaderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });

        // Set click listener for nfcWriterButton
        nfcWriterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent writenfcIntent = new Intent(AdminNFCReader.this, AdminNFCwriter.class);
                startActivity(writenfcIntent);
                // Close the current activity
                finish();
            }
        });

        // Set click listener for logoutButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(AdminNFCReader.this, LoginActivity.class);
                // Clean up the activity
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });

        // Set the title in the TextView
        TextView textView = findViewById(R.id.displayFragmentTitleTextView);
        if (textView != null) {
            textView.setText("AdminNFCReader");
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Parcelable[] parcelables = intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);
        if (parcelables != null && parcelables.length > 0) {
            contentMessage = readTextFromTag((NdefMessage) parcelables[0]);
            contentDialog(contentMessage);
            changeDisplayText(contentMessage);
        } else {
            Toast.makeText(this, "No NDEF Messages Found!", Toast.LENGTH_SHORT).show();
        }

    }

    private void contentDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Scanned");


        AlertDialog dialog = builder.create();
        dialog.show();
        //auto closing
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                dialog.cancel();
            }
        }, 1000);
    }

    private void changeDisplayText(String message){
        // Initialize and handle NFC Reader related UI components and functionality here
        String[] studentData = db.getSingularData("Student",message);

            try {
                TextView nameView = findViewById(R.id.nameText);
                nameView.setText(studentData[1]);

                TextView idView = findViewById(R.id.idText);
                idView.setText(studentData[0]);

                TextView programView = findViewById(R.id.programText);
                programView.setText(studentData[2]);
            }catch (Exception e) {
                Log.d("NFC","Something went wrong");
                Toast.makeText(AdminNFCReader.this,"Error!",Toast.LENGTH_SHORT).show();
         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Intent ensures NFCWriterLogic is running , and prevents multiple calls to run NFCWriterLogic
        Intent intent = new Intent(this, AdminNFCReader.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
        //Conditions for NFC Tag(Now Empty)
        IntentFilter[] intentFilters = new IntentFilter[]{};

        //Start Scanning
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Stop Scanning
        nfcAdapter.disableForegroundDispatch(this);
    }

    private String readTextFromTag(NdefMessage ndefMessage){
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length > 0){
            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = getTextfromNdefRecord(ndefRecord);
            return tagContent;
        }else{
            Toast.makeText(this, "No NDEF Records Found!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String getTextfromNdefRecord(NdefRecord ndefRecord){

        String tagContent =null;
        try {
            // Extract payload (data) from the NdefRecord
            byte[] payload = ndefRecord.getPayload();

            //Process into string
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        }catch(IndexOutOfBoundsException e) {
            Log.e("getTextNdefRecord", e.getMessage(),e);
            tagContent = "Empty!";
        }catch(UnsupportedEncodingException e){
            Log.e("getTextNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

    private void toggleMenu() {
        if (isMenuExpanded) {
            navigationSection.setVisibility(View.GONE);
        } else {
            navigationSection.setVisibility(View.VISIBLE);
        }
        isMenuExpanded = !isMenuExpanded;
    }
}