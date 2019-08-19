/* @ngInject */
  export function moduleConfig($stateProvider:angular.ui.IStateProvider) {
    $stateProvider
      .state('authentication', {
        abstract: true,
        templateUrl: 'app/authentication/authentication.tmpl.html'
      })
      .state('authentication.login', {
        url: '/',
        templateUrl: 'app/authentication/login/login.tmpl.html',
        controller: 'LoginController',
        controllerAs: 'vm',
        metaTags: {
                title: 'Merchant Center Account for Payment Solutions for Online & Offline Stores - Freecharge.in',
                description: 'FreeCharge Merchant center account provides a platform for online and offline seller stores to receive payments from their consumer using Freecharge wallet at their stores and online stores',
                keywords: 'Merchant Account, Merchant Partners, Freecharge',
                properties: {
                    'og:title': 'Merchant Center Account for Payment Solutions for Online & Offline Stores - Freecharge.in'
                }
            }
      })
        .state('authentication.forgotPassword', {
          url: '/forgotpassword',
          templateUrl: 'app/authentication/forgotPassword/forgotPassword.tmpl.html',
          controller: 'ForgotPasswordController',
          controllerAs: 'vm'
        })
        .state('authentication.setPassword', {
          url: '/setpassword/:hasval',
          templateUrl: 'app/authentication/setPassword/setPassword.tmpl.html',
          controller: 'SetFirstPasswordController',
          controllerAs: 'vm'
        })
        .state('authentication.onlineproducts', {
          url: '/onlineproducts',
          templateUrl: 'app/authentication/products/onlineproducts.tmpl.html',
          controller: 'LoginController',
          controllerAs: 'vm',
          metaTags: {
                title: 'Merchant Center Online Store | Payment Solutions for Merchant Accounts- Freecharge',
                description: 'Fastest & Safest way to pay online using freecharge wallets. Best Payment solutions for Merchants who look for one click payments on their app or website with best in class success rate.',
                keywords: 'Online Merchant Store, Freecharge online payment solution',
                properties: {
                    'og:title': 'Merchant Center Online Store | Payment Solutions for Merchant Accounts- Freecharge'
                }
            }
        })
        .state('authentication.offlineproducts', {
          url: '/offlineproducts',
          templateUrl: 'app/authentication/products/offlineproducts.tmpl.html',
          controller: 'LoginController',
          controllerAs: 'vm',
          metaTags: {
                title: 'Merchant Center Physical Store | Payment Solutions for Merchants Accounts- Freecharge',
                description: 'Fastest & Safest way to pay Offline without data or network connectivity. Best product for Offline Merchants who prefer one click payments from Freecharge wallet for their stores with best in class success rate. The merchants can receive payments from their consumer using Freecharge wallet at their stores.',
                keywords: 'Physical Store, Freecharge offline payment solution',
                properties: {
                    'og:title': 'Merchant Center Physical Store | Payment Solutions for Merchants Accounts- Freecharge'
                }
            }
        }).state('authentication.pricing', {
          url: '/pricing',
          templateUrl: 'app/authentication/pricing/pricing.tmpl.html',
          controller: 'LoginController',
          controllerAs: 'vm'
        })
        .state('authentication.logout', {
          controller: 'LogoutController',
          controllerAs: 'vm'
        });
  }

