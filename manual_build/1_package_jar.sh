#!/usr/bin/env sh

echo "[ >>> ] Packaging jar"

cd ..

mvn clean package -DskipTests