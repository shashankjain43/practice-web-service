#!/usr/bin/python3
from com.snapdeal.ims.client.AbstractServiceClient import AbstractServiceClient
class UserServiceClient(AbstractServiceClient):
    def getuserbytoken(self, getuserbytokenrequest):
        self._execute_(getuserbytokenrequest,'GET', '/users/token/$token')

def main():
    client = UserServiceClient()
    from com.snapdeal.ims.request.GetUserByTokenRequest import GetUserByTokenRequest
    req = GetUserByTokenRequest('tokenvalue')
    client.getuserbytoken(req)

if __name__ == '__main__':
    main()