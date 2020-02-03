### On windows server

 - Require
   - java 8 32bit
   - JvLink


 - run
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
 -jar uma-jvLink-setup.jar
```

 - jstat
 ```
jstat -gcutil -t <PID> 1000
```

 - histogram
```
jcmd xxxxx
```
