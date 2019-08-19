export class MOB_URL{
  static get getMobUrl() {
    return {
      GET_BSNSSDATA: {
        METHOD: 'GET',
        URL: '/merchant/v1/get/uidata'
      },
      MOB_SIGNUP: {
        METHOD: 'POST',
        URL: '/merchant/v1/update/merchant'
      },
      GET_KYC_DOCS: {
        METHOD: 'GET',
        URL: '/merchant/v1/get/kyc/documentlist'
      },
      UPLOAD_KYC_DOC: {
        METHOD: 'POST',
        URL: '/merchant/v1/uploaddocument'
      },
      GET_UPLOADED_KYC_DOCS:{
        METHOD:'GET',
        URL:'/merchant/v1/get/profile'
      }
    }
  }
}




