#!/usr/bin/env sh

#Todo make it parallel

for i in {1..1000}
do
  http POST :8080/append appendWith=$i
done