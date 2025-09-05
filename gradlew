#!/bin/sh

##############################################################################
# Gradle wrapper script
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

# Use system gradle if available
if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
else
    echo "Error: Gradle is not installed. Please install Gradle first."
    echo "You can install it via: brew install gradle"
    exit 1
fi