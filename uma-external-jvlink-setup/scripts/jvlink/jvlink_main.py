# from logging import getLogger
#
# import pandas as pd
# from flask import jsonify
# from pandas import DataFrame
#
# from scripts.common.helper import execution_time
# from scripts.jvlink import settings
# from .config import KeyManage, create_condition
# from .exception import JvLinkError
# from .jvlink_manage import JvLinkManage
#
# logger = getLogger("jvlink")
#
#
# class JvLink(object):
#
#     def __init__(self, option, data_key, record_key):
#         # key instance (singleton, properties, read only)
#         self._key = KeyManage()
#
#         # condition instance (jvlink data condition properties)
#         self.__condition = create_condition(self._key, option, data_key, record_key)
#
#         self._columns = self._get_columns()
#         self._slices = self._get_slices()
#
#         # jvlink manage instance (context manager and delegate jvlink connection)
#         self.__jvlink_manage = JvLinkManage()
#
#         # datetime condition properties
#         self.fromtime, self.racing_key = self._get_()
#
#     @property
#     def columns(self):
#         return self._columns
#
#     @property
#     def slices(self):
#         return self._slices
#
#     @property
#     def condition(self):
#         return self.__condition
#
#     def _get_(self) -> tuple:
#         if self.is_rt_option():
#             # 速報系のデータ
#             return None, ""
#
#         else:
#             # 蓄積系のデータ
#             return settings.fromtime, None
#
#     def is_setup_option(self) -> bool:
#         # SetUp データである場合 -> 4
#         # Setup データでない場合 -> 0, 1, 2
#         if self.condition.option == 4:
#             return True
#         else:
#             return False
#
#     def is_rt_option(self) -> bool:
#         # 速報系データの場合 -> 0
#         # 蓄積系データの場合 -> 1, 2, 4
#         if self.condition.option == 0:
#             return True
#         else:
#             return False
#
#     def ok(self, count: int):
#         logger.info(
#             {"status": "正常終了",
#              "条件": {"データ条件": self.condition, "開始日程条件": self.fromtime, "レース条件": self.racing_key},
#              "読み取った行数": count}
#         )
#
#     def error(self, e: Exception):
#         logger.error(
#             {"status": "異常終了",
#              "条件": {"データ条件": self.condition, "開始日程条件": self.fromtime, "レース条件": self.racing_key},
#              "エラー原因": e.__repr__()}
#         )
#
#     # generator
#     def delegator(self) -> str:
#         if self.is_rt_option():
#             yield from self.rt_generator()
#         else:
#             yield from self.generator()
#
#     # generator
#     def generator(self) -> str:  # Unicode
#         with self.__jvlink_manage.reader(self.condition, self.fromtime) as iter_reader:
#             try:
#                 for result in iter_reader:
#                     yield result.data
#
#             # iter_readerが、Iteratorオブジェクトではなく、
#             # Errorオブジェクトの場合（-111, -114等）拾えない。
#             # （呼び出し元の self.request() で拾っている。）
#             # そもそも、ここでErrorを拾う意図は、elseの処理がしたいから。
#             except JvLinkError as e:
#                 self.error(e)
#                 raise  # Error再送
#
#             else:
#                 self.ok(iter_reader.count)
#
#     # generator
#     def rt_generator(self) -> str:  # Unicode
#         with self.__jvlink_manage.rt_reader(self.condition, self.racing_key) as iter_reader:
#             try:
#                 for result in iter_reader:
#                     yield result.data
#
#             except JvLinkError as e:
#                 self.error(e)
#                 raise  # Error再送
#
#             else:
#                 self.ok(iter_reader.count)
#
#     @execution_time
#     def request(self):
#         if self.is_setup_option():
#             # データ量が馬鹿でかいので、メソッドをコールされると、
#             # Listにすべてデータを格納するので、Out Of Memoryで死ぬ可能性が高い。
#
#             raise Exception("アカン！！！！")
#
#         try:
#             array_2 = [self.__slicer(data=x) for x in self.delegator()]
#
#         except (JvLinkError, UnicodeEncodeError) as e:
#             self.error(e)
#
#             # TODO: レスポンスとしては、エラーを１つにまとめる。
#             #       リクエスト側でエラーが返ってきた場合どうにもリクエスト側では何もできないので。
#             #       つまり、データが取れれば、カラムとレコードが取れるが、
#             #       何かしらのエラーが出た場合、空のデータが返る。
#             #       メンテナスだけ、伝えるとかでもありかも。。。いや、いらんか。。。そもそもサーバのログ見れるし。。
#             return JvLinkResponse(array=[], columns=(), error=e)
#
#         else:
#             return JvLinkResponse(array=array_2, columns=self.columns)
#
#     def __get_record_format(self) -> DataFrame:
#         return self._key.get_items(
#             self.condition.option,
#             self.condition.data_key,
#             self.condition.record_key
#         )
#
#     def _get_columns(self) -> tuple:
#         return tuple([
#             items[self._key.data_record_key.colum_name]
#             for index, items in self.__get_record_format().iterrows()
#         ])
#
#     def _get_slices(self) -> tuple:
#         return tuple([
#             (items[self._key.data_record_key.delimiter_position],
#              items[self._key.data_record_key.byte_length])
#             for index, items in self.__get_record_format().iterrows()
#         ])
#
#     def __slicer(self, data: str) -> list:  # 2次元配列
#         '''
#         :return list: shift-jis-2004で、encodeされたdataを、適切な区切り文字で、区切り、
#                       dataを、shift-jis-2004で、decodeし、リストに詰めて2次元配列として返す。
#         '''
#
#         character_code = "shift-jis-2004"
#         byte_data = self.__to_bytes(data, self.condition.terminal_position, encoding=character_code)
#         # byte_data:   仕様上、必ずshift-jisでエンコードされたデータでなければ、適切にスライスできない。
#         #              下記、len()を使った結果
#         #              python str (Unicode)    1133    ×
#         #              エンコード    utf-8      1411    ×
#         #              エンコード  shift-jis    1272    ○  --> 実際は、shift-jis-2004でエンコードしている。
#
#         return [byte_data[i:i + j].decode(encoding=character_code).replace('\u3000', "").replace(' ', "")
#                 for i, j in self.slices
#                 ]
#
#     @staticmethod
#     def __to_bytes(data: str, terminal_position, encoding="shift-jis-2004") -> bytes:
#
#         # https://sardonyx.exblog.jp/1023830/
#         __replace_unicode_map = (
#             ("\uff0d", "\u2212"),
#             ("\uff5e", "\u301c"),
#             ("\u9ad9", "\u9ad8"),  # 髙 --> 高
#             ("\ue003", "oo")  # --> oo
#         )
#
#         # 置換マップを使い、「~」(半角チルダのエンコードが、2byteになってしまう問題を強引に対応する。（本来1byteの想定。)
#         # ただし、encode前「~」から、encode -> decode後「‾」に、文字が変わってしまう。
#         # 使わないカラムで、発生したバグで、一旦暫定対応で進める。
#         __replace_byte_map = ((b'\x81\xb0', b'~'),)
#
#         # encodingでshift-jis-2004を使う理由。
#         # shift-jis =< shift-jis-2004 (上位互換)
#         # 「橳」が、shift-jisでエンコードできなかったので、shift-ji-2004を指定する。
#         # https://qiita.com/tyochiai/items/9f9d2f50ef771d27f180
#         try:
#             byte_data = bytes(data, encoding=encoding)
#
#         except UnicodeEncodeError:
#             logger.debug(f'UnicodeEncodeError発生。 "unicode_data": "{data}"')
#             for before, after in __replace_unicode_map:
#                 data = data.replace(before, after)
#
#             # 失敗すると、UnicodeEncodeErrorが発生する。（置換対応していない場合に発生）
#             byte_data = bytes(data, encoding=encoding)
#
#         try:
#             # 長さチェックなので、-1と一致するか確認する。
#             # ex...１から始まり1273データが終わるので、1272が、実際の長さになる。
#             assert byte_data.__len__() == terminal_position - 1
#
#         except AssertionError:
#             for before, after in __replace_byte_map:
#                 logger.debug(f'AssertionError発生。 "unicode_data"": "{data}", "byte_data": "{byte_data}"')
#                 # shift-jis-2004は、「~」(半角チルダのエンコードが、2byteになってしまう。本来1byteの想定。)
#                 # 「~」が、2byteにエンコードされた場合、1byteの「b'~'」に強引に置換して、対応する。
#                 byte_data = byte_data.replace(before, after)
#
#                 # それでも、ダメなら、一旦落ちてください。
#                 assert byte_data.__len__() == terminal_position - 1, logger.error(
#                     f"'unicode_data'': '{data}', 'byte_data': '{byte_data}'")
#
#         return byte_data
#
#     # 以下、Set Up 用のメソッド。
#     @staticmethod
#     def chunk_iterator(iterator, n: int):
#         # TODO n<1を設定された時の対応。
#         # iterator自体を、chunk処理します。
#         # これにより、巨大データを一定のリソースで処理可能。
#
#         iter = iterator
#         exhausted = [False]  # レキシカルスコープ
#
#         def subiter(it, n, exhausted):
#             for i in range(n):
#                 try:
#                     yield next(it)
#                 except StopIteration:
#                     exhausted[0] = True
#
#         # subiterをくるくる回す。
#         while not exhausted[0]:
#             yield subiter(iter, n, exhausted)
#
#     @execution_time
#     def setup(self, lines=50000) -> None:
#         '''
#         iter_readerを、chunk(5万行ごと)で、読み取り、
#         csvとしてファイル書き込みするメソッド。
#         '''
#         try:
#             count = 1
#             for chunk_iter in self.chunk_iterator(iterator=self.delegator(), n=lines):
#                 def chunk_writer():
#                     array_2 = [self.__slicer(data=x) for x in chunk_iter]
#                     df = pd.DataFrame(array_2, columns=self.columns)
#                     df = df.drop(self.columns[-1], axis=1)  # 「レコード区切り」カラムを削除
#                     df.to_csv(f"./data/{self.condition}_{count}.csv", encoding="utf-8", index=False)
#
#                 chunk_writer()
#                 count += 1
#
#         except (JvLinkError, UnicodeEncodeError) as e:
#             self.error(e)
#             logger.error("書き込みが失敗しました。")
#             raise  # Error再送
#
#         else:
#             logger.info(f"./data/ の中に、.csvファイルとして、書き込み完了しました。")
#
#
# class JvLinkResponse(object):
#
#     def __init__(self, array: list, columns: tuple, error=None):
#         self.__array_2 = array
#         self.__columns = columns
#         self.__error = error
#
#         # response to front
#         self.msg = {
#             "status" : "Error",
#             "message": "データ取得に失敗しました。",
#             "headers": [],
#             "items"  : {}
#         }
#
#     @property
#     def array_2(self):
#         return self.__array_2
#
#     @property
#     def columns(self):
#         return self.__columns
#
#     @property
#     def error(self):
#         return self.__error
#
#     def is_error(self):
#         if self.error is None:
#             return False
#         else:
#             return True
#
#     def msg_builder(self):
#         if self.is_error():
#             pass
#         else:
#             self.msg["status"] = "OK"
#             self.msg["message"] = "正常にデータを取得いたしました。"
#             self.msg["headers"] = [{"text": x, "value": x} for x in self.columns]
#             self.msg["items"] = self.to_dict()
#
#         return self.msg
#
#     def to_jsonify(self):
#         return jsonify(self.msg_builder())
#
#     def to_dataframe(self):
#         return pd.DataFrame(self.array_2, columns=self.columns)
#
#     def to_dict(self):
#         return self.to_dataframe().to_dict(orient="records")
#
#     def to_csv(self, path):
#         return self.to_dataframe().to_csv(path, encoding="utf-8", index=False)
#
# # # decorator
# # def shift_jis_encode(func):
# #     # https://sardonyx.exblog.jp/1023830/
# #     __replace_map = (("\uff0d", "\u2212"), ("\uff5e", "\u301c"))
# #
# #     # shift-jis =< shift-jis-2004 (上位互換)
# #     # 「橳」が、shift-jisでエンコードできなかったので、shift-ji-2004を指定する。
# #     # https://qiita.com/tyochiai/items/9f9d2f50ef771d27f180
# #     @wraps(func)
# #     def __decorator(*args, **kwargs):
# #         try:
# #             kwargs = bytes(kwargs["data"], encoding="shift-jis-2004")
# #         except UnicodeEncodeError as e:
# #             logger.debug(f'対応しているUnicodeEncodeError: {kwargs["data"]}')
# #             for before, after in __replace_map:
# #                 kwargs["data"] = kwargs["data"].replace(before, after)
# #             kwargs = bytes(kwargs["data"], encoding="shift-jis-2004")
# #         return func(*args, kwargs)
# #
# #     return __decorator
