@echo off
echo Starting RMI Client...
set PROJECT_DIR=%CD%
gradlew runRMIClient
pause
