'use strict';
/* @ngInject */
export class ACCOUNT_REQOBJECT {

    static  get getAccountRequest() {
        return {
            'PASSWORD_OBJECT':['oldPassword','newPassword'],
            'ADD_USER_OBJECT':['userName','loginName','email','roleList'],
            'EDIT_USER_OBJECT':['userName','loginName','email','userId','roleList']
        }
    }
}
