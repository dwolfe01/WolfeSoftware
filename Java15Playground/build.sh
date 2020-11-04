#!/usr/bin/env bash
javac -d outDir/day.module $(find modules/day.module/src -name "*.java")
javac -d outDir/main.module --module-path outDir $(find modules/main.module/src -name "*.java")
