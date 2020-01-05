
### On windows server
 - Install
   - java 8 32bit
   - JvLink
 
 - environment
   - 受信port開放 8080
   - 送信port開放 27017
  
 - service化
   - winsw[https://github.com/kohsuke/winsw]
   
   
   
 - 手動起動
```
java -jar uma-feed-app.jar --spring.profiles.active=prd,setup -Xms512M -Xmx1024M
``` 
 - jstat
 ```
jstat -gcutil -t <PID> 1000
```
