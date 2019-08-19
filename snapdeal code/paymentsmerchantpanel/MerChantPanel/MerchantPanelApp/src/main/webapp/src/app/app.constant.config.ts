export class Constants {
  static get API_CONFIG(): any {
    return{
      BASE_URL:window.location.href.indexOf('localhost:')>-1 ?'http://52.76.15.141:8080/api':'/api',
      MOCK_RUN: false
    }
  }
}
