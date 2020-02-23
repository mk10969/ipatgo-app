from dataclasses import dataclass
from pathlib import Path

import pandas as pd
from pandas import DataFrame

from .keys import ConverterKey, DataRecordKey
from .utils import SingletonType


class KeyManage(object, metaclass=SingletonType):
    
    def __init__(self):
        self.__data_record_key = DataRecordKey(
            "オプション", "データ種別", "レコード種別",
            "カラム名", "位置", "長さ"
        )
        self.__converter_key = ConverterKey(
            "テーブル名", "コード", "意味", "説明"
        )
        
        pwd = Path(__file__).parent
        self.__data_df = self._read_csv(Path(pwd, "master_key/data_key.csv"))
        self.__record_df = self._read_csv(Path(pwd, "master_key/record_key.csv"))
        converter_df = self._read_csv(Path(pwd, "master_key/converter_key.csv"))
        
        merged_df = self._merge(
            lhs_df=self.data_df,
            rhs_df=self.record_df,
            key_col=self.data_record_key.record,
            condition='inner'
        )
        self._data_record_df = merged_df.set_index(
            [self.data_record_key.option,
             self.data_record_key.data,
             self.data_record_key.record],
            drop=True
        )
        self._converter_df = converter_df.set_index(
            [self.converter_key.table_name,
             self.converter_key.code],
            drop=True
        )
        
        # levelは、MultiIndexの指定されたIndexの階層の値でソート
        self._data_record_df.sort_index(level=0, inplace=True)
        self._converter_df.sort_index(level=0, inplace=True)
        
        # 欠損データ(nan)を、設定する関数 fillna()
        # df.fillna({'name': 'XXX', 'age': 20, 'point': 0})
        # self._data_record_df = self._data_record_df.fillna(
        #     {self.data_record_key.parent_index: 0,
        #      self.data_record_key.child_item  : "",
        #      self.data_record_key.child_index : 0}
        # )
    
    @property
    def data_record_df(self) -> DataFrame:
        return self._data_record_df
    
    @property
    def converter_df(self) -> DataFrame:
        return self._converter_df
    
    @property
    def data_record_key(self) -> dataclass:
        return self.__data_record_key
    
    @property
    def converter_key(self) -> dataclass:
        return self.__converter_key
    
    @staticmethod
    def _read_csv(path):
        return pd.read_csv(path, encoding="utf-8")
    
    @staticmethod
    def _merge(lhs_df, rhs_df, key_col, condition):
        '''
        pd.merge()メソッドの引数を、明確にするために関数を作成。
        :param lhs_df: left df
        :param rhs_df: right df
        :param key_col: mergeするカラム名
        :param condition: default='inner', 'left', 'right', 'outer'
        :return:
        '''
        return pd.merge(lhs_df, rhs_df, on=key_col, how=condition)
    
    @staticmethod
    def tail(lis, num=1):
        return lis[-num:]
    
    @staticmethod
    def head(lis, num=1):
        return lis[:num]
    
    def get_terminal_position(self, option, data_key, record_key) -> int:
        # TODO: ↓クソコードどうにかならんかね。、、intにキャストせなあかんねん。
        # ex.. RAは、1から始まり、1273で、終わる。なので、1273 byte sizeを用意してあげる必要がある。
        # (だから、+ 3している。。)
        # だたし、データの長さは、1272byte lengthである、、、というややこしい。。
        return int(self.tail(
            self.data_record_df.at[
                (option, data_key, record_key),
                self.data_record_key.delimiter_position
            ]
        )) + 3
    
    def get_items(self, option, data_key, record_key) -> DataFrame:
        return self.data_record_df.loc[
            (option, data_key, record_key),
            (self.data_record_key.colum_name,
             self.data_record_key.delimiter_position,
             self.data_record_key.byte_length)
        ]
    
    def get_data_record_columns(self) -> list:
        return self.data_record_key.get_columns()
    
    def get_converter_columns(self) -> list:
        return self.converter_key.get_columns()
    
    @property
    def data_df(self) -> DataFrame:
        return self.__data_df
    
    @property
    def record_df(self) -> DataFrame:
        return self.__record_df
    
    def get_multi_index_all(self):
        return [
            (items[self.data_record_key.option],
             items[self.data_record_key.data],
             items[self.data_record_key.record])
            for index, items in self.data_df.iterrows()
        ]
    
    def get_multi_index_filter(self, n=1):
        ok_list = [0, 1, 2, 4]
        if n in ok_list:
            return [
                (i, j, k)
                for i, j, k in self.get_multi_index_all()
                if i == n
            ]
        else:
            raise AttributeError(f"引数は、{ok_list}から、選択してください。")


if __name__ == '__main__':
    pass
    
    # key._data_record.to_csv("完成版.csv")
    
    ### 下記ボツ。
    # pwd = Path(__file__).parent
    # record_df = pd.read_csv(Path(pwd, "record_key.csv"), encoding="utf-8")
    # record_df = record_df.set_index("レコード種別")
    # record_df_sorted = record_df.sort_index()
    # #    print(record_df.at["RA", "位置"])
    # # print(record_df_sorted)
    #
    # kaburinasi = set(record_df_sorted.index)
    # sorted_key = sorted(kaburinasi)
    # once_list = []
    # for key in sorted_key:
    #     lis = record_df.at[key, "位置"]
    #     lis = list(lis)  # キャスト
    #     lis.append(lis[-1] + 2)
    #     # print(lis)
    #     once_list.extend(lis[1:])
    # # print(len(once_list))
    #
    # record_df_sorted["new位置"] = once_list
    # print(record_df_sorted)
    #
    # record_df_sorted.to_csv(Path(pwd, "record_key_new.csv"), encoding="utf-8")
    #
    # pwd = Path(__file__).parent
    # print(pwd)
    # record_df = pd.read_csv(Path(pwd, "record_key.csv"), encoding="utf-8")
    # record_df = record_df.set_index("レコード種別")
    # print(set(record_df.index).__len__())
    #
    # once_list = []
    # pre_key = ""
    # for key in record_df.index:
    #     if key != pre_key:
    #         lis = record_df.at[key, "位置"]
    #         lis = list(lis)  # キャスト
    #         lis.append(lis[-1] + 2)
    #         once_list.extend(lis[1:])
    #     pre_key = key
    #
    # print(once_list.__len__())
    # record_df["new位置"] = once_list
    # print(record_df)
    #
    # record_df.to_csv(Path(pwd, "record_key_new.csv"), encoding="utf-8")

# http://sinhrks.hatenablog.com/entry/2014/11/27/232150
# ↑これで、カラムの一括変更ができるね。。

# In [25]: %timeit df.loc[('a', 'A'), ('c', 'C')]
# 10000 loops, best of 3: 187 µs per loop
#
# In [26]: %timeit df.at[('a', 'A'), ('c', 'C')]
# 100000 loops, best of 3: 8.33 µs per loop
#
# In [35]: %timeit df.get_value(('a', 'A'), ('c', 'C'))
# 100000 loops, best of 3: 3.62 µs per loop
#
# FutureWarning: get_value is deprecated and will be removed in a future release.
# Please use .at[] or .iat[] accessors instead
