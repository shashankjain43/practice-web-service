/**
 * Created by shruti.aggarwal on 22/4/16.
 */

'use strict';

/* @ngInject */
export  class QrcodeController{
  qr: string;
  payTag: string;
  constructor($state, $stateParams, $filter){
    this.qr = $filter('trim')($stateParams.qr);
    this.payTag = $filter('trim')($stateParams.payTag);
  }
}
