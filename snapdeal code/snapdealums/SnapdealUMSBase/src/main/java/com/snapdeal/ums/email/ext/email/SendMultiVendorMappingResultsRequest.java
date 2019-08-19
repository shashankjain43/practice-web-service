
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.ProductMultiVendorMappingResultSRO;


public class SendMultiVendorMappingResultsRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7064167669405797101L;
	@Tag(3)
    private List<ProductMultiVendorMappingResultSRO> resultSROs;
    @Tag(4)
    private String fileName;

    public SendMultiVendorMappingResultsRequest() {
    }

    public SendMultiVendorMappingResultsRequest(List<ProductMultiVendorMappingResultSRO> resultSROs, String fileName) {
        super();
        this.resultSROs = resultSROs;
        this.fileName = fileName;
    }

    public List<ProductMultiVendorMappingResultSRO> getResultSROs() {
        return resultSROs;
    }

    public void setResultSROs(List<ProductMultiVendorMappingResultSRO> resultSROs) {
        this.resultSROs = resultSROs;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
