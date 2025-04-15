@echo off
echo Replacing jakarta.persistence with javax.persistence in all Java files...

cd src\main\java\iuh\fit\qlksfxapp

for /r %%f in (*.java) do (
    echo Processing %%f
    powershell -Command "(Get-Content '%%f') -replace 'jakarta\.persistence', 'javax.persistence' | Set-Content '%%f'"
)

echo Done!
pause
