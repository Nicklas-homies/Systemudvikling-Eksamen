package com.example.demo.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.models.Member;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MemberExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Member> listMember;

    public MemberExcelExporter(List<Member> listMember) {
        this.listMember = listMember;
        workbook = new XSSFWorkbook();
    }


    private void writeHeaderLine() {
        sheet = workbook.createSheet("Medlemmer");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Medlems ID", style);
        createCell(row, 1, "Fornavn", style);
        createCell(row, 2, "Efternavn", style);
        createCell(row, 3, "Køn", style);
        createCell(row, 4, "Email", style);
        createCell(row, 5, "Email 2", style);
        createCell(row, 6, "Hold", style);
        createCell(row, 7, "Deltager i point stævner", style);
        createCell(row, 8, "Start dato", style);
        createCell(row, 9, "Fødseldsdato", style);
        createCell(row, 10, "Telefonnummer 1", style);
        createCell(row, 11, "Telefonnummer 2", style);
        createCell(row, 12, "Telefonnummer 3", style);
        createCell(row, 13, "Stop dato", style);
        createCell(row, 14, "Stoppet", style);


    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Member member : listMember) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, member.getId(), style);
            createCell(row, columnCount++, member.getFirstName(), style);
            createCell(row, columnCount++, member.getLastName(), style);
            if (member.getIsFemale() == 1) {
                createCell(row, columnCount++, "Kvinde", style);
            }else{
                createCell(row, columnCount++, "Mand", style);
            }
            createCell(row, columnCount++, member.getMail(), style);
            createCell(row, columnCount++, member.getMail2(), style);
            createCell(row, columnCount++, member.getHold(), style);
            if (member.isPointStavne()) {
                createCell(row, columnCount++, "Ja", style);
            }else {
                createCell(row, columnCount++, "Nej", style);
            }
            createCell(row, columnCount++, dateFormatter.format(member.getStartDate()), style);
            createCell(row, columnCount++, dateFormatter.format(member.getBirthday()), style);
            createCell(row, columnCount++, member.getPhoneNumber(), style);
            createCell(row, columnCount++, member.getPhoneNumber2(), style);
            createCell(row, columnCount++, member.getPhoneNumber3(), style);
            if (member.getStopDate() == null){
                createCell(row, columnCount++, "Ikke stoppet", style);
            }else {
                createCell(row, columnCount++, dateFormatter.format(member.getStopDate()), style);
            }
            if (member.getIsDeleted() == 1){
                createCell(row, columnCount++, "Ja", style);
            }else {
                createCell(row, columnCount++, "Nej", style);
            }



        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}