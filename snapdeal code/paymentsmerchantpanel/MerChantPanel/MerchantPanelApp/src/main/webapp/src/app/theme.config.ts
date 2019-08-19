/** @ngInject */
export function mdThemingProvider($mdThemingProvider:angular.material.IThemingProvider) {
  $mdThemingProvider.theme('default')
    .primaryPalette('deep-orange')
    .accentPalette('deep-orange');
}
