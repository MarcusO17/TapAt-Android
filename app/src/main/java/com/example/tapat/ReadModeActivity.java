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
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/********************************************************************
 * *******************DOCUMENTATION NEEDED!!!!***********************
 * ******************************************************************
 */
public class ReadModeActivity extends AppCompatActivity {

    Tag detectedTag;
    NfcAdapter nfcAdapter;
    String contentMessage;
    TextView tagContentText;
    Button writeModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mode);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        tagContentText =(TextView) findViewById(R.id.tagContentText);
        writeModeButton =(Button) findViewById(R.id.writeModeButton);

        writeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWriteModeActivity();
            }
        });
    }

    public void openWriteModeActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Parcelable[] parcelables = intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);
        if (parcelables != null && parcelables.length > 0) {
            contentMessage = readTextFromTag((NdefMessage) parcelables[0]);
            contentDialog(contentMessage);
        } else {
            Toast.makeText(this, "No NDEF Messages Found!", Toast.LENGTH_SHORT).show();
        }

    }


    //Document : https://developer.android.com/develop/ui/views/components/dialogs
    private void contentDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Scan Successful!");


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

    @Override
    protected void onResume() {
        super.onResume();
        //Intent ensures MainActivity is running , and prevents multiple calls to run MainActivity
        Intent intent = new Intent(this, ReadModeActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE);
        //Conditions for NFC Tag(Now Empty)
        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            byte[] payload = ndefRecord.getPayload();
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
}