### On windows server
 - Install
   - java 8 32bit
   - JvLink
   - OpenSSH
 
 - environment
   - 受信port開放 8080
   - 送信port開放 27017
  
 - app service化
   - winsw[https://github.com/kohsuke/winsw]
   
 - OpenSSH service化
   - 管理者でPowerShell login
   - $ Start-Service sshd
   - $ Set-Service -Name sshd -StartupType 'Automatic'
 


 - ssh接続
```
ssh Administrator@192.168.56.104
```

 - 手動起動
```
java -Xms1024M -Xms1024M -Xloggc:./log/gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -jar uma-feed-app.jar --spring.profiles.active=prd,setup
``` 
 - jstat
 ```
jstat -gcutil -t <PID> 1000
```

