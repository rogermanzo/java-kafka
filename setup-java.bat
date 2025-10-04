@echo off
echo Configurando Java para SaludApp...

REM Buscar Java en ubicaciones comunes
set JAVA_PATH=""

if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set JAVA_PATH=C:\Program Files\Java\jdk-21
    goto :found
)

if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set JAVA_PATH=C:\Program Files\Java\jdk-17
    goto :found
)

if exist "C:\Program Files\Java\jdk-11\bin\java.exe" (
    set JAVA_PATH=C:\Program Files\Java\jdk-11
    goto :found
)

if exist "C:\Program Files\Eclipse Adoptium\jdk-21.0.1.9-hotspot\bin\java.exe" (
    set JAVA_PATH=C:\Program Files\Eclipse Adoptium\jdk-21.0.1.9-hotspot
    goto :found
)

echo Java no encontrado. Por favor instala Java 21 desde https://adoptium.net/
pause
exit /b 1

:found
echo Java encontrado en: %JAVA_PATH%
set JAVA_HOME=%JAVA_PATH%
set PATH=%JAVA_HOME%\bin;%PATH%

echo JAVA_HOME configurado: %JAVA_HOME%
echo.
echo Ejecutando Maven...
.\mvnw.cmd clean compile

pause
