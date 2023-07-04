#!/usr/bin/env sh

echo "[ >>> ] Run the native build with previously generated hints."

cd ..

mvn -Pnative native:compile
