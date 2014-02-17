@echo off
rem ----------------------------------------------------------------------------
rem --- mp3caretaker / Start-Script fuer Windows
rem ---
rem --- Copyright (c) 2014 by funksem
rem ---
rem --- $Id$
rem ----------------------------------------------------------------------------

set DIR=%~dp0
set DEFAULT_URL=${rest.url}

if not "%JAVA_HOME%" == "" goto gotJdkHome
echo Die Umgebungsvariable JAVA_HOME ist nicht gesetzt.
echo Diese Umgebungsvariable ist noetig um die Applikation zu starten.
goto END

:gotJdkHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome
if not exist "%JAVA_HOME%\bin\javaw.exe" goto noJavaHome
set "JRE_HOME=%JAVA_HOME%"
goto okJavaHome

:noJavaHome
echo Die Umgebungsvariable JAVA_HOME ist nicht korrekt gesetzt.
echo Diese Umgebungsvariable ist noetig um die Applikation zu starten.
echo Tipp: JAVA_HOME auf die JDK setzen und nicht auf die JRE.
goto END

:okJavaHome


"%JRE_HOME%\bin\java.exe" -Dlogback.configurationFile=%DIR%logback.xml -cp %DIR%lib\* de.funksem.mp3caretaker.Mp3Caretaker %* 

:END
