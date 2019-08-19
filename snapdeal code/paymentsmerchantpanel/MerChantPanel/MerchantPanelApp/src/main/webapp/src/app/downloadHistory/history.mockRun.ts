import {HISTORY_URL} from "./history.url";

/* @ngInject */
export default function historyMockRun($httpBackend:angular.IHttpBackendService, API_CONFIG) {
    if (API_CONFIG.MOCK_RUN) {
        this.historyUrl = HISTORY_URL.getHistoryUrl;
        $httpBackend.whenGET(new RegExp(this.historyUrl.DOWNLOAD_DATA.URL)).respond(function () {
            var downloadHistoryResponse = {
                'error': null,
                'data': {
                    'info': [
                        {
                            'id': 1,
                            'viewed': false,
                            'fileName': '1name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452024323000
                        },
                        {
                            'id': 2,
                            'viewed': false,
                            'fileName': '2name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452049695000
                        },
                        {
                            'id': 3,
                            'viewed': false,
                            'fileName': '3name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452050347000
                        },
                        {
                            'id': 4,
                            'viewed': false,
                            'fileName': '4name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452050658000
                        },
                        {
                            'id': 5,
                            'viewed': false,
                            'fileName': '5name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452050872000
                        },
                        {
                            'id': 6,
                            'viewed': false,
                            'fileName': '6name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452051241000
                        },
                        {
                            'id': 7,
                            'viewed': false,
                            'fileName': '7name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452051657000
                        },
                        {
                            'id': 8,
                            'viewed': false,
                            'fileName': '18name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452052101000
                        },
                        {
                            'id': 9,
                            'viewed': false,
                            'fileName': '9name',
                            'status': 'INPROGRESS',
                            'timestamp': 1452052511000
                        }
                    ]
                }
            };
            return [200, downloadHistoryResponse, {}];
        });
    }
}
