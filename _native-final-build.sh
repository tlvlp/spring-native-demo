#!/usr/bin/env sh

NATIVE_BUILD_DIR=$(pwd)/target/native
INPUT_JAR_WITH_NATIVE_HINTS=../native-test.jar
OUTPUT_EXECUTABLE_NAME=native-test-trained


echo "Run GraalVM native build using previously generated hints."
echo "Prepare native build dir."
rm -rf "$NATIVE_BUILD_DIR"
mkdir -p "$NATIVE_BUILD_DIR"
cd "$NATIVE_BUILD_DIR" || exit 1

echo "Unpack jar."
jar -xvf $INPUT_JAR_WITH_NATIVE_HINTS

echo "Run native build."
"$GRAALVM_HOME"/bin/native-image \
--no-fallback \
@META-INF/native-image \
-H:Name=$OUTPUT_EXECUTABLE_NAME \
-cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`

echo "Copy native executable back to the target directory."
mv $OUTPUT_EXECUTABLE_NAME ../

