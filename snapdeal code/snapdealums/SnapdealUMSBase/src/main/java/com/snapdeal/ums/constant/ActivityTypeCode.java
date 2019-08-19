/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 11-Jan-2013
 *  @author ankur
 */
package com.snapdeal.ums.constant;

public enum ActivityTypeCode {
    signup("signup"), subscription("subs"), referral("referconv"), visa("visacard"), giftvoucher("gvoucher"), bdayCashBack("bdayCB"), referralReward("refReward"), generalSDCashService(
            "genSDCServ"), orderRefund("odrRefund"), cashBack("cashBack"), sdCashOnTransaction("sdCashOnTx"), sdCashOnFirstPurchase("sdCashFtPc"), sdCashOnSecondPurchase(
            "sdCashSdPc"), fraudDetection("fraudDebit"), orderTimeOut("orderTMT"), compensation("comp"), orderPurchase("dop"), refundAsSDCash("rfSDCash"), migration("mig"), sdCashOnPurchase(
            "sdCashOnPc");

    private String code;

    private ActivityTypeCode(String code) {
        this.code = code;
    }

    public String code() {
        return this.code;
    }

}
