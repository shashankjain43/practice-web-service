package com.snapdeal.merchant.request;

import com.snapdeal.merchant.enums.FileType;
import com.snapdeal.merchant.enums.ReportType;

import lombok.Data;

@Data
public class AbstractExportRequest extends AbstractMerchantRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3024182638163970444L;

	private FileType fileType;
	
	private ReportType reportType;

}
