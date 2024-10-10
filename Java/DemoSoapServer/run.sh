#!/bin/sh

./generate_bindings.sh > src/main/resources/global.xjb

mvn clean generate-sources compile exec:java -Dexec.mainClass="eu.datex2.demo.Application"