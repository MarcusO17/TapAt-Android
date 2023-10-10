package com.example.tapat.helpers;

import android.os.Environment;
import android.util.Log;

import com.example.tapat.model.AttendanceListRowData;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator {

    public static void generatePDF(List<AttendanceListRowData> attendanceList, String courseCode, String courseName,String classID) throws DocumentException {
        Document attendanceListDocument = new Document();

        String documentFileDir = Environment.getExternalStorageDirectory().getPath();
        String filename = "attendanceList.pdf";
        String documentFilePath = documentFileDir + "/" + filename;


        try {
            PdfWriter.getInstance(attendanceListDocument,new FileOutputStream(documentFilePath));

            attendanceListDocument.open();

            //header part of the document
            Paragraph title = new Paragraph("Attendance List - " + courseCode + " : " + courseName);

            attendanceListDocument.add(title);

            PdfPTable table = new PdfPTable(3);

            LocalDateTime currentDateTime = LocalDateTime.now();
            String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            PdfPCell classDetailsCell = new PdfPCell(new Paragraph("classID: " + classID));
            PdfPCell dateCell = new PdfPCell(new Paragraph("Document Generated: " + formattedDateTime));

            dateCell.setColspan(2);

            table.addCell(classDetailsCell);
            table.addCell(dateCell);

            //attendance details in the document
            for (AttendanceListRowData row: attendanceList) {

                PdfPCell studentNameCell = new PdfPCell(new Paragraph(row.getStudentName()));
                table.addCell(studentNameCell);
                if(row.isAttendance()) {
                    PdfPCell attendanceCell = new PdfPCell(new Paragraph("Attend"));
                    table.addCell(attendanceCell);
                }
                else {
                    PdfPCell attendanceCell = new PdfPCell(new Paragraph("No Attend"));
                    table.addCell(attendanceCell);
                }
                PdfPCell reasonCell = new PdfPCell(new Paragraph(row.getReason()));
                table.addCell(reasonCell);
            }

            attendanceListDocument.close();

            Log.d("PDFGenerator", "PDF generated at "+ documentFilePath);

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
