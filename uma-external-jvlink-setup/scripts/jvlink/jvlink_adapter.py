import threading

import pythoncom
import win32com.client

from .jvlink_result_factory import JvLinkResultFactory

'''
外部モジュールのJV-Linkを呼び出すクラスです。
JV-Linkの仕様に沿ったメソッドを実装しています。
（一部利用しないメソッドは、空の実装をしています。(ウソ)）
'''


class JvLinkAdapter(object):
    
    def __init__(self):
        # https://www.blackcat.xyz/article.php/ProgramingFAQ_del0060
        # マルチスレッドにおいて、初期化メソッドを呼ぶ必要がある。
        if threading.current_thread() is not threading.main_thread():
            pythoncom.CoInitialize()
        self.__jvlink = win32com.client.Dispatch("JVDTLab.JVLink.1")
    
    def __del__(self):
        # マルチスレッドのみ、対応する。
        if threading.current_thread() is not threading.main_thread():
            pythoncom.CoUninitialize()
    
    def JVInit(self, sid):
        result = self.__jvlink.JVInit(sid)
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVSetUIProperties(self):
        result = self.__jvlink.JVSetUIProperties()
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVSetServiceKey(self, serviceKey):
        result = self.__jvlink.JVSetServiceKey(serviceKey)
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVSetSaveFlag(self, savaflag=1):
        '''
        :param savaflag: savaflag=1をデフォルト。保存する。
        :return:
        '''
        result = self.__jvlink.JVSetSaveFlag(savaflag)
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVSetSavePath(self, savepath):
        '''
        :param savepath:
        :return:
        '''
        result = self.__jvlink.JVSetSavePath(savepath)
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVOpen(self, dataspec, fromtime, option):
        '''
        :param dataspec:読み出したいデータを識別するデータ種別ＩＤを文字列として連結したものを指定する。
        :param fromtime:dataspecに指定したデータの読出し開始ポイントを時刻（YYYYMMDDhhmmss の形式）で指定する。
                        YYYYMMDDhhmmss=< time =< datetime.now()
        :param option:  option = 1 通常データ
                        option = 2 今週データ
                        option = 3 セットアップデータ
                        option = 4 ダイアログ無しセットアップデータ
        :return:Jvlink_resultオブジェクトを返す。
        '''
        
        result = self.__jvlink.JVOpen(dataspec, fromtime, option)
        return JvLinkResultFactory.create_JvOpen_result(result)
    
    def JVRTOpen(self, dataspec, key):
        '''
        :param dataspec:読み出したいデータを識別するデータ種別ＩＤを文字列として指定する。
                        １つのデータ種別ＩＤしか指定できないので４桁固定となる。
        :param key:     該当データを取得するための要求キーを指定する。
                        “YYYYMMDDJJKKHHRR”, “YYYYMMDDJJRR”, または、“YYYYMMDD”
                        YYYY：開催年、MM ：開催月、DD ：開催日、JJ ：場コード、KK ：回次、HH ：日次、RR ：レース番号
        :return:Jvlink_resultオブジェクトを返す。
        '''
        
        result = self.__jvlink.JVRTOpen(dataspec, key)
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVRead(self, size):
        '''
        :param size:バイトサイズを指定する。
        :return:Jvlink_resultオブジェクトを返す。
        '''
        
        buff, filename = bytes(), ""
        result = self.__jvlink.JVRead(buff, size, filename)
        return JvLinkResultFactory.create_JvRead_result(result)
    
    def JVClose(self):
        result = self.__jvlink.JVClose()
        return JvLinkResultFactory.create_JvLink_result(result)
    
    def JVStatus(self):
        result = self.__jvlink.JVStatus()
        return JvLinkResultFactory.create_JvLink_result(result)
    
    # 実装不可能
    # def JVGets(self, size):
    #     '''
    #     :param size:バイトサイズを指定する。
    #     :return:Jvlink_resultオブジェクトを返す。
    #     '''
    #     # byte配列のポインタが、Jvlink_resultオブジェクトのdataに格納される。
    #     # それは、明示的にdel する必要がある。
    #
    #     buff, filename = bytes(), ""
    #     result = self.__jvlink.JVGets(buff, size, filename)
    #     return JvLinkResultFactory.create_JvRead_result(result)
    
    def JVSkip(self):
        self.__jvlink.JVSkip()
    
    def JVCancel(self):
        self.__jvlink.JVCancel()
    
    def JVCourseFile(self, key):
        # result = self.jvlink.JVCourseFile(key)
        pass
