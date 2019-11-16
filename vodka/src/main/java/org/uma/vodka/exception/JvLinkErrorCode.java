package org.uma.vodka.exception;

import org.uma.vodka.common.constants.CodeEnum;


public enum JvLinkErrorCode implements CodeEnum<Integer, JvLinkErrorCode> {
    /**
     * -1: Open時、該当データなし。
     */
    _1(-1, "該当データなし"),
    /**
     * -100:パラメータが不正あるいはレジストリへの保存に失敗
     */
    _100(-100, "パラメータが不正あるいはレジストリへの保存に失敗"),
    /**
     * -101:sidが設定されていない
     */
    _101(-101, "sidが設定されていない"),
    /**
     * -102:sidが64byteを超えている
     */
    _102(-102, "sidが64byteを超えている"),
    /**
     * -103:sidが不正（sidの１桁目がスペース）
     */
    _103(-103, "sidが不正（sidの１桁目がスペース）"),
    /**
     * -111:dataspec もしくは movietypeパラメータが不正
     */
    _111(-111, "dataspec もしくは movietypeパラメータが不正"),
    /**
     * -112:fromtimeパラメータが不正
     */
    _112(-112, "fromtimeパラメータが不正"),
    /**
     * -114:key もしくは searchkeyパラメータが不正
     */
    _114(-114, "key もしくは searchkeyパラメータが不正"),
    /**
     * -115:optionパラメータが不正
     */
    _115(-115, "optionパラメータが不正"),
    /**
     * -116:dataspecとoptionの組み合わせが不正
     */
    _116(-116, "dataspecとoptionの組み合わせが不正"),
    /**
     * -118:filepathパラメータが不正
     */
    _118(-118, "filepathパラメータが不正"),
    /**
     * -201:JVInitなわれていない
     */
    _201(-201, "JVInitなわれていない"),
    /**
     * -202:前回のJVOpen/JVRTOpen/JVMVOpenに対してJVCloseが呼ばれていない（オープン中）
     */
    _202(-202, "前回のJVOpen/JVRTOpen/JVMVOpenに対してJVCloseが呼ばれていない（オープン中）"),
    /**
     * -203:JVOpen/JVMVOpenが行なわれていない
     */
    _203(-203, "JVOpen/JVMVOpenが行なわれていない"),
    /**
     * -211:レジストリ内容が不正（レジストリ内容が不正に変更された）
     */
    _211(-211, "レジストリ内容が不正（レジストリ内容が不正に変更された）"),
    /**
     * -212:レジストリ内容が不正（レジストリ内容が不正に変更された）
     */
    _212(-212, "レジストリ内容が不正（レジストリ内容が不正に変更された）"),
    /**
     * -301:認証エラー
     */
    _301(-301, "認証エラー"),
    /**
     * -302:サービスキーの有効期限切れ
     */
    _302(-302, "サービスキーの有効期限切れ"),
    /**
     * -303:サービスキーが設定されていない（サービスキーが空値）
     */
    _303(-303, "サービスキーが設定されていない（サービスキーが空値）"),
    /**
     * 304):JRAレーシングビュアー連携機能認証エラー"
     */
    _304(-304, "JRAレーシングビュアー連携機能認証エラー"),
    /**
     * -401:JV-Link内部エラー
     */
    _401(-401, "JV-Link内部エラー"),
    /**
     * -402:ダウンロードしたファイルが異常（ファイルサイズ=0）
     */
    _402(-402, "ダウンロードしたファイルが異常（ファイルサイズ=0）"),
    /**
     * -403:ダウンロードしたファイルが異常（データ内容）
     */
    _403(-403, "ダウンロードしたファイルが異常（データ内容）"),
    /**
     * -411:サーバーエラー（HTTPステータス404 NotFound）
     */
    _411(-411, "サーバーエラー（HTTPステータス404 NotFound）"),
    /**
     * -412:サーバーエラー（HTTPステータス403 Forbidden）
     */
    _412(-412, "サーバーエラー（HTTPステータス403 Forbidden）"),
    /**
     * -413:サーバーエラー（HTTPステータス200,403,404以外）
     */
    _413(-413, "サーバーエラー（HTTPステータス200,403,404以外）、ネットワーク接続エラー。"),
    /**
     * -421:サーバーエラー（サーバーの応答が不正）
     */
    _421(-421, "サーバーエラー（サーバーの応答が不正）"),
    /**
     * -431:サーバーエラー（サーバーアプリケーション内部エラー）
     */
    _431(-431, "サーバーエラー（サーバーアプリケーション内部エラー）"),
    /**
     * -501:セットアップ処理においてCD-ROMが無効
     */
    _501(-501, "セットアップ処理においてCD-ROMが無効"),
    /**
     * -502:ダウンロード失敗（通信エラーやディスクエラーなど）
     */
    _502(-502, "ダウンロード失敗（通信エラーやディスクエラーなど）"),
    /**
     * -503:ファイルが見つからない
     */
    _503(-503, "ファイルが見つからない"),
    /**
     * -504:サーバーメンテナンス中
     */
    _504(-504, "サーバーメンテナンス中"),

    ;

    private final Integer errorCode;
    private final String name;

    JvLinkErrorCode(Integer errorCode, String name) {
        this.errorCode = errorCode;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return errorCode;
    }

    public String getName() {
        return name;
    }

    public static JvLinkErrorCode of(int errorCode){
        return CodeEnum.reversibleFindOne(errorCode, JvLinkErrorCode.class);
    }

}
