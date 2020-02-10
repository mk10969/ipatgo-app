# uma-external-app


## uma-external-ipatgo
 - IPATGOをJavaから呼び出して利用する。
 - endpoint 一覧


## uma-external-jvlink-client
 - JvLinkと接続するためのクライアントライブラリ
 - JvLinkをマルチスレッド上で動作させるために、
 クライアントで、ロックをかけて排他制御を行っている。


## uma-external-jvlink-server
 - boot Jar型 Web server (tomcat内包)  
 - JvLinkから得られたデータを、HTTPで返すサーバ。
 - endpoint 一覧


## uma-external-jvlink-setup
 - setup専用Pythonスクリプト
 - 丸一日かかる。。。


## windows-service
 - uma-external-ipatgoを、サービス化するための設定群。
 - uma-external-jvlink-serverを、サービス化するための設定群。

