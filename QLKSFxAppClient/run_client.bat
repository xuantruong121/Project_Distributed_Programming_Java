@echo off
echo Starting RMI Client...
set PROJECT_DIR=%CD%

:: Set Java RMI properties
set JAVA_OPTS=-Djava.security.policy=%PROJECT_DIR%\rmi.policy -Djava.rmi.server.codebase=file:%PROJECT_DIR%\build\classes\java\main\

:: Run the client application
gradlew run -Djava.security.policy=%PROJECT_DIR%\rmi.policy

pause
