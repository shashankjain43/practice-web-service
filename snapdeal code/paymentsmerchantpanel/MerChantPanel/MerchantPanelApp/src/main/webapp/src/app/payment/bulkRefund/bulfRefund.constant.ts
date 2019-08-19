
  export class BULK_REFUND_CONSTANT {

    static  get getHistoryConstant() {
      return {
        'DOWNLOAD_HEADER': [
          {
            'KEY': 'id',
            'DISPLAY_VALUE': 'ID',
            'IS_SHOWN': false,
            'PRIORITY': 1
          },
          {
            'KEY': 'fileName',
            'DISPLAY_VALUE': 'File Name',
            'IS_SHOWN': true,
            'PRIORITY': 2
          },
          {
            'KEY': 'viewed',
            'DISPLAY_VALUE': 'Viewed',
            'IS_SHOWN': true,
            'PRIORITY': 3
          },
          {
            'KEY': 'uploadStatus',
            'DISPLAY_VALUE': 'Status',
            'IS_SHOWN': true,
            'PRIORITY': 4
          },
          {
            'KEY': 'timestamp',
            'DISPLAY_VALUE': 'Date',
            'IS_SHOWN': true,
            'PRIORITY': 5
          },
          {
            'KEY': 'refundStatus',
            'DISPLAY_VALUE': 'Refund Status',
            'IS_SHOWN': true,
            'PRIORITY': 6
          },
          {
            'KEY': 'download',
            'DISPLAY_VALUE': 'Download files',
            'IS_SHOWN': true,
            'PRIORITY': 7
          }],
        'FILE_SIZE': 150
      };
    }
  }
