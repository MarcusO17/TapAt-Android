package com.example.tapat.adminfragments;

import android.app.Dialog;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapat.R;
import com.example.tapat.helpers.dbHelper;

import java.util.Arrays;

public class AdminNFCwriter extends Fragment {

    private ButtonListAdapter buttonListAdapter;
    private dbHelper db;
    private Dialog nfcDialog;

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

        // Step 2: show a dialog"Detecting Card" that doesn't close until a NFC card or tag is detected .the dialog file will be nfc_dialog.xml
        showNfcDialog("Detecting Card");

        // Step 3: NFC card detected,change the dialog message to "NFC Detected" and Overwrites everything inside the card with buttonName

        // Step 4: change the dialog message to "Complete writing"+buttonName,click anywhere on the screen to close the dialog

        //Note:the overall process happens in this fragment without switching fragment or activity.

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
}