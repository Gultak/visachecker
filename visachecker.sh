#!/bin/sh
cd "$(dirname "$0")"
/usr/bin/git pull -q
/usr/bin/mvn -q package
/usr/bin/java -Djava.util.logging.config.file=resources/logging.properties -jar target/visa-checker-standalone.jar
