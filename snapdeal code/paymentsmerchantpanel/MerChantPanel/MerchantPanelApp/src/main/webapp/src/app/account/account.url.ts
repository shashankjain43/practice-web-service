export class APP_ACCOUNT_URL{
    static get getAccountURL() {
      return {
        VIEW_PROFILE:{
            METHOD:'GET',
            URL : '/merchant/v1/get/profile'
        },
        CHANGE_PASSSWORD:{
            METHOD:'POST',
            URL : '/user/v1/updatepwd'
        },
        GET_USERS:{
            METHOD:'GET',
            URL : '/user/v1/getall'
        },
        ADD_USERS:{
            METHOD:'POST',
            URL : '/user/v1/adduser'
        },
        EDIT_USERS:{
            METHOD:'POST',
            URL : '/user/v1/edituser'
        },
        GET_ROLES:{
            METHOD:'GET',
            URL : '/merchant/v1/get/roles'
        },
        VERIFY_USER:{
            METHOD:'GET',
            URL : '/user/v1/verify'
        }
      }
    }
  }
