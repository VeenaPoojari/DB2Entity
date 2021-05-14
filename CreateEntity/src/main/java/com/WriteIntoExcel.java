package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import io.restassured.response.Response;


public class WriteIntoExcel {

	public static void createEntity(final String init) throws IOException {
		HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream("dummy.xls"));
		HSSFSheet myExcelSheet = myExcelBook.getSheet("db");
		Map<String, String> dataType = new HashMap<String, String>();
		int rowcount = 0;
		dataType.put("VARCHAR2", "String");
		dataType.put("NUMBER", "Integer");
		dataType.put("RAW", "UUID");
		dataType.put("TIMESTAMP", "LocalDateTime");
		for (int i = 0; i <= myExcelSheet.getLastRowNum(); i++) {
			HSSFRow row = myExcelSheet.getRow(i);
			String c = row.getCell(0).getStringCellValue();
			// System.out.println("excel: "+c);
			// System.out.println("convert: "+camelCase(c));
			System.out.println("\n@Column(name=\"" + c + "\")");
			String s = row.getCell(1).getStringCellValue().replaceAll("\\(.*?\\)", "");
			System.out.println("private " + dataType.get(s) + " " + camelCase(c) + ";");
			rowcount++;
		}
		System.out.println("\nrow count: " + rowcount);
	}

	public static String camelCase(final String init) {
		if (init == null)
			return "null";

		final StringBuilder ret = new StringBuilder(init.length());
		for (int i = 0; i < init.length(); i++) {
			Character c = init.charAt(i);
			if (c.equals('_')) {
				c = init.charAt(++i);
				// System.out.println("c: "+c+", char: "+c.toUpperCase(c));
				ret.append(c.toUpperCase(c));
			} else {
				// System.out.println("c: "+c+",char: "+c.toLowerCase(c));
				ret.append(c.toLowerCase(c));
			}
		}

		return ret.toString();
	}

	public static void main(String[] args) {
		/*
		 * Double dblValue = Double.parseDouble("1.99E+07"); String str =
		 * String.format("%.2f", dblValue); System.out.println(str);
		 */
		try {
			WriteIntoExcel.createEntity("COL NAME");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
