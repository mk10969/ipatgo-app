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
java -jar uma-feed-app.jar --spring.profiles.active=prd,setup -Xms1024M -Xmx2048M
``` 
 - jstat
 ```
jstat -gcutil -t <PID> 1000
```

