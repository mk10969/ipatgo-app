import base64
from logging import getLogger

from .config import KeyManage, create_condition
from .config.utils import execution_time
from .exception import JvLinkError
from .jvlink_manage import JvLinkManage

logger = getLogger("jvlink")


class JvLinkSetup(object):
    
    def __init__(self, option, data_key, record_key):
        self.__jvlink_manage = JvLinkManage()
        self._key = KeyManage()
        self.__condition = create_condition(self._key, option, data_key, record_key)
        self.__fromtime = "20000101000000"
    
    @property
    def fromtime(self):
        return self.__fromtime
    
    @property
    def condition(self):
        return self.__condition
    
    # generator
    def generator(self) -> str:
        with self.__jvlink_manage.reader(self.condition, self.fromtime) as iter_reader:
            try:
                for result in iter_reader:
                    yield result.data
            except JvLinkError as e:
                logger.error("ERROR: ", e)
                raise  # Error再送
            else:
                logger.info("正常に読み取りました。")
    
    @staticmethod
    def chunk_iterator(iterator, n: int):
        # TODO n<1を設定された時の対応。
        # iterator自体を、chunk処理します。
        # これにより、巨大データを一定のリソースで処理可能。
        
        iter = iterator
        exhausted = [False]  # レキシカルスコープ
        
        def subiter(it, n, exhausted):
            for i in range(n):
                try:
                    yield next(it)
                except StopIteration:
                    exhausted[0] = True
        
        # subiterをくるくる回す。
        while not exhausted[0]:
            yield subiter(iter, n, exhausted)
    
    @execution_time
    def write(self, file_path: str) -> None:
        try:
            with open(file_path, mode='a') as f:
                for i in self.generator():
                    f.write(base64.b64encode(i.encode("MS932")).decode())
                    f.write('\n')
        
        except (JvLinkError, UnicodeEncodeError) as e:
            logger.error("書き込みが失敗しました。", e)
        else:
            logger.info("書き込み完了しました。")
