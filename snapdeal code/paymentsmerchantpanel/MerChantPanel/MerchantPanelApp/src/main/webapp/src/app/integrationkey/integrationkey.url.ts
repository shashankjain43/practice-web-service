export class APP_INTEGRATION_URL{
    static get getIntegrationKeyUrl() {
      return {
        sandbox:{
            method:'GET',
            url : '/integrate/v1/sandbox'
        },
        production:
        {
            method:'GET',
            url : '/integrate/v1/production'
        }
      }
    }
  }
