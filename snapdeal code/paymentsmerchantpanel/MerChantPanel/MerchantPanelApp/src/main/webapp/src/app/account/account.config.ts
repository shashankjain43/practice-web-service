/**
 * Created by tanya.kanodia on 21-03-2016.
 */
import {IAccountService} from "./account.interface";
import {IManageService} from "./manage/manage.interface";

'use strict';
/* @ngInject */
export function moduleConfig($stateProvider:angular.ui.IStateProvider, $locationProvider: angular.ILocationProvider) {
  $stateProvider
    .state('base.default.account', {
        abstract: true,
        views: {
            '': {
                controller: 'AccountController',
                templateUrl: 'app/account/account.tmpl.html',
                controllerAs: 'vm'
            },
            'subHeader':{
                templateUrl: 'app/base/layoutComponent/subHeader/subHeader.tmpl.html',
                controller: 'SubHeaderController',
                controllerAs: 'vm',
                resolve:{
                    /* @ngInject */

                    SubHeaderFor: function(ACCESS_CONSTANT){
                        return ACCESS_CONSTANT().PERMISSIONS.ACCOUNTS;
                    }
                }
            }
        }
    })
    .state('base.default.account.content', {
        abstract: true,
        views: {
            'user': {
                template: '<div ui-view="user"></div>'
            },
            'content': {
                template: '<div ui-view></div>'
            }
        },
        resolve: {
            /* @ngInject */
            profileData: function (AccountService:IAccountService) {
                return AccountService.viewProfile();
            }
        }
    })
    .state('base.default.account.content.profile', {
        url: '/profile',
        controller: 'ProfileController',
        templateUrl: 'app/account/profile/profile.tmpl.html',
        controllerAs: 'vm',
        resolve: {
            /* @ngInject */
            profileInfo: function (profileData) {
                return {'value': profileData.data};
            }
        }
    })
    .state('base.default.account.content.password', {
        url: '/password',
        controller: 'PasswordController',
        templateUrl: 'app/account/password/password.tmpl.html',
        controllerAs: 'vm'
    })
      .state('base.default.account.content.manage', {
          url: '/manage',
          views: {
              '': {
                  templateUrl: 'app/account/manage/manage.tmpl.html',
                  controller: 'ManageController',
                  controllerAs: 'vm'
              },
              'user': {
                  templateUrl: 'app/account/manage/user.tmpl.html',
                  controller: 'UserController',
                  controllerAs: 'vm',
                  resolve: {
                      allRole: function (AccountService:IAccountService) {
                          return AccountService.getRoles();
                      }
                  }
              }
          },
          resolve: {
              allUsers: function (ManageService:IManageService) {
                  return ManageService.getUsersOfMerchant();
              }

          }
      });

      // use the HTML5 History API
         /*   $locationProvider.html5Mode(true);
        $locationProvider.hashPrefix('!'); */
}
