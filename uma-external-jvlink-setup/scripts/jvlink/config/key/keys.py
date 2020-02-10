from dataclasses import dataclass


@dataclass(frozen=True)
class DataRecordKey:
    option: str
    data: str
    record: str
    colum_name: str
    delimiter_position: str
    byte_length: str
    
    def get_columns(self):
        return [
            self.option, self.data, self.record,
            self.colum_name, self.delimiter_position, self.byte_length
        ]


@dataclass(frozen=True)
class ConverterKey:
    # 少ないメモリで、インスタンスを作成できる。
    __slots__ = ["table_name", "code", "sense", "description"]
    table_name: str
    code: str
    sense: str
    description: str
    
    def get_columns(self):
        return [self.table_name, self.code, self.sense, self.description]


if __name__ == '__main__':
    # intでインスタンスが生成されるのね。。。
    a = ConverterKey(1, "b", "c", "d")
    
    print(a.get_columns())
    print(type(a.table_name))
