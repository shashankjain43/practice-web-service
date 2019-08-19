#!/usr/bin/python3
from com.snapdeal.ims.request.AbstractRequest import AbstractRequest


class GetUserByTokenRequest(AbstractRequest):
    def __init__(self, token):
        self.token = token
