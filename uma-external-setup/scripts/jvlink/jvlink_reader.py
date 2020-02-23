from time import sleep

from .config import StoreOpenCondition
from .jvlink_error_handler import JvLinkErrorHandler


# 一回openすると、閉じないとエラーになる。
# いくらreaderのポイントを保持していても意味がない。
# class JVlinkReaderContainer(object):
#     def __init__(self, jvlink_manage, condition):
#         self.jvlink_manage = jvlink_manage
#         self.condition = condition
#
#     def __iter__(self):
#         return JvLinkReader(jvlink_manage=self.jvlink_manage, condition=self.condition)
#

class JvLinkReader(object):
    def __init__(self, jvlink_manage, condition):
        self.__jvlink_manage = jvlink_manage
        self.__condition = condition
        self.__return_code = -1
        self.count = 0
    
    def __iter__(self):
        return self
    
    def __next__(self):
        if self.__return_code == 0:
            raise StopIteration()
        
        result = self.__jvlink_manage.read(self.__condition.terminal_position)
        self.__return_code = result.return_code
        
        if self.__return_code < -1:
            # データダウンロード中の場合、-3が返ってくる。
            if self.__return_code == -3:
                # ファイルダウンロード待ち時間
                sleep(1)
                return self.__next__()
            JvLinkErrorHandler.handle_error(result)
        
        # 開始文字が、レコードkeyではない、または、データがnullの場合。
        if not result.data.startswith(self.__condition.record_key) or not result.data:
            # realtimeOpenはskip不要。
            if isinstance(self.__condition, StoreOpenCondition):
                self.__jvlink_manage.skip()
            
            return self.__next__()
        
        self.count += 1
        return result
