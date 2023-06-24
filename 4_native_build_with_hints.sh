#!/usr/bin/env sh

echo "[ >>> ] Run the native build with previously generated hints."

mvn -Pnative native:compile
