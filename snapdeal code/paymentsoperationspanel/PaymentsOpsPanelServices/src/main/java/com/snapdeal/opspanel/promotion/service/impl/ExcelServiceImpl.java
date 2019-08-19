package com.snapdeal.opspanel.promotion.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.request.FormData;
import com.snapdeal.opspanel.promotion.service.ExcelService;
import com.snapdeal.opspanel.promotion.utils.AmazonS3Utils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	SDMoneyClient sdMoneyClient;

    @Autowired
    HttpServletRequest servletRequest;

    @Autowired
    AmazonS3Utils amazonUtils;

	@Autowired
	private TokenService tokenService;
	
	public void processFile(String pathToFile, FormData request) throws WalletServiceException, OpsPanelException{

		/*		try {
			FileInputStream file = null;
			File upLoadedFile = new File(pathToFile);
			file = new FileInputStream(upLoadedFile);
         	String fileName = upLoadedFile.getName();

			String token = servletRequest.getHeader("token");
			String emailId = null;
			
			try {
				emailId = tokenService.getEmailFromToken(token);
			} catch (OpsPanelException e) {
				
				file.close();
				throw e;
			}
			        
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			BulkTaskRequest bulkRequest = buildBulkRequest(request,
					upLoadedFile);
			bulkRequest.setUnsuccessfulQueue(new LinkedList<BulkRow>());
			bulkRequest.setSuccessfulQueue(new LinkedList<BulkRow>());
			bulkRequest.setBusinessEntity(request.getBusinessEntity());
			bulkRequest.setCorpId(request.getCorpId());
			bulkRequest.setFileName(upLoadedFile.getName());
			bulkRequest.setActivity(request.getActivity());
			bulkRequest.setInstrument(request.getInstrument());
			bulkRequest.setId_type(request.getId_type());
			bulkRequest.setIsPark(request.getIsPark());
			bulkRequest.setTotal_success_amount(new BigDecimal("0.00"));
			bulkRequest.setNo_of_success_rows(0l);
			bulkRequest.setNo_of_success_notifications(0l);
			Long rowNum = 0L;
			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();
				BulkRow taskRow = new BulkRow();

				rowNum++;
				if (rowNum == 1) {
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				int cellNum = 0;
				CreditVoucherBalanceRequest cvbRequest = null;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					cellNum++;
					switch (cellNum) {
					case 1:
						taskRow.setId(OPSUtils.getCellValueAsString(cell));
						break;
					case 2:
						taskRow.setAmount(new BigDecimal(OPSUtils
								.getCellValueAsString(cell)));
						break;
					case 3:
						taskRow.setEventContex(OPSUtils
								.getCellValueAsString(cell));
						break;

					}
				}
				taskRow.setUploadTime(new Timestamp(new Date().getTime()));
				bulkRequest.getUnsuccessfulQueue().add(taskRow);
			}
			bulkRequest.setTotal_no_of_rows(rowNum);
			file.close();
         submitTask(bulkRequest);

         amazonUtils.pushFile(pathToFile,
                  emailId.substring(0, emailId.indexOf("@")) + "/" + fileName);

			FileOutputStream out = null;

			Date date = new Date();
			String newFilePath = pathToFile.substring(0,
					pathToFile.lastIndexOf("."))
					+ date.toLocaleString()
					+ "_Request"
					+ pathToFile.substring(pathToFile.lastIndexOf("."));
			out = new FileOutputStream(new File(newFilePath));

			workbook.write(out);

			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private BulkTaskRequest buildBulkRequest(FormRequest request,
			File upLoadedFile) {
		BulkTaskRequest bulkRequest = new BulkTaskRequest();
		bulkRequest.setUnsuccessfulList(new PriorityQueue<BulkRow>());
		bulkRequest.setSuccessfulList(SuccessfulList);
		bulkRequest.setApiName(getApiName(request));
		bulkRequest.setUserEmail(request.getEmailId());
		bulkRequest.setFileName(upLoadedFile.getName());
		return bulkRequest;
	}

	private String getApiName(FormRequest request) {
		if (request.getInstrument().equals("GV")) {
			return "creditVoucherBalance";
		} else
			return "creditGeneralBalance";
	}

	private void submitTask(BulkTaskRequest request) {

		Calendar cal = Calendar.getInstance();
		TaskDTO task = new TaskDTO();
		task.setTaskType("BULK_CALL");
		task.setRequest(request);
		task.setCurrentScheduleTime(cal.getTime());
		taskScheduler.submitTask(task);
	}*/
}
}