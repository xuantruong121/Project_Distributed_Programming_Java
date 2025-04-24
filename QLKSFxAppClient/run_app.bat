@echo off
echo Starting JavaFX Application...

REM Set the path to your JDK
set JAVA_HOME=C:\Program Files\Java\jdk-21

REM Set the path to JavaFX SDK (you need to download this separately)
set JAVAFX_HOME=C:\path\to\javafx-sdk-17.0.2

REM Set the module path to include JavaFX modules
set PATH_TO_FX=%JAVAFX_HOME%\lib

REM Compile the application
"%JAVA_HOME%\bin\javac" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d build\classes\java\main src\main\java\iuh\fit\qlksfxappclient\TestJavaFX.java

REM Run the application
"%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -cp build\classes\java\main iuh.fit.qlksfxappclient.TestJavaFX

pause
