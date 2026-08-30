if not exist "bin" mkdir bin

javac -classpath .\bin;.\lib\* ^
-encoding UTF-8 ^
-d bin ^
-sourcepath .\src ^
.\src\tafl\util\*.java ^
.\src\tafl\modelo\*.java ^
.\src\tafl\control\*.java ^
.\src\tafl\textui\*.java