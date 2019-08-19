import {APP_ACCOUNT_URL} from "./account.url";
'use strict';
/* @ngInject */
export function accountMockRun($httpBackend:angular.IHttpBackendService, API_CONFIG) {
    if (API_CONFIG.MOCK_RUN) {
        this.APP_ACCOUNT_URL = APP_ACCOUNT_URL.getAccountURL;
        $httpBackend.whenPOST(new RegExp(this.APP_ACCOUNT_URL.ADD_USERS.URL)).respond(function ( ) {
            var addUserResponse ={
                'error': null,
                'data': {
                    'success': true
                }
            };
            return [200, addUserResponse, {}];
        });
        $httpBackend.whenPOST(new RegExp(this.APP_ACCOUNT_URL.EDIT_USERS.URL)).respond(function ( ) {
            var editUserResponse ={
                'error': null,
                'data': {
                    'success': true
                }
            };
            return [200, editUserResponse, {}];
        });
        $httpBackend.whenGET(new RegExp(this.APP_ACCOUNT_URL.VERIFY_USER.URL)).respond(function ( ) {
            var editUserResponse ={
                'error': null,
                'data': {
                    'userPresent': true
                }
            };
            return [200, editUserResponse, {}];
        });

        $httpBackend.whenGET(new RegExp(this.APP_ACCOUNT_URL.VIEW_PROFILE.URL)).respond(function () {
            var accountResponse = {
                'data': {
                    'contactInfo': {
                        'primaryEmail': 'primaryEmail@gamil.com',
                        'mobile': '90909090',
                        'landline': '011909090',
                        'addesss': 'addesss',
                        'secondaryEmail': 'SecondaryEmail@gamil.com'
                    },
                    'bankInfo': {
                        'bankAccount': '0010110101',
                        'bankName': 'bankName',
                        'ifsc': 'bn0001'
                    },
                    'businessInfo': {
                        'businessType': 'businessType',
                        'businessCategory': 'businessCategory',
                        'subCategory': 'subCategory',
                        'tin': 'tin'
                    }
                },
                'error': null

            };
            return [200, accountResponse, {}];
        });
        $httpBackend.whenGET(new RegExp(this.APP_ACCOUNT_URL.CHANGE_PASSSWORD.URL)).respond(function () {
            var accountResponse = {
                'error': null,
                'data': {
                    'success': true
                }
            };
            return [200, accountResponse, {}];
        });
        $httpBackend.whenGET(new RegExp(this.APP_ACCOUNT_URL.GET_ROLES.URL)).respond(function () {
            var allRoles = {
                'error': null,
                'data': {
                    'roles': [
                        {
                            'id': 1,
                            'name': 'Admin',
                            'permissions': [
                                {
                                    'id': 1,
                                    'name': 'MERC_ADD_USER',
                                    'displayName': 'Add User',
                                    'enabled': false
                                },
                                {
                                    'id': 2,
                                    'name': 'MERC_EDIT_USER',
                                    'displayName': 'Edit User',
                                    'enabled': false
                                }
                            ]
                        },
                        {
                            'id': 2,
                            'name': 'Accounting',
                            'permissions': [
                                {
                                    'id': 101,
                                    'name': 'perm1',
                                    'displayName': 'Initiate refund',
                                    'enabled': false
                                },
                                {
                                    'id': 102,
                                    'name': 'perm2',
                                    'displayName': 'View Transactions',
                                    'enabled': false
                                },
                                {
                                    'id': 103,
                                    'name': 'perm3',
                                    'displayName': 'Generate Settlement',
                                    'enabled': false
                                }
                            ]
                        },
                        {
                            'id': 3,
                            'name': 'Integration',
                            'permissions': [
                                {
                                    'id': 101,
                                    'name': 'perm1',
                                    'displayName': ' View Production API',
                                    'enabled': false
                                },
                                {
                                    'id': 102,
                                    'name': 'perm2',
                                    'displayName': 'View Sandbox',
                                    'enabled': false
                                },
                                {
                                    'id': 103,
                                    'name': 'perm3',
                                    'displayName': 'View Bank',
                                    'enabled': false
                                },
                                {
                                    'id': 104,
                                    'name': 'perm4',
                                    'displayName': 'API Activation',
                                    'enabled': false
                                }
                            ]
                        }
                    ]
                }
            };
            return [200, allRoles, {}];
        });
        $httpBackend.whenGET(new RegExp(this.APP_ACCOUNT_URL.GET_USERS.URL)).respond(function () {
            var accountResponse = {
                'error': null,
                'data': {
                    'users': [
                        {
                            'userId': '103',
                            'userName': 'userName3',
                            'emailId': 'use31@gamil.com',
                            'loginName': 'user3',
                            'roleList': [
                                {
                                    'id': 123,
                                    'name': 'Integration',
                                    'permissions': [
                                        {
                                            'id': 101,
                                            'name': 'perm1',
                                            'displayName': ' View Production API',
                                            'enabled': false
                                        },
                                        {
                                            'id': 102,
                                            'name': 'perm2',
                                            'displayName': 'View Sandbox',
                                            'enabled': true
                                        }
                                    ]
                                },
                                {
                                    'id': 123,
                                    'name': 'Admin',
                                    'permissions': [
                                        {
                                            'id': 1,
                                            'name': 'MERC_ADD_USER',
                                            'displayName': 'Add User',
                                            'enabled': true
                                        },
                                        {
                                            'id': 2,
                                            'name': 'MERC_EDIT_USER',
                                            'displayName': 'Edit User',
                                            'enabled': true
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            'userId': '102',
                            'userName': 'userName2',
                            'emailId': 'user1@gamil.com',
                            'loginName': 'user1',
                            'roleList': [
                                {
                                    'id': 112,
                                    'name': 'Accounting',
                                    'permissions': [
                                        {
                                            'id': 101,
                                            'name': 'perm1',
                                            'displayName': 'Initiate refund',
                                            'enabled': true
                                        },

                                        {
                                            'id': 103,
                                            'name': 'perm3',
                                            'displayName': 'Generate Settlement',
                                            'enabled': false
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            'userId': '101',
                            'userName': 'userName1',
                            'emailId': 'use21@gamil.com',
                            'loginName': 'user2',
                            'roleList': [
                                {
                                    'id': 123,
                                    'name': 'Admin',
                                    'permissions': [
                                        {
                                            'id': 101,
                                            'name': 'perm1',
                                            'displayName': 'Change Password',
                                            'enabled': true
                                        },
                                        {
                                            'id': 102,
                                            'name': 'perm2',
                                            'displayName': 'Add User',
                                            'enabled': true
                                        },
                                        {
                                            'id': 103,
                                            'name': 'perm3',
                                            'displayName': 'View Bank',
                                            'enabled': false
                                        },
                                        {
                                            'id': 104,
                                            'name': 'perm4',
                                            'displayName': 'View Business Info',
                                            'enabled': false
                                        }
                                    ]
                                }
                            ]
                        }

                    ]
                }
            };
            return [200, accountResponse, {}];
        });
    }
}
