#!/usr/bin/env sh

# Runtime (in local without config/optimization) ~4 min

echo "Run the native image tracing agent to pre-train the native build. \
The result of consecutive runs will be merged. \
Just stop the running process once training is complete!"
$GRAALVM_HOME/bin/java \
    -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-merge-dir="$(pwd)"/target/classes/META-INF/native-image \
    -jar "$(pwd)"/target/native-test.jar

echo "Creating a safety copy of the generated hints to be able to skip this step in the future"
mkdir -p $(pwd)/native-hints-backup/post-train
cp -rf $(pwd)/target/classes/META-INF $(pwd)/native-hints-backup/post-train/

echo "Package the updated META-INF into the jar"
mvn package -DskipTests