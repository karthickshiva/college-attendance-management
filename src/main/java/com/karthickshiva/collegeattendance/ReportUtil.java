package com.karthickshiva.collegeattendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;

public class ReportUtil {

	public static InputStreamResource downloadReportAsCSV(List<AttendanceEntry> attendanceEntries, List<Student> students) throws IOException {
		Map<Integer, List<AttendanceEntry>> map = new TreeMap<>();
		Date minDate = new Date(Long.MAX_VALUE);
		Date maxDate = new Date(0L);
		
		
		for(Student student : students)
		{
			if(!map.containsKey(student.getId()))
			{
				map.put(student.getId(), new ArrayList<>());
			}
		}
		
		for(AttendanceEntry attendanceEntry : attendanceEntries)
		{
			map.get(attendanceEntry.getStudent().getId()).add(attendanceEntry);
			if(attendanceEntry.getDate().compareTo(minDate) < 1)
			{
				minDate = attendanceEntry.getDate();
			}
			if(attendanceEntry.getDate().compareTo(maxDate) > 1)
			{
				maxDate = attendanceEntry.getDate();
			}
		}
		
		File excelFile = File.createTempFile("attendance_report", ".xlsx");
		OutputStream excelFIS = new FileOutputStream(excelFile);
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();
		
		XSSFRow row; int rowNum = 0;
		
		for(Student student : students)
		{
			row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(student.getName());
			
		}
		
		workBook.write(excelFIS);
		workBook.close();
		excelFIS.close();
		
		return new InputStreamResource(new FileInputStream(excelFile));
	}

}
