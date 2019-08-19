package com.snapdeal.opspanel.promotion.service.impl;

import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.promotion.request.BulkTaskRequest;
import com.snapdeal.opspanel.promotion.service.OutputExcelService;

@Service("outputExcelService")
public class OutputExcelServiceImpl implements OutputExcelService {

	@Override
	public void writeToOutputFile(String fileName, BulkTaskRequest request) {
/*		try {
		
			Workbook workBook = new HSSFWorkbook();
			Sheet sheet = workBook.createSheet("result");

			for (FileRow row : request.getSuccessfulQueue()) {
				handleRow( workBook, sheet, row);
			}
			for (FileRow row : request.getUnsuccessfulQueue()) {
				handleRow( workBook, sheet, row);
			}
		
			String newFilePath = fileName.substring(0,
					fileName.lastIndexOf("."))
					+ new Date().toLocaleString()
					+ "_Response"
					+ fileName.substring(fileName.lastIndexOf("."));
			File outputFile = new File( newFilePath);
			FileOutputStream out = new FileOutputStream(outputFile);	
			workBook.write(out);
			out.close();
			System.out.println("Excel written successfully");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void handleRow(Workbook workBook,
			Sheet sheet, BulkRow row) {

		Row newRow = sheet.createRow((int) row.getRowNum());

		Map<String, Object[]> data = new HashMap<String, Object[]>();

		Object[] objArr = new Object[] { row.getId(), row.getUploadTime(),
				row.getTransactionTime(), row.getAmount(),
				row.getEventContex(), row.getResponse(), row.getFinalStatus() };

		int cellnum = 0;
		for (Object obj : objArr) {
			Cell cell = newRow.createCell(cellnum++);
			if (obj instanceof Date)
				cell.setCellValue((Date) obj);
			else if (obj instanceof Boolean)
				cell.setCellValue((Boolean) obj);
			else if (obj instanceof String)
				cell.setCellValue((String) obj);
			else if (obj instanceof Double)
				cell.setCellValue((Double) obj);
		}

	}*/
}}
