#!/usr/bin/env sh

echo "[ >>> ] Run the native image tracing agent to pre-train the native build. \
The output will be added to the source code, so make sure to add *META-INF to .gitignore \
Run 3_call_service.py to make the calls and just stop the running process once training is complete!"

cd ..

"$GRAALVM_HOME"/bin/java \
    -agentlib:native-image-agent=config-output-dir=./src/main/resources/META-INF/native-image/ \
    -jar target/native-test.jar
