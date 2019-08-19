/**
 * Created by user on 29/10/15.
 */

export default class  AuthConstants {
  static get Auth_URL():any {
    return {
      LOGIN: {
        METHOD: 'POST',
        URL: '/session/v1/login'
      },
      LOGOUT: {
        METHOD: 'GET',
        URL: '/access/v1/logout'
      },
      FORGOT_PASSWORD: {
        METHOD: 'POST',
        URL: '/session/v2/genotp'
      },
      VERIFY_OTP: {
        METHOD: 'POST',
        URL: '/session/v2/verifyotp'
      },
      RESEND_OTP: {
        METHOD: 'POST',
        URL: '/session/v2/resendotp'
      },
      SET_PASSWORD: {
        METHOD: 'POST',
        URL: '/session/v1/setpassword'
      },
      CREATE_MERCHANT: {
        METHOD: 'POST',
        URL: '/merchant/v1/create/merchant'
      },
      OFFLINE_MERCHANT: {
        METHOD: 'POST',
        URL: '/merchant/v1/create/offlinemerchant'
      }
    }
  }
}



