/** @ngInject */
export function httpConfig($httpProvider: ng.IHttpProvider) {
  $httpProvider.interceptors.push('HttpRequestInterceptor');
}
