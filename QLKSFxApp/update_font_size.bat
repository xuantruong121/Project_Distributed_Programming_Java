@echo off
echo Updating font size in QuanLyNhanVien.fxml...

cd src\main\resources\fxml

powershell -Command "(Get-Content 'QuanLyNhanVien.fxml') -replace '<Font size=\"13.0\"/>', '<Font size=\"14.0\"/>' | Set-Content 'QuanLyNhanVien.fxml'"

echo Done!
pause
