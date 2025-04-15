@echo off
echo Replacing jakarta.validation with javax.validation in all Java files...

cd src\main\java\iuh\fit\qlksfxapp

for /r %%f in (*.java) do (
    echo Processing %%f
    powershell -Command "(Get-Content '%%f') -replace 'jakarta\.validation', 'javax.validation' | Set-Content '%%f'"
)

echo Done!
pause
