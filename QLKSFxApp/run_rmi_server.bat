@echo off
echo Starting RMI Server...
set PROJECT_DIR=%CD%
gradlew runRMIServer
pause
