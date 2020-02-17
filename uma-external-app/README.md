### On windows server
 - Install
   - java 8 32bit
   - JvLink
   - OpenSSH
 
 - environment
   - 受信port開放 8080
   - 送信port開放 27017
  
 - app service化
   - [winsw](https://github.com/kohsuke/winsw)
   
 - OpenSSH service化
   - 管理者でPowerShell login
   - $ Start-Service sshd
   - $ Set-Service -Name sshd -StartupType 'Automatic'
 


 - ssh接続
```
ssh Administrator@192.168.56.104
```

 - 起動
```
java ^
 -XX:+UseConcMarkSweepGC ^
 -XX:+UseParNewGC ^
 -XX:+CMSParallelRemarkEnabled ^
 -XX:+CMSClassUnloadingEnabled ^
 -XX:+UseCMSInitiatingOccupancyOnly ^
 -XX:CMSInitiatingOccupancyFraction=40 ^
 -Xms512M ^
 -Xmx512M ^
 -Xmn256M ^
 -XX:NewRatio=2 ^
 -XX:SurvivorRatio=8 ^
 -XX:MaxTenuringThreshold=15 ^
 -XX:TargetSurvivorRatio=90 ^
 -Xloggc:./log/gc.log ^
 -XX:+PrintGCDetails ^
 -XX:+PrintGCTimeStamps ^
 -jar uma-external-jvlink-server.jar ^
 --spring.profiles.active=prd
```

 - jstat
 ```
jstat -gcutil -t <PID> 1000
```

 - histogram
```
jcmd xxxxx
```