from enum import Enum, unique

from ..jvlink_result_factory import JvLinkResult


@unique
class JvLinkErrorCode(Enum):
    _100 = ("パラメータが不正あるいはレジストリへの保存に失敗", -100)
    _101 = ("sidが設定されていない", -101)
    _102 = ("sidが64byteを超えている", -102)
    _103 = ("sidが不正（sidの１桁目がスペース）", -103)
    _111 = ("dataspec もしくは movietypeパラメータが不正", -111)
    _112 = ("fromtimeパラメータが不正", -112)
    _114 = ("key もしくは searchkeyパラメータが不正", -114)
    _115 = ("optionパラメータが不正", -115)
    _116 = ("dataspecとoptionの組み合わせが不正", -116)
    _118 = ("filepathパラメータが不正", -118)
    _201 = ("JVInitが行われていない", -201)
    _202 = ("前回のJVOpen/JVRTOpen/JVMVOpenに対してJVCloseが呼ばれていない（オープン中）", -202)
    _203 = ("JVOpen/JVMVOpenが行なわれていない", -203)
    _211 = ("レジストリ内容が不正（レジストリ内容が不正に変更された）", -211)
    _212 = ("レジストリ内容が不正（レジストリ内容が不正に変更された）", -212)
    _301 = ("認証エラー", -301)
    _302 = ("サービスキーの有効期限切れ", -302)
    _303 = ("サービスキーが設定されていない（サービスキーが空値）", -303)
    _304 = ("JRAレーシングビュアー連携機能認証エラー", -304)
    _401 = ("JV-Link内部エラー", -401)
    _402 = ("ダウンロードしたファイルが異常（ファイルサイズ=0）", -402)
    _403 = ("ダウンロードしたファイルが異常（データ内容）", -403)
    _411 = ("サーバーエラー（HTTPステータス404 NotFound）", -411)
    _412 = ("サーバーエラー（HTTPステータス403 Forbidden）", -412)
    _413 = ("サーバーエラー（HTTPステータス200,403,404以外）", -413)
    _421 = ("サーバーエラー（サーバーの応答が不正）", -421)
    _431 = ("サーバーエラー（サーバーアプリケーション内部エラー）", -431)
    _501 = ("セットアップ処理においてCD-ROMが無効", -501)
    _502 = ("ダウンロード失敗（通信エラーやディスクエラーなど）", -502)
    _503 = ("ファイルが見つからない", -503)
    _504 = ("サーバーメンテナンス中", -504)
    
    def get_message(self):
        return self.value[0]
    
    def get_error_code(self):
        return self.value[1]
    
    def is_same_error_code(self, result: JvLinkResult) -> bool:
        return result.return_code == self.get_error_code()

# if __name__ == '__main__':
#     for m in JvLinkErrorCode:
#         print(m)
#     from src.main.jvlink.jvlink_result_factory import JvLinkResultFactory
#     res = (-201)
#     result = JvLinkResultFactory.create_JvLink_result(res)
#     print(JvLinkErrorCode._201.is_same_error_code(result))
