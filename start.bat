@echo off
set BASE_DIR=%CD%
set JAVA_HOME=%BASE_DIR%\jdk.1.8.0_102
set PATH=%JAVA_HOME%\bin;C:\WINDOWS;C:\WINDOWS\COMMAND
set classpath=.;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar

java -jar TenChooseTwo.jar