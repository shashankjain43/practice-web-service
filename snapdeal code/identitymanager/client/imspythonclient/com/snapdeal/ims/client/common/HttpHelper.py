#!/usr/bin/python3
class HttpHelper(dict):
    __instance__ = None

    def __new__(cls, *args, **kwargs):
        if HttpHelper.__instance__ is None:
            HttpHelper.__instance__ = dict.__new__(cls)
        return HttpHelper.__instance__

    def __init__(self, host, clientId, clientSecret, timeout):
        self.host = host
        self.clientId = clientId
        self.clientSecret = clientSecret
        self.timeout = timeout
        self.version = '1.0.0'
        self.timeout = 5000

    def calculatehash(reqString):
        import hashlib
        return hashlib.sha256(reqString.encode()).hexdigest()

    def _url(self, path):
        return self.host + path

    def _getVersion(self):
        return self.version

    def createheaders(self, requestObj, userAgent, userMachineIdentifier, apprequestid, method):
        import time
        timestamp = time.time()
        headers = {}
        headers['content-type'] = 'application/json'
        headers['accept'] = 'application/json'
        headers['clientId'] = self.clientId
        headers['client-version'] = self._getVersion()
        headers['timestamp'] = timestamp
        headers['user-Agent'] = userAgent
        headers['userMachineIdentifier'] = userMachineIdentifier
        headers['apprequestid'] = apprequestid
        if method == 'GET':
            hashString = self.clientId + self.clientSecret + str(timestamp);
            headers['hash'] = HttpHelper.calculatehash(hashString)
        else:
            from com.snapdeal.ims.client.common.JsonUtil import JsonUtil
            util = JsonUtil()
            hashString = util.json_repr(requestObj) + self.clientSecret + str(timestamp);
            print(hashString)
            headers['hash'] = HttpHelper.calculatehash(hashString)
        return headers


def main():
    import configparser
    config = configparser.ConfigParser()
    config.read("config.properties")
    print(config.sections())
    ims_properties = config['ims_properties']
    host = ims_properties['host']
    clientId = ims_properties['clientId']
    clientSecret = ims_properties['clientSecret']
    print(host)
    print(clientId)
    print(clientSecret)
    from com.snapdeal.ims.request.GetUserByTokenRequest import GetUserByTokenRequest
    from com.snapdeal.ims.client.common.JsonUtil import JsonUtil
    util = JsonUtil()
    req = GetUserByTokenRequest('tokenvalue')
    import uuid
    helper = HttpHelper(host,clientId,clientSecret,1000)
    headers = helper.createheaders(req, 'userAgent', 'userMachineIdentifier', uuid.uuid4().__str__(), 'POST')
    print(headers)
    headers = helper.createheaders(req, 'userAgent', 'userMachineIdentifier', uuid.uuid4().__str__(), 'GET')
    print(headers)


if __name__ == '__main__':
    main()
