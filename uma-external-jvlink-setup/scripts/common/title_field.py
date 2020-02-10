class FieldString(object):
    def __init__(self, length):
        self._len = length

    def __set_name__(self, owner, name):
        self._name = name

    def __get__(self, instance, owner):
        if instance is None:
            return self
        return instance.__dict__[self._name]

    def __set__(self, instance, value):
        if not isinstance(value, str):
            raise TypeError(f'アトリビュート {self._name} には文字列のみがセットできます。')
        if not value:
            raise ValueError(f"アトリビュート {self._name}は、nullではいけません。")
        if len(value) > self._len:
            raise ValueError(f'アトリビュート {self._name} の最大長さは {self._len} です。')
        instance.__dict__[self._name] = value

    def __delete__(self, instance):
        # 削除するのは、_nameであって、_lenではないため、実装している。
        del instance.__dict__[self._name]


class FieldIntegerMinBetweenMax(object):
    def __init__(self, min, max):
        self._min = min
        self._max = max

    def __set_name__(self, owner, name):
        self._value = name

    def __get__(self, instance, owner):
        if instance is None:
            return self
        return instance.__dict__[self._value]

    def __set__(self, instance, value):
        if not isinstance(value, int):
            raise TypeError(f'アトリビュート {self._value} には、intもしくは、booleanがセットできます。')
        if not self._min <= value <= self._max:
            raise ValueError(f'アトリビュート {self._value} は、 {self._min}から{self._max}の範囲です。')
        instance.__dict__[self._value] = value

    def __delete__(self, instance):
        del instance.__dict__[self._value]


class FieldInteger(object):
    def __init__(self):
        pass

    def __set_name__(self, owner, name):
        self._value = name

    def __get__(self, instance, owner):
        if instance is None:
            return self
        return instance.__dict__[self._value]

    def __set__(self, instance, value):
        if not isinstance(value, int):
            raise TypeError(f'アトリビュート {self._value} には、intもしくは、booleanがセットできます。')
        instance.__dict__[self._value] = value

    def __delete__(self, instance):
        del instance.__dict__[self._value]


if __name__ == '__main__':
    pass

# class Meta(type):
#     def __new__(cls, name, bases, class_dict):
#         for key, value in class_dict.items():
#             # クラスのインスタンスに紐づける
#             if isinstance(value, Field):
#                 value.name = key
#         return type.__new__(cls, name, bases, class_dict)

# class Field(object):
#     def __init__(self):
#         self.name = None
#
#     def __get__(self, instance, owner):
#         if instance is None:
#             return self
#         return getattr(instance, self.name, 0)
#
#     def __set__(self, instance, value):
#         setattr(instance, self.name, value)

