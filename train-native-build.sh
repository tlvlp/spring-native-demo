#!/usr/bin/env sh

echo "Start native training with tracing agent."

echo "Build base jar to be traced and add first round SpringNative hints."
#mvn clean package -DskipTests -Pnative

echo "Run the native image tracing agent to start pre-training the native build."
# Results will be merged with the previous hints under META-INF
$GRAALVM_HOME/bin/java \
    -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-merge-dir="$(pwd)"/target/classes/META-INF/native-image \
    -jar "$(pwd)"/target/native-test.jar

