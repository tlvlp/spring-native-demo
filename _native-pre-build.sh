#!/usr/bin/env sh

# Runtime (in local without config/optimization) ~4 min

echo \
"Running the first native build aided by the Spring AOT hints to generate the hint configs that are required to start in GraalVM. \br \
Note that this will not fully run the application and will not detect any 'hidden' \
class and resource use like the Graalvm Tracing Agent. \br"
mvn clean package -DskipTests -Pnative

echo "Creating a safety copy of the generated hints to be able to skip this step in the future"
mkdir -p $(pwd)/native-hints-backup/pre-train
cp -rf $(pwd)/target/classes/META-INF $(pwd)/native-hints-backup/pre-train/

echo "Package the META-INF into the jar"
mvn package -DskipTests