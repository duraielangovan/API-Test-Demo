@if "%DEBUG%" == "" @Off
@rem############################################
@rem
@rem Gradle startup scripts for windows
@rem
@rem############################################
@rem Set local scope for the variable with windows NT shell
if %OS% == "WINDOWS_NT" setlocal
set DIRNAME=%~dp0
if %DIRNAME%="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%
@rem add default JVM options here, we can use JAVA_OPTS or GRADLE_OPTS to pass JV options
set DEFAULT_JVM_OPTS="-Xmx64m"
@rem Find java.exe
If defined JAVA_HOME goto findJavaFromHome

set JAVA_EXE =java.exe
%JAVA_EXE% -version > NUL 2>&1
if %ERRORLEVEL% == "0" goto init
echo.
echo ERROR: JAVA_HOME is not set and no java command could be found on your path
echo Please set the JAVA_HOME and match to the java

goto fail

:init
@rem get command line arguments

if not %OS% == "WINDOWS_NT" goto win9XME_args

:win9XME_args
@ rem slurp the line arguments
set CMD_LINE_ARGS=
set _SKIP=2

:win9XME_args_slurp
if "x%1~" == "x" goto execute

set CMD_LINE_ARGS=%*

@rem Setup the command line

set CLASSPATH = %APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem execute gradle wrapper
 "%JAVA_HOME%" %DEFAULT_JVM_OPTS% %JAVA_OPTS%  "-Dorg.gradle.appname=%APP_BASE_NAME% -classPath="%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%

:end

@rem end local scope for the variables with WINDOWS_NT

if %ERRORLEVEL% == "0" goto mainEnd

:fail
rem set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem _cmd.ext /c_ return code!
if not "" == %GRADLE_EXIT_CONSOLE% exit 1
exit \b 1

:mainEnd
if %OS% == "WINDOWS_NT" endlocal

:omega








