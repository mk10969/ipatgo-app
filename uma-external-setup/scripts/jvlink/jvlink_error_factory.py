from .config.utils import SingletonType
from .exception import *
from .jvlink_result import JvLinkResult


class JvLinkErrorFactory(object, metaclass=SingletonType):
    @staticmethod
    def create(result: JvLinkResult) -> JvLinkError:
        if result is None:
            raise AttributeError(f"結果オブジェクトが、{result}では、アカン！！！！")
        if JvLinkErrorCode._100.is_same_error_code(result):
            return JvLinkParameterError(JvLinkErrorCode._100)
        elif JvLinkErrorCode._101.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._101)
        elif JvLinkErrorCode._102.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._102)
        elif JvLinkErrorCode._103.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._103)
        elif JvLinkErrorCode._111.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._111)
        elif JvLinkErrorCode._112.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._112)
        elif JvLinkErrorCode._114.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._114)
        elif JvLinkErrorCode._115.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._115)
        elif JvLinkErrorCode._116.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._116)
        elif JvLinkErrorCode._118.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._118)
        elif JvLinkErrorCode._201.is_same_error_code(result):
            return JvLinkNotInitError(JvLinkErrorCode._201)
        elif JvLinkErrorCode._202.is_same_error_code(result):
            return JvLinkNotClosedError(JvLinkErrorCode._202)
        elif JvLinkErrorCode._203.is_same_error_code(result):
            return JvLinkNotOpenedError(JvLinkErrorCode._203)
        elif JvLinkErrorCode._211.is_same_error_code(result):
            return JvLinkRegistryError(JvLinkErrorCode._211)
        elif JvLinkErrorCode._212.is_same_error_code(result):
            return JvLinkRegistryError(JvLinkErrorCode._212)
        elif JvLinkErrorCode._301.is_same_error_code(result):
            return JvLinkAuthError(JvLinkErrorCode._301)
        elif JvLinkErrorCode._302.is_same_error_code(result):
            return JvLinkAuthError(JvLinkErrorCode._302)
        elif JvLinkErrorCode._303.is_same_error_code(result):
            return JvLinkValueErrorError(JvLinkErrorCode._303)
        elif JvLinkErrorCode._304.is_same_error_code(result):
            return JvLinkAuthError(JvLinkErrorCode._304)
        elif JvLinkErrorCode._401.is_same_error_code(result):
            return JvLinkInternalError(JvLinkErrorCode._401)
        elif JvLinkErrorCode._402.is_same_error_code(result):
            return JvLinkDownloadFileError(JvLinkErrorCode._402, result.filename)
        elif JvLinkErrorCode._403.is_same_error_code(result):
            return JvLinkDownloadFileError(JvLinkErrorCode._403, result.filename)
        elif JvLinkErrorCode._411.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._411)
        elif JvLinkErrorCode._412.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._412)
        elif JvLinkErrorCode._413.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._413)
        elif JvLinkErrorCode._421.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._421)
        elif JvLinkErrorCode._431.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._431)
        elif JvLinkErrorCode._501.is_same_error_code(result):
            return JvLinkSetupFailedError(JvLinkErrorCode._501)
        elif JvLinkErrorCode._502.is_same_error_code(result):
            return JvLinkDownloadFailedError(JvLinkErrorCode._502)
        elif JvLinkErrorCode._503.is_same_error_code(result):
            return JvLinkFileNotFoundError(JvLinkErrorCode._503)
        elif JvLinkErrorCode._504.is_same_error_code(result):
            return JvLinkServerError(JvLinkErrorCode._504)
        else:
            # 当てハマらない== 0 or -1で、問題ないということ。
            return None


if __name__ == '__main__':
    from .jvlink_result_factory import JvLinkResultFactory
    
    res = (-402, "", "1234", "sdfasd.txt")
    result = JvLinkResultFactory.create_JvRead_result(res)
    exception = JvLinkErrorFactory.create(result)
    print(exception)
    
    # for i in JvLinkErrorCode:
    #     print(i.is_same_error_code(result))
