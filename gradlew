#!/bin/sh
# Gradle Wrapper launcher for macOS and Linux.
# License: Apache 2.0, https://www.apache.org/licenses/LICENSE-2.0
set -eu
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -n "${JAVA_HOME:-}" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD=java
fi
if ! command -v "$JAVACMD" >/dev/null 2>&1; then
    echo "ERROR: Java not found. Install JDK 17+ and set JAVA_HOME." >&2
    exit 1
fi
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
exec "$JAVACMD" -Dfile.encoding=UTF-8 -Xmx64m -Xms64m -Dorg.gradle.appname=gradlew -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
