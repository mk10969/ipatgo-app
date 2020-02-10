from weakref import WeakKeyDictionary


class ResultData(object):
    def __init__(self):
        self._value = WeakKeyDictionary()
    
    def __get__(self, instance, owner):
        if instance is None:
            return self
        return self._value.get(instance, None)
    
    def __set__(self, instance, value):
        self._value[instance] = value


class JvLinkResult(object):
    return_code = ResultData()


class JvOpenResult(JvLinkResult):
    download_count = ResultData()
    read_count = ResultData()
    last_filename_timestamp = ResultData()


class JvReadResult(JvLinkResult):
    data = ResultData()
    byte = ResultData()
    filename = ResultData()


if __name__ == '__main__':
    pass
    # from ..jvlink_result_factory import JvLinkResultFactory
    # res = (-100, "aaa", "bbb", "ccc")
    # result = JvLinkResultFactory.create_JvRead_result(res)
    # res1 = (-200, "aaa", "bbb", "ccc")
    # result1 = JvLinkResultFactory.create_JvRead_result(res1)
    # print(result.return_code)
    # print(result1.return_code)
    # print(JvReadResult.__dict__["data"].__dict__["_value"].__dict__)
    
    # アカン
    # class ResultData(object):
    #
    #     def __init__(self):
    #         self.return_code = 0
    #
    #     def __get__(self, instance, owner):
    #         if instance is None:
    #             return self
    #         return self.return_code
    #
    #     def __set__(self, instance, value):
    #         if not (0 >= value >= -504):
    #             raise ValueError("return_code be between 0 and -504")
    #         self.return_code = value
