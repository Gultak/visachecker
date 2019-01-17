#!/bin/sh
git pull
mvn package
java -Djava.util.logging.config.file=resources/logging.properties -jar target/visa-checker-standalone.jar
