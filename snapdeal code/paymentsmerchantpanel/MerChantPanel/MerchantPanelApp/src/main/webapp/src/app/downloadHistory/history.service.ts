import {IHistoryService} from "./history.interface";
import {HttpService} from "../shared/services/http.service";
import {HISTORY_URL} from "./history.url";
import {IHttpService} from "../shared/services/http.interface";


"use strict";
/* @ngInject */
export class HistoryService implements IHistoryService {

  URL_CONSTANT:any;
  private HttpService:IHttpService;

  constructor(HttpService:IHttpService) {
    this.URL_CONSTANT = HISTORY_URL.getHistoryUrl;
    this.HttpService = HttpService;
  }

  public getDownloadHistory() {

    return this.HttpService.request(this.URL_CONSTANT.DOWNLOAD_DATA.METHOD, this.URL_CONSTANT.DOWNLOAD_DATA.URL);
  }

  public downloadFiles(downloadObject:any) {
    var uri = '?id='+downloadObject.id;
    return this.HttpService.request(this.URL_CONSTANT.DOWNLOAD_FILE.METHOD, this.URL_CONSTANT.DOWNLOAD_FILE.URL + uri);
  }
}

