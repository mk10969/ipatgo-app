# マルチスレッドの場合、threading.Lockをかける
import threading


class SingletonType(type):
    __instance = None
    __lock = threading.Lock()

    def __call__(cls, *args, **kwargs):
        with cls.__lock:
            if cls.__instance is None:
                cls.__instance = super(SingletonType, cls).__call__(*args, **kwargs)

        return cls.__instance


# class SingletonType(type):
#     __instance = None
#
#     def __call__(cls, *args, **kwargs):
#         if cls.__instance is None:
#             cls.__instance = super(SingletonType, cls).__call__(*args, **kwargs)
#
#         return cls.__instance
