rd yt-sms_jar /S /Q
md yt-sms_jar 
pushd yt-sms_jar 
md bin
md lib
md config

copy ..\..\..\thirdparty\dubbox\*.* bin\
copy ..\..\..\thirdparty\ssm\*.* bin\
copy ..\..\..\thirdparty\mybatis\*.* bin\
copy ..\..\..\thirdparty\mysql\*.* bin\
copy..\..\..\thirdparty\rocketmq\*.* bin\
copy..\..\..\thirdparty\druid\*.* bin\
copy..\..\..\thirdparty\axis\*.* lib\
copy..\..\..\thirdparty\orgjson\*.* lib\

copy ..\..\lib\yt-utility.jar bin\
copy ..\..\lib\yt-container.jar bin\
copy ..\..\lib\yt-db.jar bin\

copy ..\..\lib\yt-sms.jar lib\
copy ..\..\module\yt-sms\config\*.* config\
copy ..\startup-yt-sms.bat startup.bat
popd