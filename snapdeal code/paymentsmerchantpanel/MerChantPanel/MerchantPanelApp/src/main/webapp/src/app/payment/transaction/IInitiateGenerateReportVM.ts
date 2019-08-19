/**
 * Created by shruti.aggarwal on 12/4/16.
 */
export interface IInitiateGenerateReportVM {
  dateFilter: DateFilter;
  status: boolean;
  message: string;
  forbiddenMessage: string;
  forbiddenStatus: boolean;
}

export class DateFilter {
  startDate: string;
  endDate: string;
}
