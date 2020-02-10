from .jvlink_result import JvLinkResult, JvOpenResult, JvReadResult
from ..common import SingletonType


class JvLinkResultFactory(object, metaclass=SingletonType):
    @staticmethod
    def create_JvLink_result(result: int) -> JvLinkResult:
        jv_result = JvLinkResult()
        jv_result.return_code = result
        return jv_result
    
    @staticmethod
    def create_JvOpen_result(result_ary: tuple) -> JvLinkResult:
        jv_result = JvOpenResult()
        jv_result.return_code = result_ary[0]
        jv_result.download_count = result_ary[1]
        jv_result.read_count = result_ary[2]
        jv_result.last_filename_timestamp = result_ary[3]
        return jv_result
    
    @staticmethod
    def create_JvRead_result(result_ary: tuple) -> JvLinkResult:
        jv_result = JvReadResult()
        jv_result.return_code = result_ary[0]
        jv_result.data = result_ary[1]
        jv_result.byte = result_ary[2]
        jv_result.filename = result_ary[3]
        return jv_result
