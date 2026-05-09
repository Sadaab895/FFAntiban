#!/usr/bin/env sh
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
APP_HOME=`pwd -P`

if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

exec "$JAVACMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
