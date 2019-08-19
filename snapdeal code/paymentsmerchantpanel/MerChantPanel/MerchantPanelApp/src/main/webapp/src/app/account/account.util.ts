import {IAccountUtilService} from "./accountUtil.interface";

'use strict';
/* @ngInject */

export class AccountUtilService implements IAccountUtilService {
    constructor() {
    }
    public regularText(text) {
        var regularText=text.replace(/([A-Z])/g, ' $1').replace(/^./, function(str){ return str.toUpperCase(); });
        return regularText;
    }
}
