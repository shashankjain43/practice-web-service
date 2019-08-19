/**
 * Created by chitra.parihar on 18-03-2016.
 */
export class HISTORY_CONSTANT {

  static  get getHistoryConstant() {
    return {
      'DOWNLOAD_HEADER': [
        {
          'KEY': 'id',
          'DISPLAY_VALUE': 'Id',
          'IS_SHOWN': false,
          'PRIORITY': 1
        },
        {
          'KEY': 'viewed',
          'DISPLAY_VALUE': 'Viewed',
          'IS_SHOWN': true,
          'PRIORITY': 2
        },
        {
          'KEY': 'status',
          'DISPLAY_VALUE': 'Status',
          'IS_SHOWN': true,
          'PRIORITY': 3
        },
        {
          'KEY': 'timestamp',
          'DISPLAY_VALUE': 'Date',
          'IS_SHOWN': true,
          'PRIORITY': 4
        },
        {
          'KEY': 'fileName',
          'DISPLAY_VALUE': 'File Name',
          'IS_SHOWN': true,
          'PRIORITY': 0
        },
        {
          'KEY': 'download',
          'DISPLAY_VALUE': 'Download files',
          'IS_SHOWN': true,
          'PRIORITY': 7
        }]
    }
  }
}
