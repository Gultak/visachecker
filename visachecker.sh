#!/bin/sh
git pull -q
mvn -q package
java -Djava.util.logging.config.file=resources/logging.properties -jar target/visa-checker-standalone.jar
