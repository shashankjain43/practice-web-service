package com.snapdeal.opspanel.promotion.rp.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;
import com.snapdeal.opspanel.Amazons3.request.DownloadRefundFileRequest;
import com.snapdeal.opspanel.Amazons3.request.UploadServiceRequest;
import com.snapdeal.opspanel.Amazons3.response.FileListingResponse;
import com.snapdeal.opspanel.Amazons3.service.BulkRefundService;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.bulk.exception.BulkComponentException;
import com.snapdeal.opspanel.bulk.request.BulkRefundTaskRequest;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.ts.TaskScheduler;
import com.snapdeal.payments.ts.dto.TaskDTO;

import lombok.extern.slf4j.Slf4j;
import scala.collection.mutable.HashSet;

@RestController
@RequestMapping("/bulkrefund/")
@Slf4j
public class BulkController {

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	TaskScheduler taskScheduler;

	@Autowired
	BulkRefundService bulkRefundService;

	@Value("${file.size}")
	private Integer filesize;

	@Value("${bulk.localDir}")
	private String localDir;

	@Autowired
	private TokenService tokenService;
	
	/*
	 * private static CellProcessor[] getProcessors() {
	 * 
	 * final CellProcessor[] processors = new CellProcessor[] {
	 * 
	 * new NotNull(), // txnId new NotNull(), new NotNull(), // comments };
	 * 
	 * return processors; }
	 */

	@Audited(context = "Bulk", searchId = "merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_BULK_UPDATER'))")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public GenericResponse upload(@RequestParam("file") MultipartFile file,
			@RequestParam("merchantId") String merchantId, @RequestParam("merchantName") String merchantName,
			@RequestParam("refundKey") String refundKey) throws Exception {
		String msg = null;
		String fileName = null;
		boolean isInputFileDeleted = false;

		if (file != null) {
			fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
			fileName = fileName.substring(0, fileName.indexOf(".csv")) + "_" + new Date().getTime() + "_.csv";
			log.info("\n\n\n\n NEW BULK REFUND FILE RECIEVED: " + fileName);
		}

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		/* emailId = "avinash.katiyar@snapdeal.com"; */
		if (emailId == null) {
			throw new BulkComponentException("MT-5005",
					"You are logged out or your access has timed out to bulk system, Kindly relogin !");
		}

		if (file != null) {

			String ext = FilenameUtils.getExtension(fileName);

			if (!ext.equals("csv")) {
				throw new BulkComponentException("MT-5037", "Sorry The file is not an csv file");

			}

			if (file.getSize() > filesize) {
				throw new BulkComponentException("MT-5038",
						"Sorry The file size is greater than limit " + ((double) filesize) / 1000000 + " MB");
			}

			byte[] bytes = file.getBytes();
			BufferedOutputStream buffStream = new BufferedOutputStream(
					new FileOutputStream(new File(localDir + fileName)));
			buffStream.write(bytes);
			buffStream.close();
			log.info("BULK REFUND CHECK: File Uploaded to the local server.");

			// ---------------------------------------------------------------
			InputStream inputStream = new FileInputStream(localDir + fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			Long numOfRows = new Long("0");
			BufferedReader br = new BufferedReader(inputStreamReader);
			String line;
			// br.readLine();
			HashSet<String> hSet = new HashSet<String>();
			while ((line = br.readLine()) != null) {
				numOfRows++;
				String[] tokens = line.split(",", -1);
				try {
					if (tokens.length == 1) {

						throw new BulkComponentException("MT-1110", " Row Num" + numOfRows
								+ " File can not have a blank row or a row not containing column separators \n ");

					} else if (tokens.length == 2||tokens.length == 3||tokens.length == 4) {

						throw new BulkComponentException("MT-1112",
								" Invalid CSV format. Inappropriate number of commas on line number " + numOfRows
										+ ". Please make sure to fill atleast 3 fields in uploaded file. \n");
					} else if (( tokens.length != 5) || tokens.length > 5) {

						throw new BulkComponentException("MT-1111",
								" Invalid CSV format. Please delete values from column no. " + tokens.length
										+ ". Make sure, field does not contain comma in content. Also make sure each row has only three comma\n");
					}
					for (int j=0;j<tokens.length-2;j++) {
						if (tokens[j] == null || tokens[j].trim().equals("")) {

							throw new BulkComponentException("MT-1112",
									" Invalid CSV format. Blank field found in first 3 columns at Row no :" + numOfRows
											+ ". Make sure, file does not contain comma in any field.\n");
						}
					}
					if (numOfRows > 1) {

						if (!isNumeric(tokens[1])) {
							throw new BulkComponentException("MT-9110", "Invalid content in file at line number : "
									+ numOfRows + " , Amount should be numeric ");
						}

					}
				} catch (BulkComponentException bce) {
					new File(localDir + fileName).delete();
					log.info("BULK REFUND CHECK: File On local server deleted Because of failed validations \n");
					log.info("Bulk Component Exception while parsing CSV with filename :" + fileName
							+ ExceptionUtils.getFullStackTrace(bce));
					throw bce;
				}

			}

			/*
			 * ICsvBeanReader beanReader = null; try { beanReader = new
			 * CsvBeanReader(new FileReader(localDir + fileName),
			 * CsvPreference.STANDARD_PREFERENCE);
			 * 
			 * // the header elements are used to map the values to the bean //
			 * (names must match) final String[] header =
			 * beanReader.getHeader(true); final CellProcessor[] processors =
			 * getProcessors(); HashSet txnSet = new HashSet<>();
			 * BulkRefundInputCSVModel refundRow; while ((refundRow =
			 * beanReader.read(BulkRefundInputCSVModel.class, header,
			 * processors)) != null) { if
			 * (!StringUtils.isNumericSpace(refundRow.getAmount())) { throw new
			 * BulkComponentException("MT-9110",
			 * "Invalid content in file at line number : " +
			 * beanReader.getLineNumber() + " , Amount should be numeric "); }
			 * else if (refundRow.getId() == null ||
			 * refundRow.getId().trim().equals("") || refundRow.getComments() ==
			 * null || refundRow.getComments().trim().equals("")) { throw new
			 * BulkComponentException("MT-9110",
			 * "Invalid content in file at line number : " +
			 * beanReader.getLineNumber() +
			 * " , Txn id or comments can not be empty "); } if
			 * (txnSet.contains(refundRow.getId())) { throw new
			 * BulkComponentException("MT-9110",
			 * "Invalid content in file at line number : " +
			 * beanReader.getLineNumber() +
			 * " , duplicate transaction id in file  "); } else {
			 * txnSet.add(refundRow.getId()); } }
			 * 
			 * log.info(
			 * "BULK REFUND CHECK: File successfully validated(No blank spaces in the file"
			 * );
			 * 
			 * } catch (BulkComponentException bce) { log.info(
			 * "Bulk Component Exception while parsing CSV with filename :" +
			 * fileName + ExceptionUtils.getFullStackTrace( bce ) );
			 * isInputFileDeleted = true; throw bce; } catch
			 * (SuperCsvConstraintViolationException e) { log.info(
			 * "Exception while parsing CSV with filename :" + fileName +
			 * e.getMessage() + ExceptionUtils.getFullStackTrace( e ) );
			 * isInputFileDeleted = true; throw new
			 * BulkComponentException("MT-1110",
			 * "Null Value encountered at row no.:" +
			 * e.getCsvContext().getLineNumber()+ " and column no.:" +
			 * e.getCsvContext().getColumnNumber() +
			 * "Please correct your uploaded sheet."); } catch
			 * (SuperCsvException e) { log.info(
			 * "Exception while parsing CSV with filename :" + fileName +
			 * e.getMessage()+ ExceptionUtils.getFullStackTrace( e ) );
			 * isInputFileDeleted = true; throw new
			 * BulkComponentException("MT-1111",
			 * "Please make sure to delete all the extra columns and content after first three Columns \n"
			 * ); } catch(Exception e){ log.info(
			 * "Exception While Parsing CSV with filename :" + fileName +
			 * e.getMessage() + ExceptionUtils.getFullStackTrace(e));
			 * isInputFileDeleted = true; throw new
			 * BulkComponentException("MT-1113", errMsg) } finally { if
			 * (beanReader != null) { beanReader.close(); }
			 * if(isInputFileDeleted){ new File(localDir + fileName).delete();
			 * log.info(
			 * "BULK REFUND CHECK: File On local server deleted Because of failed validations "
			 * ); } }
			 */
			// log.info("BULK REFUND CHECK: File is successfully saved locally
			// at the server");

			// -------------------------------------------------------------------

			UploadServiceRequest uploadRequest = new UploadServiceRequest();
			uploadRequest.setEmail(emailId);
			uploadRequest.setRefundKey(refundKey);
			uploadRequest.setFileName(fileName);
			uploadRequest.setFileSource(localDir + fileName);
			uploadRequest.setMerchantName(merchantName);
			uploadRequest.setMerchantId(merchantId);
			uploadRequest.setUploadTime(new Date());
			uploadRequest.setInputFile(true);

			String s3uploadedPath = bulkRefundService.uploadRefundFile(uploadRequest);

			log.info("BULK REFUND CHECK: File Uploaded to the amazon s3 client.");

			BulkRefundTaskRequest taskRequest = new BulkRefundTaskRequest();
			taskRequest.setEmailId(emailId);
			taskRequest.setS3Path(s3uploadedPath);
			taskRequest.setFileName(fileName);
			taskRequest.setMerchantId(merchantId);
			taskRequest.setMerchantName(merchantName);
			taskRequest.setRefundKey(refundKey);
			TaskDTO taskDTO = new TaskDTO();
			taskDTO.setRequest(taskRequest);

			log.info("BULK REFUND CHECK: Task Request made  for the file ");

			taskDTO.setTaskType("BULK_REFUND");
			taskDTO.setCurrentScheduleTime(new Date());
			taskScheduler.submitTask(taskDTO);
			msg = "You have successfully uploaded " + fileName + "\n";
			log.info("BULK REFUND CHECK: task submitted to the scheduler ");

		}
		return getGenericResponse(msg);
	}

	public boolean isNumeric(String str) {
		try {
			BigDecimal amount = new BigDecimal(str);
			return true;
		} catch(NumberFormatException nfe){
			return false;
		} catch(Exception exc) {
			return false;
		}
	}

	@Audited(context = "Bulk", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_BULK_UPDATER'))")
	@RequestMapping(value = "/filelisting", method = RequestMethod.GET)
	public @ResponseBody GenericResponse testFilelisting() throws PaymentsCommonException, WalletServiceException, OpsPanelException {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		List<FileListingResponse> list = bulkRefundService.getFileListings(emailId);
		GenericResponse response = new GenericResponse();
		response.setData(list);
		return response;
	}

	@Audited(context = "Bulk", searchId = "request.fileName", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_BULK_UPDATER'))")
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public @ResponseBody GenericResponse testDownload(@RequestBody DownloadRefundFileRequest request)
			throws PaymentsCommonException {

		URL url = bulkRefundService.downloadRefundFile(request);
		GenericResponse response = new GenericResponse();
		response.setData(url);
		return response;
	}

	@Audited(context = "Bulk", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_BULK_UPDATER') and hasPermission('OPS_MERCHANTOPS_SUPERUSER'))")
	@RequestMapping(value = "/allfilelisting", method = RequestMethod.GET)
	public @ResponseBody GenericResponse testAllFilelisting() throws PaymentsCommonException {

		List<FileListingResponse> list = bulkRefundService.getAllFileListings();
		GenericResponse response = new GenericResponse();
		response.setData(list);
		return response;
	}

	@Audited(context = "Bulk", searchId = "key", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_BULK_UPDATER'))")
	@RequestMapping(value = "/VerifyRefKey", method = RequestMethod.GET)
	public @ResponseBody GenericResponse refundCheck(@RequestParam String key) throws PaymentsCommonException {

		GenericResponse response = new GenericResponse();
		response.setData(bulkRefundService.checkRefundKey(key));

		return response;
	}

	private GenericResponse getGenericResponse(String response) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(response);
		return genericResponse;
	}
}