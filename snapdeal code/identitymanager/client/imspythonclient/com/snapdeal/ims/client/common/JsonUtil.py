#!/usr/bin/python3
class JsonUtil():
    """docstring for JsonUtil"""
    def __init__(self):
        super(JsonUtil, self).__init__()

    def json_repr(self,obj):
        import json
        """Represent instance of a class as JSON.
            Arguments:
                obj -- any object
            Return:
                String that reprent JSON-encoded object.
        """
        def serialize(obj):
            """Recursively walk object's hierarchy."""
            if isinstance(obj, (bool, int, float, str)):
                return obj
            elif isinstance(obj, dict):
                obj = obj.copy()
                for key in obj:
                    obj[key] = serialize(obj[key])
                    return obj
            elif isinstance(obj, list):
                return [serialize(item) for item in obj]
            elif isinstance(obj, tuple):
                return tuple(serialize([item for item in obj]))
            elif hasattr(obj, '__dict__'):
                return serialize(obj.__dict__)
            else:
                return repr(obj) # Don't know how to handle, convert to string
        return json.dumps(serialize(obj), allow_nan=False, sort_keys=True)


def main():
    from com.snapdeal.ims.request.GetUserByTokenRequest import GetUserByTokenRequest
    util = JsonUtil()
    req = GetUserByTokenRequest('tokenvalue')
    print(str(req))
    json = util.json_repr(req)
    print(json)

if __name__ == '__main__':
    main()