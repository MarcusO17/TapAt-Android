package com.example.tapat.helpers;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.tapat.model.AttendanceListRowData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVGenerator {

    public CSVGenerator() {
    }

    public static void generateCSV(Activity activity, List<AttendanceListRowData> attendanceList, String courseName, String classID) {


        String filename = classID + "-" + courseName + "-" + "attendanceList.csv";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            try {
                Uri downloadsDir = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, filename);
                contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                Uri newFileUri = activity.getContentResolver().insert(downloadsDir, contentValues);

                ParcelFileDescriptor pfd = activity.getContentResolver().openFileDescriptor(newFileUri, "w");

                FileWriter csvWriter = new FileWriter(pfd.getFileDescriptor());
                writeCSVContents(csvWriter, attendanceList);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(dir, filename);
                FileWriter csvWriter = new FileWriter(file);
                writeCSVContents(csvWriter, attendanceList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void writeCSVContents(FileWriter csvWriter, List<AttendanceListRowData> attendanceList) {
        try {

            //header of the csv
            csvWriter.write("Name,Attendance,Reason\n");

            //attendance details in the document
            for (AttendanceListRowData row: attendanceList) {
                String name = row.getStudentName();
                String attendance = "Attend";
                if (row.getAttendance()){
                    attendance = "Attend";
                }else {
                    attendance = "No Attend";
                }
                String reason = row.getReason();

                csvWriter.write(name + "," + attendance + "," + reason +"\n");
            }

            csvWriter.close();

            Log.d("CSVGenerator", "CSV generated at downloads folder" );

        } catch (IOException e) {
        throw new RuntimeException(e);
        }
    }
}
