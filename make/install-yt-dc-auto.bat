rd yt-dc-auto_jar /S /Q
md yt-dc-auto_jar 
pushd yt-dc-auto_jar 
md bin
md lib
md config

copy ..\..\..\thirdparty\dubbox\*.* bin\
copy ..\..\..\thirdparty\ssm\*.* bin\


copy ..\..\lib\yt-utility.jar bin\
copy ..\..\lib\yt-container.jar bin\

copy ..\..\lib\yt-data-auto.jar lib\
copy ..\..\lib\yt-dc-auto.jar lib\
copy ..\..\module\yt-dc-auto\config\*.* config\
copy ..\startup-yt-dc-auto.bat startup.bat
popd