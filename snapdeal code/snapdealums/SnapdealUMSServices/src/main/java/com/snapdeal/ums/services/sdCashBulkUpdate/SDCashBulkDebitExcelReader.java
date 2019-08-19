package com.snapdeal.ums.services.sdCashBulkUpdate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.admin.sdCashBulkDebit.SDCashBulkDebitUploadResponse;
import com.snapdeal.ums.core.dto.RawSDCashBulkCreditExcelRow;
import com.snapdeal.ums.core.dto.RawSDCashBulkDebitExcelRow;
import com.snapdeal.ums.utils.UMSStringUtils;

@Service
public class SDCashBulkDebitExcelReader {

	 // Class to read file contents in the form of bytes[] using apache poi and
    // converts into raw excel file.

    private static final Logger LOG = LoggerFactory
        .getLogger(SDCashBulkCreditExcelReader.class);

    /**
     * Returns a linked list of Raw Excel file row after processing the file
     * contents in bytes.
     * 
     * @param fileContent
     * @param sdCashUploadResponse
     * @return
     * @throws IOException
     */

    public LinkedList<RawSDCashBulkDebitExcelRow> extractRawSDCashDebitRow(
        byte[] fileContents,
        SDCashBulkDebitUploadResponse sdCashUploadResponse)
        throws IOException
    {

        // let's check if the file uploaded

        if (fileContents != null && fileContents.length > 0) {

            LOG.info("Reading file content into Input Stream and converting into raw Excel File...");
            LinkedList<RawSDCashBulkDebitExcelRow> recordList = new LinkedList<RawSDCashBulkDebitExcelRow>();
            InputStream excelFileInputStream = new ByteArrayInputStream(
                fileContents);

            // Get the workbook instance for excelFileInputSteam

            HSSFWorkbook workbook = new HSSFWorkbook(excelFileInputStream);

            // Get first sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {

                String email = null;
                String sdCash = null;
                String expiryDays = null;
                String orderId = null;
                boolean isDebited = false;

                Row row = rowIterator.next();
                int rowId = row.getRowNum();

                Cell cell1 = row.getCell(0);

                if (checkNotNullAndSetCellType(cell1)) {
                    email = cell1.getStringCellValue();
                }

                Cell cell2 = row.getCell(1);

                if (checkNotNullAndSetCellType(cell2)) {
                    sdCash = cell2.getStringCellValue();
                }
          
                Cell cell3 = row.getCell(2);

                if (checkNotNullAndSetCellType(cell3)) {
                    orderId = cell3.getStringCellValue().trim();
                }

                RawSDCashBulkDebitExcelRow rawRow = new RawSDCashBulkDebitExcelRow(
                    email, sdCash, orderId, isDebited, rowId);

                if (UMSStringUtils.isNotNullNotEmpty(email)) {
                    // Add only not null & non-empty email fields
                    recordList.add(rawRow);
                }
            }
            excelFileInputStream.close();
            LOG.info("Finished reading the uploaded file");
            return recordList;
        }
        else {
            LOG.error("Could not read the file contents.. Null or Empty file");
            return null;
        }

    }

    /**
     * Returns false if a cell is null or empty , if not- sets each cell type as
     * a String.
     * 
     * @param cell
     * @return
     */

    private boolean checkNotNullAndSetCellType(Cell cell)
    {

        boolean cellType = false;

        if (cell == null || (cell.getCellType() == Cell.CELL_TYPE_BLANK)) {

            return cellType;

        }
        else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellType = true;
            return cellType;
        }

    }
}
