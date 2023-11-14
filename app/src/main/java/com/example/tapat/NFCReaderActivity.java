package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tapat.model.StudentItem;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/********************************************************************
 * *******************DOCUMENTATION NEEDED!!!!***********************
 * ******************************************************************
 */
public class NFCReaderActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    String contentMessage;
    Button stopScanningButton;
    ArrayList<String> studentAttendedList = new ArrayList<>();
    List<StudentItem> studentList;
    List<String> studentIDList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcreader);

        studentList = getIntent().getParcelableArrayListExtra("student_list");

        for (StudentItem student: studentList) {
            studentIDList.add(student.getStudentID());
        }

        //Get NFCAdapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        stopScanningButton = (Button) findViewById(R.id.stopscanningbutton);

        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NFCReaderActivity.this);
                builder.setCancelable(true);
                builder.setMessage("Stop Scanning?");
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //send information back to attendance list fragment
                        Intent intent = new Intent("com.example.tapat.ACTION_NFC_DATA");
                        intent.putStringArrayListExtra("attendedList", studentAttendedList);
                        sendBroadcast(intent);
                        //End Activity
                        finish();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        // Extract NDEF messages from the received intent
        Parcelable[] parcelables = intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);
        if (parcelables != null && parcelables.length > 0) {
            //Reading Text from Tags
            contentMessage = readTextFromTag((NdefMessage) parcelables[0]);

            Log.d("SCANNED INFO", contentMessage); //Logging

            //Processing the Acquired Message from Tag
            String[] studentInfo = contentMessage.split(":");
            String studentID = studentInfo[0].trim();
            Log.d("Student ID List Info", studentIDList.size()+"");

            for (String i : studentIDList) {
                Log.d("inside array", i); //Check if in Array
            }

            //If inside valid students!
            if (studentIDList.contains(studentID)) {
                //Display
                contentDialog(contentMessage);
                studentAttendedList.add(studentID);
            }else {
                contentWrongDialog(contentMessage);
            }

            Log.d("SCANNER INFO", studentID);
        } else {
            Toast.makeText(this, "No NDEF Messages Found!", Toast.LENGTH_SHORT).show();
        }

    }


    //Document : https://developer.android.com/develop/ui/views/components/dialogs
    private void contentDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Scan Successful!");


        AlertDialog dialog = builder.create();
        dialog.show();
        //auto closing
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        }, 1000);
    }
    private void contentWrongDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Scan Failed!");
        builder.setMessage("Student Do Not Belong In The Class");


        AlertDialog dialog = builder.create();
        dialog.show();
        //auto closing
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.cancel();
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Intent ensures NFCWriterLogic is running , and prevents multiple calls to run NFCWriterLogic
        Intent intent = new Intent(this, NFCReaderActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
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

    private String readTextFromTag(NdefMessage ndefMessage) {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];
            //Get the payload converted into text
            String tagContent = getTextfromNdefRecord(ndefRecord);
            return tagContent;
        } else {
            Toast.makeText(this, "No NDEF Records Found!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public String getTextfromNdefRecord(NdefRecord ndefRecord) {

        String tagContent = null;
        try {
            // Extract payload (data) from the NdefRecord
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            // Extract text content from the payload, considering language size and text encoding
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        } catch (IndexOutOfBoundsException e) {
            Log.e("getTextNdefRecord", e.getMessage(), e);
            tagContent = "Empty!";
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }
}