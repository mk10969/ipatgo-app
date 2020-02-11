### On windows server

 - Require
   - [IPATGO](https://ipat-docs.readthedocs.io/ja/latest/https://ipat-docs.readthedocs.io/ja/latest/)



  - 起動
```
java ^
 -Xms512M ^
 -Xmx512M ^
 -Xmn256M ^
 -Xloggc:./log/gc.log ^
 -XX:+PrintGCDetails ^
 -XX:+PrintGCTimeStamps ^
 -jar uma-external-ipatgo.jar ^
 --ipatgo.authentication.INetId=XXXXXX ^
 --ipatgo.authentication.subscriberNo=XXXXXX ^
 --ipatgo.authentication.password=XXXXXX ^
 --ipatgo.authentication.parsNo=XXXXXXl
```
