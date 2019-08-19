
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.BulkUploadResultSRO;

public class SendBulkUploadResultEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1518978721693197406L;
	@Tag(3)
    private List<BulkUploadResultSRO> resultDTOs;
    @Tag(4)
    private String fileName;
    @Tag(5)
    private String email;

    public SendBulkUploadResultEmailRequest(List<BulkUploadResultSRO> resultDTOs, String fileName,String email) {
        super();
        this.resultDTOs = resultDTOs;
        this.fileName = fileName;
        this.email = email;
    }

    public SendBulkUploadResultEmailRequest() {
    }

    public List<BulkUploadResultSRO> getResultDTOs() {
        return resultDTOs;
    }

    public void setResultDTOs(List<BulkUploadResultSRO> resultDTOs) {
        this.resultDTOs = resultDTOs;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
