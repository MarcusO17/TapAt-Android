package com.example.tapat.helpers;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.tapat.model.AttendanceListRowData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVGenerator {

    public CSVGenerator() {
    }

    public static String getCSVPath(String courseName, String classID) {
        String documentFileDir = Environment.getExternalStorageDirectory().getPath();
        String filename = classID + "-" + courseName + "-" + "attendanceList.csv";
        String documentFilePath = documentFileDir + "/" + filename;

        return documentFilePath;
    }
    public static void generateCSV(List<AttendanceListRowData> attendanceList, String courseName, String classID) {


        String documentFileDir = Environment.getExternalStorageDirectory().getPath();
        String filename = classID + "-" + courseName + "-" + "attendanceList.csv";
        String documentFilePath = documentFileDir + "/" + filename;

        try {
            //creating file at destination
            FileWriter csvWriter = new FileWriter(documentFilePath);

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

            Log.d("CSVGenerator", "CSV generated at "+ documentFilePath);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
