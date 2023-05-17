#!/usr/bin/env sh

echo "Run GraalVM native build using previously generated hints."
rm -rf target/native
mkdir -p target/native
cd target/native
jar -xvf ../native-test.jar
$GRAALVM_HOME/bin/native-image -H:Name=native-test-trained -cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
mv native-test-trained ../
