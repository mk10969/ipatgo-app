### Jvlink Option 仕様
# / ** 通常データ * /
# STANDARD("通常データ", 1),
#
# / ** 今週データ * /
# THIS_WEEK("今週データ", 2),
#
# / ** ダイアログ有りセットアップデータ * /
# SETUP_WITH_DIALOG("ダイアログ有りセットアップデータ", 3),
#
# / ** ダイアログ無しセットアップ * /
# SETUP_WITHOUT_DIALOG("ダイアログ無しセットアップ", 4);


class OpenCondition(object):
    __min = 0
    __max = 4
    __len_2 = 2
    __len_4 = 4
    
    def __init__(self, option, data_key, record_key, terminal_position):
        if not isinstance(option, int):
            raise TypeError(f"アトリビュート {option} には、int型でセットしてください。")
        if not self.__min <= option <= self.__max:
            raise ValueError(f"アトリビュート {option}は、{self.__min}から、{self.__max}の範囲で指定してください。")
        
        if not isinstance(data_key, str):
            raise TypeError(f"アトリビュート {data_key} には、str型でセットしてください。")
        if not data_key:
            raise ValueError(f"アトリビュート {data_key}は、nullではいけません。")
        if not len(data_key) == self.__len_4:
            raise ValueError(f'アトリビュート {data_key} の長さは {self.__len_4} です。')
        
        if not isinstance(record_key, str):
            raise TypeError(f"アトリビュート {record_key} には、str型でセットしてください。")
        if not record_key:
            raise ValueError(f"アトリビュート {record_key}は、nullではいけません。")
        if not len(record_key) == self.__len_2:
            raise ValueError(f'アトリビュート {record_key} の長さは {self.__len_2} です。')
        
        if not isinstance(terminal_position, int):
            raise TypeError(f'アトリビュート {terminal_position} には、int型でセットしてください。')
        
        self.__option = option
        self.__data_key = data_key
        self.__record_key = record_key
        self.__terminal_position = terminal_position
    
    @property
    def option(self):
        return self.__option
    
    @property
    def data_key(self):
        return self.__data_key
    
    @property
    def record_key(self):
        return self.__record_key
    
    @property
    def terminal_position(self):
        return self.__terminal_position
    
    def __repr__(self):
        return f"{self.data_key.lower()}_{self.record_key.lower()}"


class StoreOpenCondition(OpenCondition):
    def __init__(self, option, data_key, record_key, terminal_position):
        super().__init__(
            option=option,
            data_key=data_key,
            record_key=record_key,
            terminal_position=terminal_position
        )


class RealTimeOpenCondition(OpenCondition):
    def __init__(self, option, data_key, record_key, terminal_position):
        super().__init__(
            option=option,
            data_key=data_key,
            record_key=record_key,
            terminal_position=terminal_position
        )


def create_condition(key_manage, option, data_key, record_key):
    if option == 0:
        return RealTimeOpenCondition(
            option,
            data_key,
            record_key,
            key_manage.get_terminal_position(option, data_key, record_key)
        )
    
    else:
        return StoreOpenCondition(
            option,
            data_key,
            record_key,
            key_manage.get_terminal_position(option, data_key, record_key)
        )


if __name__ == '__main__':
    a = RealTimeOpenCondition(0, "RACE", "RA", 111)
    print(a)
    aa = StoreOpenCondition(1, "aaaa", "aa", 111)
    print(aa)
    