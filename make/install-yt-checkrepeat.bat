rd yt-checkrepeat_jar /S /Q
md yt-checkrepeat_jar 
pushd yt-checkrepeat_jar 
md bin
md lib
md config

copy ..\..\..\thirdparty\dubbox\*.* bin\
copy..\..\..\thirdparty\rocketmq\*.* bin\
copy ..\..\lib\yt-utility.jar bin\
copy ..\..\lib\yt-container.jar bin\
copy ..\..\lib\yt-launch.jar bin\


copy ..\..\lib\yt-checkrepeat.jar lib\
copy ..\..\module\yt-checkrepeat\config\*.* config\
copy ..\startup-yt-checkrepeat.bat startup.bat
popd