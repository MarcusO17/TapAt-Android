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
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private NfcAdapter nfcAdapter;
    private boolean isNfcWriteComplete = false;

    public AdminNFCwriter() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminnfcwriter, container, false);
        // Init DB
        db = new dbHelper(getContext());

        // Set the title in the TextView
        TextView textView = view.findViewById(R.id.textViewNFCWriter);
        if (textView != null) {
            textView.setText("AdminNFCWriter");
        }

        String[] student = db.getNames("Students");

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
                buttonListAdapter.setSelectedButtonName(buttonText);
                // Show the "Detecting NFC Card" dialog when the button is clicked
                showNfcDialog("Detecting NFC Card");
                // Start NFC reader mode
                startNfcReader(buttonText);
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
        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext());

        pendingIntent = PendingIntent.getActivity(requireContext(), 0, new Intent(requireContext(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
        intentFiltersArray = new IntentFilter[]{new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)};
        techListsArray = new String[][]{new String[]{NfcF.class.getName()}};

        return view;
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

    @Override
    public void onResume() {
        super.onResume();
        // Enable NFC reader mode when the fragment is in the foreground
        if (nfcAdapter != null) {
            Bundle options = new Bundle();
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 5000); // Adjust the delay as needed
            nfcAdapter.enableReaderMode(requireActivity(), new NfcAdapter.ReaderCallback() {
                @Override
                public void onTagDiscovered(Tag tag) {
                    // Handle the NFC tag discovery here
                    String buttonText = buttonListAdapter.getSelectedButtonName();
                    if (buttonText != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // NFC card detected, change the dialog message to "NFC Detected"
                                updateNfcDialogStatus("NFC Detected");
                                // Write the buttonName to the NFC tag
                                boolean writeSuccess = writeDataToNfcTag(tag, buttonText);
                                // Mark NFC write as complete
                                if (writeSuccess) {
                                    updateNfcDialogStatus("Write Complete");
                                } else {
                                    updateNfcDialogStatus("Failed to Write");
                                }
                            }
                        });
                    }
                }
            }, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, options);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Disable NFC reader mode when the fragment is not in the foreground
        if (nfcAdapter != null) {
            nfcAdapter.disableReaderMode(requireActivity());
        }
    }

    private boolean writeDataToNfcTag(Tag tag, String data) {
        try {
            if (tag == null) {
                Toast.makeText(requireContext(), "Tag cannot be null!", Toast.LENGTH_SHORT).show();
                return false;
            }

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // Formats Tag with NDEF Format and Writes Message
                formatTag(tag, data);
            } else {
                ndef.connect();
                if (!ndef.isWritable()) {
                    Toast.makeText(requireContext(), "Tag is not writable!", Toast.LENGTH_SHORT).show();
                    ndef.close();
                    return false;
                }
                if (data == null || data.length() == 0) {
                    Toast.makeText(requireContext(), "Tag Resetting!", Toast.LENGTH_SHORT).show();
                    ndef.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_WELL_KNOWN, null, null, null)));
                } else {
                    NdefMessage message = new NdefMessage(NdefRecord.createMime("text/plain", data.getBytes()));
                    ndef.writeNdefMessage(message);
                    Toast.makeText(requireContext(), "Tag Written!", Toast.LENGTH_SHORT).show();
                }
                ndef.close();
            }
            return true;
        } catch (Exception e) {
            Log.e("writeDataToNfcTag", e.getMessage());
            return false;
        }
    }

    private void formatTag(Tag tag, String data) {
        try{

            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if(ndefFormatable == null){
                showToast("Tag is not NDEF Formatable :(");
            }

            ndefFormatable.connect();
            NdefMessage ndefMessage = new NdefMessage(NdefRecord.createMime("text/plain", data.getBytes()));

            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

        }catch(Exception e){
            Log.e("formatTagA", e.getMessage());
        }
    }

    private void updateNfcDialogStatus(String message) {
        if (nfcDialog != null && nfcDialog.isShowing()) {
            TextView nfcStatusTextView = nfcDialog.findViewById(R.id.nfcStatusTextView);
            if (nfcStatusTextView != null) {
                nfcStatusTextView.setText(message);
                nfcStatusTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Dismiss the dialog when the user taps anywhere on the screen
                        nfcDialog.dismiss();
                    }
                });
                // If the message is "Write Complete" or "Failed to Write," allow the user to close the dialog by tapping anywhere
                if ("Write Complete".equals(message) || "Failed to Write".equals(message)) {
                    nfcDialog.setCanceledOnTouchOutside(true);
                }
            }
        }
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

    private void startNfcReader(String buttonText) {
        // Start NFC reader mode
        if (nfcAdapter != null) {
            Bundle options = new Bundle();
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 5000); // Adjust the delay as needed
            nfcAdapter.enableReaderMode(requireActivity(), new NfcAdapter.ReaderCallback() {
                @Override
                public void onTagDiscovered(Tag tag) {
                    // Handle the NFC tag discovery here
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // NFC card detected, change the dialog message to "NFC Detected"
                            updateNfcDialogStatus("NFC Detected");

                            // Write the buttonName to the NFC tag
                            boolean writeSuccess = writeDataToNfcTag(tag, buttonText);

                            // Mark NFC write as complete
                            if (writeSuccess) {
                                updateNfcDialogStatus("Write Complete");
                            } else {
                                updateNfcDialogStatus("Failed to Write");
                            }
                        }
                    });
                }
            }, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, options);
        }
    }

    public void dismissNfcDialog() {
        if (nfcDialog != null && nfcDialog.isShowing()) {
            nfcDialog.dismiss();
        }
    }
}
