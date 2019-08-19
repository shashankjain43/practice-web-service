"use strict";

export interface IManageService{
    getUsersOfMerchant:()=>any;
    addUsers:(Object)=>any;
	editUsers:(Object)=>any;
	verifyUser:(Object)=>any;
}
