import {IFilter} from "./IFilter.interface";
import {IUserSelectedFilterVM} from "../filterVM/IUserSelectFilterVM";

export class TransactionStatusFilter implements IFilter {

  getkey(transactionStatusFilterVM: IUserSelectedFilterVM[]) {
    return transactionStatusFilterVM[0].key;
  }

  getValue(transactionStatusFilterVM: IUserSelectedFilterVM[]):string {
    var value: string = '';
    angular.forEach(transactionStatusFilterVM, function(transactionStatusFilter: IUserSelectedFilterVM, key){
        value += transactionStatusFilter.value;
    });
    return value;
  }

}
