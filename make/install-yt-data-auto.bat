rd yt-data-auto_jar /S /Q
md yt-data-auto_jar 
pushd yt-data-auto_jar 
md bin
md lib
md config

copy ..\..\..\thirdparty\dubbox\*.* bin\
copy ..\..\..\thirdparty\ssm\*.* bin\
copy ..\..\..\thirdparty\mybatis\*.* bin\
copy ..\..\..\thirdparty\mysql\*.* bin\
copy..\..\..\thirdparty\druid\*.* bin\


copy ..\..\lib\yt-utility.jar bin\
copy ..\..\lib\yt-container.jar bin\
copy ..\..\lib\yt-db.jar bin\

copy ..\..\lib\yt-data-auto.jar lib\
copy ..\..\module\yt-data-auto\config\*.* config\
copy ..\startup-yt-data-auto.bat startup.bat
popd