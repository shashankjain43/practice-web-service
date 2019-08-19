
    /* @ngInject */
    export default function authenticationMockRun($httpBackend: angular.IHttpBackendService, Auth_URL, API_CONFIG) {
        if (API_CONFIG.MOCK_RUN) {
            $httpBackend.whenPOST(new RegExp(Auth_URL.LOGIN.URL)).respond(function () {
                var loginResponse = {
                    'error': null,
                    'data': {
                        'token': 'user_token',
                        'merchantId': 'bc55c145-8356-4d11-82ed-f782fb2cd5cb',
                        'userDTO': {
                            'userId': 'userId1',
                            'userName': 'userName1',
                            'emailId': 'emailId@gmail.com',
                            'loginName': 'logicnName123',
                            'roleList': [
                                {
                                    'id': 1,
                                    'displayName': 'ACCOUNT',
                                    'name':'Account',
                                    'permissions': [
                                        {
                                            'id': 1001,
                                            'name': 'MANAGE_USER',
                                            'displayName': 'Manage User',
                                            'enabled': true
                                        },
                                        {
                                            'id': 1010,
                                            'name': 'VIEW_MERCHANT_PROFILE',
                                            'displayName': 'View merchant profile',
                                            'enabled': true
                                        }
                                    ]
                                },
                                {
                                    'id': 102,
                                    'displayName': 'PAYMENTS',
                                    'name':'Payments',
                                    'permissions': [
                                        {
                                            'id': 1020,
                                            'name': 'VIEW_TRANSACTION',
                                            'displayName': 'View Transaction',
                                            'enabled': true
                                        },
                                        {
                                            'id': 1021,
                                            'name': 'MCNT_INIT_REFUND',
                                            'displayName': 'Initiate Refund',
                                            'enabled': true
                                        },
                                        {
                                            'id': 1022,
                                            'name': 'MCNT_BULK_INIT_REFUND',
                                            'displayName': 'Bulk Refund',
                                            'enabled': true
                                        }
                                    ]
                                },
                                {
                                    'id': 103,
                                    'displayName': 'INTEGRATION',
                                    'name':'Integration',
                                    'permissions': [
                                        {
                                            'id': 1030,
                                            'name': 'PRODUCTION',
                                            'displayName': 'Production',
                                            'enabled': true
                                        },
                                        {
                                            'id': 1031,
                                            'name': 'SANDBOX',
                                            'displayName': 'Sandbox',
                                            'enabled': true
                                        }
                                    ]
                                }
                            ]
                        }
                    }
                };
                return [200, loginResponse, {}];
            });

            $httpBackend.whenPOST(new RegExp(Auth_URL.FORGOT_PASSWORD.URL)).respond(function ( ) {
                var verifyOtpResponse ={
                    'error': null,
                    'data': {
                        'otpId': '02edb51f-140e-43ef-9ab4-9e4273f8332e'
                    }
                };
                return [200, verifyOtpResponse, {}];
            });

            $httpBackend.whenPOST(new RegExp(Auth_URL.SET_PASSWORD.URL)).respond(function ( ) {
                var setPasswordResponse ={
                    'error': null,
                    'data': {
                        'success': true                    }
                };
                return [200, setPasswordResponse, {}];
            });
            $httpBackend.whenPOST(new RegExp(Auth_URL.VERIFY_OTP.URL)).respond(function ( ) {
                var verifyOtpResponse ={
                    'error': null,
                    'data': {
                        'message': 'Password reset successfully'
                    }
                };
                return [200, verifyOtpResponse, {}];
            });

            $httpBackend.whenPOST(new RegExp(Auth_URL.RESEND_OTP.URL)).respond(function ( ) {
                var resendOtpResponse ={
                    'error': null,
                    'data': {
                        'otpId': '02edb51f-140e-43ef-9ab4-9e4273f8332e'
                    }
                };
                return [200, resendOtpResponse, {}];
            });
        }
    }

