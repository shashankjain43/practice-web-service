/**
 * Created by chitra.parihar on 17-03-2016.
 */
export class APP_MESSAGE_CONSTANT {

  static get getMessageConstants() {
    return {
      FILE_SIZE: 'File size should not be greater than 150KB',
      CHOOSE_CSV_FILE: 'Please choose  excel file only',
      NO_DOWNLOAD_HISTORY: 'No files are available for download',
      EMPTY_INTEGRATION_KEY: 'No data is available for ',
      NO_MATCH_PASSWORD: 'Password should match',
      INCORRECT_PASSWORD: 'Incorrect old password',
      REQUIRED_FIELD: 'Please enter ',
      SUBMIT_SUCCESS: 'Amount refunded successfully',
      OH_ISSUE: 'Oh oh! There is some issue',
      CORRECT_FIELD: 'Please enter correct',
      AMOUNT_GREATER: 'Amount should not be greater than ',
      ADD_SUCCESSFULLY: 'User added successfully',
      EDIT_SUCCESSFULLY: 'User updated successfully',
      PASSWSORD_RANGE: 'Password should be between 8-16 characters',
      PASSWORD_CHANGED: 'Password changed successfully',
      PASSWORD_SET: 'Password set successfully',
      TRY_AGAIN: 'Please try after sometime',
      PERMISSION_REQUIRED: 'Please select atleast one permission',
      NO_FIELD: 'No transactions are available ',
      NO_INITIATE: 'No transactions are available for initiate refund',
      EMPTY_TRANSACTION: 'No transaction available',
      FORGOT_PWD_MSG: 'We just sent you reset instructions at your registered email Id',
      USER_EXIST: 'Login name already taken',
      SELECT_USER: 'Please select user',
      INVALID_DATE_RANGE: 'date should not be later than current date',
      INVALID_TO_DATE: 'To date should not be lower than From date',
      INVALID_FROM_DATE: 'From date should not be greater than To date',
      TIME_DIFFERENCE: 'Date range should be within one week',
      GENERATE_REPORT_FORBIDDEN: 'No data for generate report',
      NO_GENERATE_REPORT: 'No data to generate report',
      NO_ZERO_VALUE: 'Please enter value greater than zero',
      CONTACT_US_SUCCESS:'The request has been successfully submitted, We will get in touch with you shortly',
      CONTACT_US_FAIL:'We have encountered an error while submitting your request. Please Try again',
      INITIATE_GENERATE_REOPRT_SUCCESS_MESSAGE: 'Report can be downloaded from download history in some time'

    }
  }
}
