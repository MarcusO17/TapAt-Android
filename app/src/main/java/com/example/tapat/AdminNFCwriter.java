package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.LinearLayout;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.adminfragments.ButtonListAdapter;
import com.example.tapat.helpers.dbHelper;

import java.util.Arrays;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class AdminNFCwriter extends AppCompatActivity {

    //Init NFC Adapter
    public NfcAdapter nfcAdapter;
    String message;
    boolean submitted;

    //self declared
    private ButtonListAdapter buttonListAdapter;
    private dbHelper db;
    private Dialog nfcDialog;
    private LinearLayout navigationSection;
    private View overlayView;
    private Button buttonA;
    private boolean isMenuExpanded = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminnfcwriter);

        //Start NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Initialize the navigationSection
        navigationSection = findViewById(R.id.navigationSection);
        overlayView = findViewById(R.id.navigationSection);
        buttonA = findViewById(R.id.buttonA);

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
                Intent readnfcIntent = new Intent(AdminNFCwriter.this, AdminNFCReader.class);
                startActivity(readnfcIntent);
                // Close the current activity
                finish();
            }
        });

        // Set click listener for nfcWriterButton
        nfcWriterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });

        // Set click listener for logoutButton
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(AdminNFCwriter.this, LoginActivity.class);
                // Clean up the activity
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });

        // Set the title in the TextView
        TextView textView = findViewById(R.id.textViewNFCWriter);
        if (textView != null) {
            textView.setText("NFC Writer");
        }

        // Initialize the dbHelper
        db = new dbHelper(this);

        String [] student = db.getNames("Students");

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ButtonListAdapter
        buttonListAdapter = new ButtonListAdapter(this);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(buttonListAdapter);

        // Initialize views
        EditText searchEditText = findViewById(R.id.searchEditText);


        //Test if NFC is on or off(write data)
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
            buttonListAdapter.setOnItemClickListener(new ButtonListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String buttonText) {
                    submitted = true;
                    message = buttonText.toString();
                    showNfcDialog("Detecting Card!");
                    Toast.makeText(AdminNFCwriter.this,"Scan your NFC Tag Now!",Toast.LENGTH_SHORT).show();
                    enableForegroundDispatchSystem();
                }

            });
        }else{
            //Opens NFC Settings if NFC is Off
            Toast.makeText(this, "NFC Not Detected!, Please Turn On NFC!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }

        // Initialize and populate the button list
        buttonListAdapter.setData(Arrays.asList(student));

        // Implement search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the button list based on the search text
                buttonListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed in this case
            }
        });

    }

    //Called when something happens, bleh need better explanation.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if(submitted == false){
            Toast.makeText(this,"Type a message!",Toast.LENGTH_SHORT).show();
            return;
        }else{
            //If Intent triggered has an NFC Tag! Run below
            if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
                updateNfcDialogStatus("NFC Tag Found!");
                Toast.makeText(this,"NFC Tag Found!", Toast.LENGTH_SHORT).show();

                //Assign tag to the NFC Tag detected from the Intent.
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                //Message to be written.
                NdefMessage ndefMessage = createNdefMessage(message);

                writeNdefMessage(tag, ndefMessage);
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }


    //Prepares App to accept NFC Detection/Activity
    private void enableForegroundDispatchSystem(){

        //Intent ensures NFCWriterLogic is running , and prevents multiple calls to run NFCWriterLogic
        Intent intent = new Intent(this, AdminNFCwriter.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
        //Conditions for NFC Tag(Now Empty)
        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);

    }

    //Disable NFC listening when app closed
    private void disableForegroundDispatchSystem(){
        nfcAdapter.disableForegroundDispatch(this);
    }

    //formatting tag to NDEF mode.
    private void formatTag(Tag tag, NdefMessage ndefMessage){
        try{

            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if(ndefFormatable == null){
                Toast.makeText(this, "Tag is not NDEF Formatable :(", Toast.LENGTH_SHORT).show();
            }

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

        }catch(Exception e){
            Log.e("formatTag", e.getMessage());
        }
    }

    //DOCUMENTATION NEEDED !! TBC 7/9/2023. Everything below
    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage){
        try{
            // If tag is empty
            if(tag == null){
                Toast.makeText(this, "Tag cannot be null!", Toast.LENGTH_SHORT).show();
                return;
            }

            Ndef ndef = Ndef.get(tag);
            if(ndef == null){
                //Formats Tag with NDEF Format and Writes Message
                formatTag(tag,ndefMessage);
            }else{
                ndef.connect();
                //Check if NDEF message is writable
                if(!ndef.isWritable()){
                    Toast.makeText(this, "Tag is not writable!", Toast.LENGTH_SHORT).show();
                    ndef.close();
                    return;
                }
                if(message == null || message.length() == 0){
                    Toast.makeText(this, "Tag Resetting!", Toast.LENGTH_SHORT).show();
                    ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_WELL_KNOWN, null, null, null)));
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();

                updateNfcDialogStatus("Tag Written!");
                Toast.makeText(this, "Tag Written!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            updateNfcDialogStatus("Failed to Write");
            Log.e("writeNdefMessage", e.getMessage());
        }
    }


    private NdefRecord createTextRecord(String content){
        try{
            //Converts content into NDEF formatable, bytecode for storage
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLength = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language,0,languageSize);
            payload.write(text, 0 ,textLength);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        }catch(Exception e){
            Log.e("createTextRecord", e.getMessage());
        }
        return null;
    }

    private NdefMessage createNdefMessage(String content){
        NdefRecord ndefRecord = createTextRecord(content);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});
        return ndefMessage;
    }


    private void showNfcDialog(String initialMessage) {
        nfcDialog = new Dialog(this);
        nfcDialog.setContentView(R.layout.nfc_dialog);

        ImageView nfcStatusTextView = nfcDialog.findViewById(R.id.nfcStatusTextView);
        //nfcStatusTextView.setText(initialMessage);

        nfcDialog.setCanceledOnTouchOutside(false);
        nfcDialog.show();
    }

    private void updateNfcDialogStatus(String message) {
        if (nfcDialog != null && nfcDialog.isShowing()) {
            ImageView nfcStatusTextView = nfcDialog.findViewById(R.id.nfcStatusTextView);
                nfcStatusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the user taps anywhere on the screen
                        nfcDialog.dismiss();
                    }
                });
                // If the message is "Write Complete" or "Failed to Write," allow the user to close the dialog by tapping anywhere
                if ("Tag Written!".equals(message) || "Failed to Write".equals(message)) {
                    nfcDialog.setCanceledOnTouchOutside(true);
                }
        }
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