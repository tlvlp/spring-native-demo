#!/usr/bin/env sh

echo "Run the native image tracing agent to pre-train the native build. \
The result of consecutive runs will be merged. \
Just stop the running process once training is complete!"
$GRAALVM_HOME/bin/java \
    -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-merge-dir="$(pwd)"/target/classes/META-INF/native-image \
    -jar "$(pwd)"/target/native-test.jar
