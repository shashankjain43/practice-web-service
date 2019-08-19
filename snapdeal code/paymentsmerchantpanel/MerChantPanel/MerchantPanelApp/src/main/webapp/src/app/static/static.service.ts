import {IHttpService} from "../shared/services/http.interface";
import {HttpService} from "../shared/services/http.service";
import {STATIC_URL} from "./static.url";
import {IStaticService} from "./static.interface";

'use strict'
/* @ngInject */
export class StaticService implements IStaticService {
	private HttpService:IHttpService;
	public STATIC_URL;
	constructor(HttpService:IHttpService, STATIC_URL:any) {
        this.STATIC_URL = STATIC_URL.getStaticUrl;
        this.HttpService = HttpService;
    }
    public sendContactMail(data){
       return this.HttpService.request(this.STATIC_URL.sendContactMail.method, this.STATIC_URL.sendContactMail.url, data);
    }
    public sendContactMailNonLoggedIn(data){
       return this.HttpService.request(this.STATIC_URL.sendContactMailNonLogged.method, this.STATIC_URL.sendContactMailNonLogged.url, data);
    }
}
