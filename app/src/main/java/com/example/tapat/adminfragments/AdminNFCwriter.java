package com.example.tapat.adminfragments;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.helpers.dbHelper;

import java.io.IOException;
import java.util.Arrays;

public class AdminNFCwriter extends Fragment {

    private ButtonListAdapter buttonListAdapter;
    private dbHelper db;
    private Dialog nfcDialog;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    private boolean isNfcWriteComplete = false;

    public AdminNFCwriter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminnfcwriter, container, false);
        //Init DB
        db = new dbHelper(getContext());

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.textViewNFCWriter);
        if (textView != null) {
            textView.setText("AdminNFCWriter");
        }

        String [] student = db.getNames("Students");

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize ButtonListAdapter
        buttonListAdapter = new ButtonListAdapter(requireContext());

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(buttonListAdapter);

        // Initialize views
        EditText searchEditText = view.findViewById(R.id.searchEditText);

        buttonListAdapter.setOnItemClickListener(new ButtonListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String buttonText) {
                writeNfc(buttonText);
            }
        });

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

        // Initialize NFC-related variables
        pendingIntent = PendingIntent.getActivity(requireContext(), 0, new Intent(requireContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*"); // Handles all MIME based dispatches. You should customize this based on your needs.
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Failed to initialize NFC", e);
        }
        intentFiltersArray = new IntentFilter[]{ndef};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};

        return view;
    }

    private void writeNfc(String buttonName) {
        // Step 1: Check NFC availability and state
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext());
        if (nfcAdapter == null) {
            // Device has no NFC support
            showToast("This device has no NFC");
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            // NFC is disabled, prompt the user to enable it
            showToast("Please Enable your NFC");
            return;
        }

        // Step 2: Show a dialog "Detecting Card" that doesn't close until an NFC card or tag is detected. The dialog file will be nfc_dialog.xml
        showNfcDialog("Detecting Card");

        // Step 3: NFC card detected, change the dialog message to "NFC Detected" and overwrite everything inside the card with buttonName
        // You can use NfcAdapter's enableForegroundDispatch to handle NFC card detection
        nfcAdapter.enableForegroundDispatch(requireActivity(), pendingIntent, intentFiltersArray, techListsArray);

        // Step 4: Change the dialog message to "Complete writing" + buttonName, click anywhere on the screen to close the dialog
        // You can update the dialog text when the NFC write is complete
    }

    private void showNfcDialog(String initialMessage) {
        nfcDialog = new Dialog(requireContext());
        nfcDialog.setContentView(R.layout.nfc_dialog);

        TextView nfcStatusTextView = nfcDialog.findViewById(R.id.nfcStatusTextView);
        nfcStatusTextView.setText(initialMessage);

        nfcDialog.setCanceledOnTouchOutside(false);
        nfcDialog.show();
    }

    private void showToast(String message) {
        Context context = requireContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 3000);
    }

    private void writeDataToNfcTag(Tag tag, String data) {
        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            try {
                ndef.connect();
                NdefMessage message = new NdefMessage(NdefRecord.createMime("text/plain", data.getBytes()));
                ndef.writeNdefMessage(message);
                ndef.close();
            } catch (IOException | FormatException e) {
                e.printStackTrace();
            }
        } else {
            showToast("NFC tag is not NDEF compatible");
        }
    }

    public void handleNfcIntent(Intent intent, String buttonName) {
        // Handle the NFC intent and write data to the NFC tag here
        // You can access the NFC data from the intent and use buttonName to write to the tag
        // Follow the NFC writing logic from the previous response in this method

        // Check if the NFC tag contains NDEF data
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            // NFC tag detected with NDEF data
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                // Write the buttonName to the NFC tag
                writeDataToNfcTag(tag, buttonName);

                // Mark NFC write as complete
                isNfcWriteComplete = true;

                // Update the dialog message
                updateNfcDialogStatus("Complete writing " + buttonName);
            }
        }
    }

    private void updateNfcDialogStatus(String message) {
        if (nfcDialog != null) {
            TextView nfcStatusTextView = nfcDialog.findViewById(R.id.nfcStatusTextView);
            if (nfcStatusTextView != null) {
                nfcStatusTextView.setText(message);
                nfcStatusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the user taps anywhere on the screen
                        if (isNfcWriteComplete) {
                            nfcDialog.dismiss();
                        }
                    }
                });
            }
        }
    }
}
