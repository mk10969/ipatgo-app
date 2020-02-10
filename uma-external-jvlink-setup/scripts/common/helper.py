import os
import glob
import re
from datetime import datetime
from logging import getLogger
from functools import wraps


logger = getLogger("jvlink")


# TODO log Rotation Script


def deco_NotNone(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        for index, arg in enumerate(args):
            if arg is None:
                raise ValueError(f"{index}番目の引数が、Nullじゃボケ")
        return func(*args, **kwargs)
    return wrapper


def execution_time(func):
    @wraps(func)
    def __decorator(*arg, **kwargs):
        stat = datetime.now()
        result = func(*arg, **kwargs)
        end = datetime.now()
        logger.info({"実行関数": func.__name__, "実行時間": end - stat})
        return result
    
    return __decorator


# def validator(input, format="YYYYMMDD"):
#     def __validator(func):
#         @wraps(func)
#         def __decorator(*arg, **kwargs):
#
#             if format == "YYYYMMDD":
#                 pattern = '^[0-9]{8}$'
#             elif format == "YYYYMMDDJJRR":
#                 pattern = '^[0-9]{12}$'
#             else:
#                 raise AttributeError(f"{format}が、アカン！！！！")
#
#             if re.match(pattern, input):
#                 result = func(*arg, **kwargs)
#             else:
#                 # 400エラーが正しいか。。
#                 raise Exception("アカン")
#             return result
#
#         return __decorator
#     return __validator
#
#
# @validator("20190411", format="YYYYMMDD")
# def main():
#     print("main run")

# if __name__ == '__main__':
#     aa = [("", "", ]