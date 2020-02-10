class JvLinkError(Exception):
    def __init__(self, jvlink_error_code):
        self.__error_code = jvlink_error_code.get_error_code()
        self.__error_message = jvlink_error_code.get_message()
    
    def __repr__(self):
        return self.build()

    @property
    def error_code(self):
        return self.__error_code

    @property
    def error_message(self):
        return self.__error_message

    def build(self):
        return {
            "Error Code"    : self.error_code,
            "Error Message" : self.error_message
        }
        
        
class JvLinkParameterError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkValueErrorError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkNotInitError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkNotClosedError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkNotOpenedError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkRegistryError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkAuthError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkInternalError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkDownloadFileError(JvLinkError):
    def __init__(self, jvlink_error_code, filename):
        super().__init__(jvlink_error_code)
        self._filename = filename
    
    @property
    def filename(self):
        return self._filename
    
    def build(self):
        return {
            "Error Code"   : self.error_code,
            "Error Message": self.error_message,
            "fIle name"    : self.filename
        }


class JvLinkServerError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkSetupFailedError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkDownloadFailedError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


class JvLinkFileNotFoundError(JvLinkError):
    def __init__(self, jvlink_error_code):
        super().__init__(jvlink_error_code)


if __name__ == '__main__':
    from scripts.jvlink.exception.jvlink_error_code import JvLinkErrorCode
    
    e = JvLinkDownloadFileError(JvLinkErrorCode._402, "once.txt")
    print(e.__repr__())

    e1 = JvLinkParameterError(JvLinkErrorCode._100)
    print(e1.__repr__())
