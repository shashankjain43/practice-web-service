package com.snapdeal.ums.constants;

public enum ErrorConstants
{
    //GENERIC ERRORS - THE LAST DEFENCE AGAINST EXCEPTIONS!!
    UNEXPECTED_ERROR(-1, "Unexpected error encountered!"),
    BAD_REQUEST(-2, "Bad request! Missing/Invalid parameters"),
    //URI name is appended at run time
    SERVICE_DISABLED(-3, "Requested UMS service has been disabled. Get in touch with the UMS dev team to enable "),
    INVALID_REQUEST(-4, "Request is null or request parameter is null!"),
    
    
    INVALID_EMAIL_ID(510, "Invalid user/emailID!"),
    LOYALTY_VERIFICATION_CODE_MISMATCH(511, "Verification code mismatch!"),
    LOYALTY_USER_NOT_ELIGIBLE(512, "Either the user is not eligible for the loyalty program or is already active!"),
    LOYALTY_INVALID_REQUEST(513, " Invalid request!"),
    USER_DOES_NOT_EXIST(514, "User does not exist!"),
    REQUEST_MISMATCH_WITH_EMAIL(515, "Request parameters did not match with the email ID!"),

    //Loyalty upload errors
    EMAIL_IDS_ABSENT_IN_REQ(600, "User does not exist!"),
    FILE_NOT_PRESENT_OR_EMPTY(601, "Either the file was not recieved or was empty!"),
    FILE_FORMAT_UNSUPPORTED(602, "Uploaded file format is unsupported. Only a .csv file can be processed!"),
    FILE_EXCEEDS_SIZE_LIMIT(603, "Uploaded file exceeds the size limit!"),
    MAX_ELIGIBILITY_EMAIL_COUNT_PER_UPLOAD_EXCEEDED(604, "Maximum email count for eligibility has crossed the limit!"),

    
    //NEFT related errors
    REQUEST_MANDATORY_FIELDS_ARE_EMPTY_NULL(700, "Mandatory field(s) of the request are either empty or null!"),
    USER_NEFT_DETAILS_EXISTS(701, "User NEFT details in the request already exists and is active!"),
    NEFT_DETAILS_WITH_ID_IN_REQUEST_DOES_NOT_EXIST(702, "Received invalid NEFT details ID in the request!"),
    
    
  //SDCashCredit related errors
    SD_CASH_INVALID(800,"SDCash field is invalid."),
    INVALID_EXPIRY_DAYS(801,"Invalid expiry days."),
    SD_CASH_LIMIT_EXCEED(802,"SDCash exceeded the maximum limit."),
    NOT_AN_EXCEL_FILE(803,"Uploaded file format is unsupported.It is not a .xls file"),
    CREDIT_REASON_ABSENT(804,"Credit reason is missing."),
    MAX_ROW_COUNT_EXCEEDED(805,"Max row count has been exceeded!"),
    ZERO_ROWS_FOUND(806,"No rows found for SD cash credit!"),
    INVALID_CREDIT_AMOUNT(807,"The credit amount has to be greater than ZERO."),
    EXPIRY_DAYS_LESSER_THAN_ONE(808,"Expiry days cannot be less than ONE."),
    FILE_ALREADY_PRESENT(809, "File has already been processed"),
    USER_DOES_NOT_GET_CREATED(810, "Error occured at the time of User creation"),
    EMAIL_TEMPLATE_IS_NOT_AVAILABLE(811, "Email Template is not available!"),
    INSUFFICIENT_OR_ZERO_BALANCE(812,"Insufficient or zero balance"),
    
    //UserProfileDetails related errors
    USER_NOT_PRESENT(901,"User does not exist"),
    USER_ADDRESS_EXCEPTION(902,"unexpected error"),
    USER_ALREADY_EXISTS(903,"User already exists!"),
    
    //ServerBehaviourContext related errors
    SERVER_BEHAVIOUR_CONTEXT_EXISTS(1001,"Server context behaviour already exist"),
    DISABLED_URL_NOT_PRESENT(1002,"Invalid Request: No disabled url specified in request"),
    DISABLED_URL_PRESENT(1003,"The requested url has already been disabled"),
    INVALID_NAME(1004,"No server behaviour context name specified or name is null"),
    NO_DISABLED_URL_CURRENT_SERVER_BEHAVIOUR_CONTEXT(1005,"No disabled url is present for the current server behaviour context"),
    

    ;
    
    private int code;

    private String msg;

    private ErrorConstants(int code, String msg)
    {

        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {

        return this.code;
    }

    public String getMsg()
    {

        return this.msg;
    }
    
    @Override
    public String toString()
    {
    
        return this.code+": "+this.msg;
    }

}
