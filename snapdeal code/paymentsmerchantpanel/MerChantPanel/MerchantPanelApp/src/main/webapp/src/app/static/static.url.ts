export class STATIC_URL{
    static get getStaticUrl() {
      return {
        sendContactMail:{
            method:'POST',
            url : '/user/v2/merchantuser/contactus'
        }, 
        sendContactMailNonLogged:{
            method:'POST',
            url : '/user/v2/generaluser/contactus'
        }
      }
    }
  }




