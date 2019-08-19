
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.AuditSRO;

public class SendAuditMailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -157407208278479458L;
	@Tag(3)
    private AuditSRO audit;
    @Tag(4)
    private String useremailId;

    public SendAuditMailRequest() {
    }

    public SendAuditMailRequest(AuditSRO audit, String useremailId) {
        super();
        this.audit = audit;
        this.useremailId = useremailId;
    }

    public AuditSRO getAudit() {
        return audit;
    }

    public void setAudit(AuditSRO audit) {
        this.audit = audit;
    }

    public String getUseremailId() {
        return useremailId;
    }

    public void setUseremailId(String useremailId) {
        this.useremailId = useremailId;
    }

}
