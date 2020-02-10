import contextlib
from logging import getLogger

from .config import OpenCondition
from .config.utils import deco_NotNone
from .jvlink_adapter import JvLinkAdapter
from .jvlink_error_handler import JvLinkErrorHandler
from .jvlink_reader import JvLinkReader

logger = getLogger("jvlink")


class JvLinkManage(object):
    
    def __init__(self):
        self.jvlink = JvLinkAdapter()
        self.jvlink_sid = "UNKNOWN"
    
    def init(self):
        result = self.jvlink.JVInit(self.jvlink_sid)
        JvLinkErrorHandler.handle_error(result)
    
    @deco_NotNone
    def open(self, condition: OpenCondition, fromtime: str) -> JvLinkReader:
        result = self.jvlink.JVOpen(condition.data_key, fromtime, condition.option)
        JvLinkErrorHandler.handle_error(result)
        return JvLinkReader(self, condition)
    
    @deco_NotNone
    def rt_open(self, condition: OpenCondition, key: str) -> JvLinkReader:
        result = self.jvlink.JVRTOpen(condition.data_key, key)
        JvLinkErrorHandler.handle_error(result)
        return JvLinkReader(self, condition)
    
    @deco_NotNone
    def read(self, terminal_position):
        return self.jvlink.JVRead(terminal_position)
    
    def status(self):
        result = self.jvlink.JVStatus()
        JvLinkErrorHandler.handle_error(result)
        return result
    
    def close(self):
        result = self.jvlink.JVClose()
        JvLinkErrorHandler.handle_error(result)
    
    def skip(self):
        self.jvlink.JVSkip()
    
    def cancel(self):
        self.jvlink.JVCancel()
    
    # # 後で実装
    # def course_file(self):
    #     pass
    
    @contextlib.contextmanager
    def reader(self, condition, fromtime):
        try:
            logger.debug({"status": "初期化", "条件": condition, "fromtime": fromtime})
            self.init()
            yield self.open(condition, fromtime)
        
        finally:
            self.close()
            del self.jvlink
            logger.debug({"status": "クローズ", "条件": condition, "fromtime": fromtime})
    
    @contextlib.contextmanager
    def rt_reader(self, condition, key):
        try:
            logger.debug({"status": "初期化", "条件": condition, "racing_key": key})
            self.init()
            yield self.rt_open(condition, key)
        
        finally:
            self.close()
            del self.jvlink
            logger.debug({"status": "クローズ", "条件": condition, "racing_key": key})
