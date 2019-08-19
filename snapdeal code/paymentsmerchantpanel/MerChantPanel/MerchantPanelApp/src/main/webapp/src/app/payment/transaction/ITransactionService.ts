/**
 * Created by shruti.aggarwal on 12/4/16.
 */
export interface ITransactionService{
  transactionHeadersVM: Array;
  viewRefund(Object, string): ng.IPromise;
  getFilteredTransactions(Object, string): ng.IPromise;
  initiateGenerateReport(Object): ng.IPromise;
  showRefundedAmount(Object): ng.IPromise;
}
