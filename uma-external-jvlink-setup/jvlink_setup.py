from functools import wraps
from logging import getLogger
from pathlib import Path

from scripts.jvlink.jvlink_main_setup import JvLinkSetup

logger = getLogger(__name__)

data_dir = Path(Path(__name__).parent, "data")
if not data_dir.exists():
    data_dir.mkdir()


def logging(func):
    @wraps(func)
    def __decorator(*arg, **kwargs):
        logger.info(f"{arg[0]}条件の、Set upを開始します。")
        result = func(*arg, **kwargs)
        logger.info(f"{arg[0]}条件の、Set upが完了しました。")
        return result
    
    return __decorator


@logging
def setup(condition: tuple):
    option, data_key, record_key = condition
    file_path = f'{data_dir}/{data_key}_{record_key}.txt'
    jv = JvLinkSetup(option, data_key, record_key)
    jv.write(file_path=file_path)


def race_info_setup():
    race_info_lists = [
        (4, 'RACE', 'RA'),  # 1986年以降のレース番組の詳細情報(中央・地方・海外)
        (4, 'RACE', 'SE'),  # 1986年以降の競争馬毎のレース情報(中央・地方・海外)
        (4, 'RACE', 'HR'),  # 1986年以降の払戻金情報
    ]
    for condition in race_info_lists:
        setup(condition)


def race_info2_setup():
    race_info2_lists = [
        # (4, 'RACE', 'H1'),  # 1986年以降の確定票数(3連単以外)
        # (4, 'RACE', 'H6'),  # 2004年8月以降の確定票数(3連単)
        # (4, 'RACE', 'O1'),  # 1993年6月以降の確定オッズ(単複枠)
        # (4, 'RACE', 'O2'),  # 1993年6月以降の確定オッズ(馬連)
        # (4, 'RACE', 'O3'),  # 1999年10月以降の確定オッズ(ワイド)
        # (4, 'RACE', 'O4'),  # 2002年6月以降の確定オッズ(馬単)
        # (4, 'RACE', 'O5'),  # 2002年6月以降の確定オッズ(3連複)
        # (4, 'RACE', 'O6'),  # 2004年8月以降の確定オッズ(3連単)
        # ↓こいつ設定にないかも
        # (4, 'RACE', 'WF'),  # 2011年4月以降の重勝式(WIN5)に関する情報
        (4, 'RACE', 'JG'),  # 2011年6月以降の競走馬除外情報に関する情報
    ]
    for condition in race_info2_lists:
        setup(condition)


def master_info_setup():
    master_info_lists = [
        (4, 'DIFF', 'UM'),  # 1986年以降の競走馬の基本情報
        (4, 'DIFF', 'KS'),  # 騎手マスタ全件
        (4, 'DIFF', 'CH'),  # 調教師マスタ全件
        (4, 'DIFF', 'BR'),  # 1986年以降の生産者情報
        (4, 'DIFF', 'BN'),  # 1986年以降の馬主情報
        # (4, 'DIFF', 'RC'),  # 1986年以降のコースレコード履歴、2003年以降のG1レコード履歴
    ]
    for condition in master_info_lists:
        setup(condition)


def blood_info_setup():
    blood_info_lists = [
        (4, 'BLOD', 'HN'),  # 1986年以降の繁殖馬情報
        (4, 'BLOD', 'SK'),  # 1986年以降の産駒情報
        (4, 'BLOD', 'BT'),  # 血統情報全件
    ]
    for condition in blood_info_lists:
        setup(condition)


def other_info_setup():
    other_info_lists = [
        # (4, 'SNAP', 'CK'),  # 2004年以降の出走別着度数情報
        # (4, 'SLOP', 'HC'),  # 2003年以降の坂路調教情報
        (4, 'HOSE', 'HS'),  # 1997年以降の競走馬市場取引価格情報
        # (4, 'HOYU', 'HY'),  # 2000年産駒以降の馬名の意味由来情報
        (4, 'COMM', 'CS'),  # コース情報全件
    ]
    for condition in other_info_lists:
        setup(condition)


if __name__ == '__main__':
    ### RUN SETUP ###
    print("start setup")
    # race_info_setup()
    # blood_info_setup()
    # master_info_setup()
    # race_info2_setup()
    # other_info_setup()
