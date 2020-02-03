# uma-jvLink

## uma-jvLink-client
 - JvLinkと接続するためのクライアントライブラリ
 - JvLinkをマルチスレッド上で動作させるために、
 クライアントで、ロックをかけて排他制御を行っている。


## uma-jvLink-server
 - boot Jar型 Web server (tomcat内包)  
 - JvLinkから得られたデータを、HTTPで返すサーバ。
 - endpoint 一覧


## uma-JvLink-setup
 - データをセットアップするためのサーバ。
 - JvLinkセットアップデータを、ファイルに出力する。


## uma-jvLink-service
 - uma-jvLink-serverを、サービス化するための設定群。
