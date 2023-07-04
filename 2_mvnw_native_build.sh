#!/usr/bin/env sh

echo "[ >>> ] Run native build with previously generated hints."

GRAALVM_IMAGE=ghcr.io/graalvm/graalvm-community:17-ol9
CONTAINER_ENTRY_POINT=/app

docker run \
  --rm \
  -it \
  -v "$(pwd)":$CONTAINER_ENTRY_POINT:rw \
  -v "$HOME"/.m2/repository/:/root/.m2/repository \
  --name graalvm-build \
  $GRAALVM_IMAGE \
  \
  ./mvnw -Pnative native:compile



