#!/usr/bin/env bash
#$JAVA_HOME/bin/jaotc --output java_base.so --module java.base --info -J-Xmx4g
#java -XX:+UnlockExperimentalVMOptions -XX:AOTLibrary=./java_base.so --module-path outDir -m main.module/day.info.DayInfo $1
java --module-path outDir -m main.module/day.info.DayInfo $1
