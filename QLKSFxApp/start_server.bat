@echo off
echo Starting QLKS Server Application...

:: Set Java RMI properties
set JAVA_OPTS=-Djava.rmi.server.hostname=localhost

:: Run the server application
java %JAVA_OPTS% -jar qlks-server-1.0.0.jar

pause
