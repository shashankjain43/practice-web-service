
  "use strict";

  export interface IMobService{
    getBusinessTypes:()=>any;
    getBusinessCategories:()=>any;
    getBusinessSubCategories:(String)=>any;
    mobSignUp:(Object)=> any;
    getKycDocs:(String)=> any;
    getUploadedKycDocs:()=>any;
  }



