#!/usr/bin/env sh

# Runtime depends only on the training.

INPUT_JAR_PATH="$(pwd)"/target/native-test.jar

echo "Run the native image tracing agent to pre-train the native build. \
The result of consecutive runs will be merged. \
Just stop the running process once training is complete!"
"$GRAALVM_HOME"/bin/java \
    -Dspring.aot.enabled=true \
    -agentlib:native-image-agent=config-merge-dir="$(pwd)"/target/classes/META-INF/native-image \
    -jar "$INPUT_JAR_PATH"

echo "Create a safety copy of the generated hints to be able to skip this step in the future"
mkdir -p $(pwd)/native-hints-backup/post-train
cp -rf $(pwd)/target/classes/META-INF $(pwd)/native-hints-backup/post-train/

echo "Package the updated META-INF into the jar"
mvn package -DskipTests