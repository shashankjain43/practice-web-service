
  "use strict";

  export interface IHistoryService{
    getDownloadHistory:()=>any;
    downloadFiles:(downloadObject:Object)=> any;
  }



