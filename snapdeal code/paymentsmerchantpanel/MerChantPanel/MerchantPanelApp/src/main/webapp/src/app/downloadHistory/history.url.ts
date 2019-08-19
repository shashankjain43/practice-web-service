/**
 * Created by chitra.parihar on 08-03-2016.
 */

  export class HISTORY_URL{
    static get getHistoryUrl() {
      return {
        DOWNLOAD_FILE: {
          METHOD: 'GET',
          URL: '/data/v1/download'
        },
        DOWNLOAD_DATA: {
          METHOD: 'GET',
          URL: '/data/v1/get/downloads'
        }
      }
    }
  }




