package com.example.tapat;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Init NFC Adapter
    public NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this); //Start NFC Adapter

        //Test if NFC is on or off
        if(nfcAdapter != null && nfcAdapter.isEnabled()){
        }else{
            //Opens NFC Settings if NFC is Off
            Toast.makeText(this, "NFC Not Detected!, Please Turn On NFC!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
    }

    //Called when something happens, bleh need better explanation.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        //If Intent triggered has an NFC Tag! Run below
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            Toast.makeText(this,"NFC Tag!", Toast.LENGTH_SHORT).show();

            //Assign tag to the NFC Tag detected from the Intent.
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //Message to be written.
            NdefMessage ndefMessage = createNdefMessage("Testing! It Works! Use External App to Reset");

            writeNdefMessage(tag, ndefMessage);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }


    //Prepares App to accept NFC Detection/Activity
    private void enableForegroundDispatchSystem(){

        //Intent ensures MainActivity is running , and prevents multiple calls to run MainActivity
        Intent intent = new Intent(this, MainActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
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
                if(!ndef.isWritable()){
                    Toast.makeText(this, "Tag is not writable!", Toast.LENGTH_SHORT).show();
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();

                Toast.makeText(this, "Tag Written!", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.e("writeNdefMessage", e.getMessage());
        }
    }

    //Uhoh DOCU AND EXPLAINATION NEEDED!!!
    private NdefRecord createTextRecord(String content){
        try{
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
}