from .jvlink_error_factory import JvLinkErrorFactory
from .jvlink_result import JvLinkResult
from ..common import SingletonType


class JvLinkErrorHandler(object, metaclass=SingletonType):
    @staticmethod
    def handle_error(result: JvLinkResult) -> None:
        error = JvLinkErrorFactory.create(result)
        if error is not None:
            raise error


if __name__ == '__main__':
    from .jvlink_result_factory import JvLinkResultFactory
    
    res = (-402, "", "1234", "sdfasd.txt")
    #    res = (0, "", "", "")
    result = JvLinkResultFactory.create_JvRead_result(res)
    JvLinkErrorHandler.handle_error(result)
