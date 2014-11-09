cd d:\dev\data\Dropbox\projects\sProtect\shared\
call mvn clean install
pause
cd d:\dev\data\Dropbox\projects\sProtect\web-main\
call mvn clean package
pause
cd d:\dev\data\Dropbox\projects\sProtect\web-debug\
call mvn clean package
pause