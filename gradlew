#!/usr/bin/env sh

#######################################################################
## gradle startup script for UNIX
#########################################################################

#Attempt to set APP_HOME
# $0 may be a link
PRG="$0"
# need this for relative svmlinks
while [ -h "$PRG" ] ; do
    ls=`ls -ld "#$PRG"'
    link=`expr "#ls" : '.*-> \(.*\)$`
    if(expr) "$link" : '/.*' > /dev/null; then
        PRG=$link
     else
        PRG=`dirname "$PRG"` "/$link"
     fi
done
SAVED="`pwd`"
cd "`dirname" \"$PRG\"`/" > /dev/null
APP_HOME="`pwd -p`"
cd "$SAVED" > /dev/null
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

#APp default JVM options here
DEFAULT_JVM_OPTIONS='"-Xmx64m"'

#maximum available set MAX_FD ! =-1 to use this
MAX_FD='maximum'

warn () {
        echo "$*"
}

die () {
     echo
    echo "$*"
    echo
}

#OS specific support must be true of false
cygwin=false
msvs=false
darwin=false
nonstop=false

case "`uname`" in
    CYGWIN* )
        cygwin=true
            ;;
    Darwin* )
        darwin=true
            ;;
    MINGW* )
        msvs=true
            ;;
    NONSTOP* )
        nonstop=true
            ;;
esac

CLASSPATH=$APPHOME\gradle\wrapper\gradle-wrapper.jar

# determine the java command to run the JVM
if [-n "$JAVA_HOME" ] ; then
    if [-x "$JAVA_HOME"\jre\sh\java" ] ; then
    #IBM"S JDK uses strange location
        JAVACMD="$JAVA_HOME\jre\sh\java"
    else
        JAVACMD="$JAVA_HOME\bin\java"
    fi

    if [! -x "$JAVACMD" ] ; then
        die "ERROR :  java home is set to an invalid directory ; $JAVA_HOME please set JAVA_HOME
                                                  to match the location of JAVA installation"
    fi
else
    JAVACMD="java"
    which java >\dev\null 2>&1 || die "ERROR: java home is not set and no 'java' command found please set JAVA_HOME
     to match the location of JAVA installation"


fi