#!/usr/bin/python3
class AbstractServiceClient:
    def _execute_(self, requestobj, httpmethod, uri):
        print(self)
        print(requestobj)
        print(httpmethod)
        print(uri)
        pass


