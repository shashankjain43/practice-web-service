//
//package com.snapdeal.ums.email.ext.email;
//
//import com.dyuproject.protostuff.Tag;
//import com.snapdeal.base.model.common.ServiceRequest;
//
//public class SendMovieVoucherEmailRequest
//    extends ServiceRequest
//{
//
//    /**
//	 * 
//	 */
//	private static final long serialVersionUID = 5643265322262623021L;
//	@Tag(3)
//    private String email;
//    @Tag(4)
//    private PartnerPromoCodeSRO promoCode;
//    @Tag(5)
//    private String contextPath;
//    @Tag(6)
//    private String contentPath;
//
//    public SendMovieVoucherEmailRequest() {
//    }
//
//    public SendMovieVoucherEmailRequest(String email, PartnerPromoCodeSRO promoCode, String contextPath, String contentPath) {
//        super();
//        this.email = email;
//        this.promoCode = promoCode;
//        this.contextPath = contextPath;
//        this.contentPath = contentPath;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public PartnerPromoCodeSRO getPromoCode() {
//        return promoCode;
//    }
//
//    public void setPromoCode(PartnerPromoCodeSRO promoCode) {
//        this.promoCode = promoCode;
//    }
//
//    public String getContextPath() {
//        return contextPath;
//    }
//
//    public void setContextPath(String contextPath) {
//        this.contextPath = contextPath;
//    }
//
//    public String getContentPath() {
//        return contentPath;
//    }
//
//    public void setContentPath(String contentPath) {
//        this.contentPath = contentPath;
//    }
//
//}
