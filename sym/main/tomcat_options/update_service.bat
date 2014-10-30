rem Update the service
rem for tapestry correct page encoding
rem native library (i.e. opencv) folder
tomcat7 //US//Tomcat7 --JvmOptions=-Djava.library.path=c:\dev\native ^ --JvmOptions=-Dfile.encoding=UTF-8

rem MySQL dependency
sc config Tomcat7 depend= MySQL56

pause