@echo off
echo Replacing javax.persistence with jakarta.persistence in all Java files...

cd src\main\java\iuh\fit\qlksfxapp

for /r %%f in (*.java) do (
    echo Processing %%f
    powershell -Command "(Get-Content '%%f') -replace 'javax\.persistence', 'jakarta.persistence' | Set-Content '%%f'"
)

echo Replacing javax.validation with jakarta.validation in all Java files...

for /r %%f in (*.java) do (
    echo Processing %%f
    powershell -Command "(Get-Content '%%f') -replace 'javax\.validation', 'jakarta.validation' | Set-Content '%%f'"
)

echo Done!
pause
