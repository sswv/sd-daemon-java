#!/bin/bash

_DIR="`dirname "$0"`"
cd "$_DIR"
_DIR="`pwd`"

export JAVA_HOME=/home/lj/jdk
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$JAVA_HOME/bin:/usr/local/bin:/usr/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin:$PATH

echo BASHPID: "$BASHPID"
echo LISTEN_FDS: "$LISTEN_FDS"
echo LISTEN_PID: "$LISTEN_PID"

# Should be asynchronous as in a systemd service starting script
java -cp .:"sd-daemon-java.jar" -Djava.library.path="$_DIR/../native" org.linjian.sd_daemon_java.example.EchoServerNew &
