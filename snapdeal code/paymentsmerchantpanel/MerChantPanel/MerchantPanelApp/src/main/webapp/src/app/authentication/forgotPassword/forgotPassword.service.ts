import {IHttpService} from "../../shared/services/http.interface";
export interface IForgotPasswordService {
    forgotPassword:(requestObject:any)=> ng.IPromise<any>
    resendOTP:(requestObject:any)=> ng.IPromise<any>
    verifyOTP:(requestObject:any)=> ng.IPromise<any>
}


export class ForgotPasswordService implements IForgotPasswordService {
    private HttpService:IHttpService;
    private AuthURL:any;

  /* @ngInject */
  constructor(HttpService:IHttpService, Auth_URL:any) {
        this.HttpService = HttpService;
        this.AuthURL = Auth_URL;
    }

    public forgotPassword(requestObject:any) {
        return this.HttpService.request(this.AuthURL.FORGOT_PASSWORD.METHOD, this.AuthURL.FORGOT_PASSWORD.URL, requestObject);
    }

    public resendOTP(requestObject:any) {
        return this.HttpService.request(this.AuthURL.RESEND_OTP.METHOD, this.AuthURL.RESEND_OTP.URL, requestObject);
    }

    public verifyOTP(requestObject:any) {
        return this.HttpService.request(this.AuthURL.VERIFY_OTP.METHOD, this.AuthURL.VERIFY_OTP.URL, requestObject);
    }
}


