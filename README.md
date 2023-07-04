# Intro
Demo project for exploring and presenting some aspects of Spring Native builds.

> The main aim is to reduce the complexity that inherently come with native builds.
> 
> This repo has slowly turned into a POC to make 'native' a natural part of the build process, making it a one time
> configuration burden that the devs can forget about as long as the tests cover every part of their code.

Experience-wise it's comparable to reactive programming, it can be `frustrating` to understand and fix 
but `very rewarding` when things fall into place.

I partially ventured down this rabbit hole, so that you don't have to 
(unless `saving ops costs` are a priority, then you should definitely give it a go).


# Building

## Dockerized build
This is currently the most convenient build method I could find,
as it includes automated tracing during the integration tests

The related steps are in the project root:
- [1_mvnw_verify_with_trace.sh](1_mvnw_verify_with_trace.sh) Runs the integration tests while building native hints
  with the GraalVM tracing agent. 
  Note that the agent configuration is in the [pom.xml](pom.xml)'s Failsafe plugin configs and if required, 
  the same can be added to the Surefire unit test plugin as well. 
  Using `config-merge-dir` to set the output path ensures that each test will merge the generated hints, 
  while using the `config-output-dir` would override the hints on each run.
- [2_mvnw_native_build.sh](2_mvnw_native_build.sh) Runs the native image build with the previously created hints.
- (Optional) [3_call_service.py](3_call_service.py) Tests the running service with a few thousand calls.`python3 3_call_service.py`
  
## Manual build
Not recommended for production as there are still redundant steps here, but the related scripts have been moved
to the [manual_build](manual_build) folder as they can be useful to understand the process step by step:

1. `1_package_jar.sh` Pre-packages the jar to make it ready for tracing.
2. Run `2_train_and_trace.sh` to start the tracing agent (let it run for the next 2 steps).
3. Run `3_call-service.py` to exercise the service.
4. Stop the Tracing Agent in `_train-and-trace.sh` with `ctrl+c` to write and package the hints.
5. Run `_native-final-build.sh` to make the final build.



# Presentation

## Spring Boot vanilla
- Dynamic runtime elements with heavy use of reflection and proxies.
- Generally more flexible.

> PROS
> - Runtime flexibility.
> - Slightly more performant out of the box (with a warmed up HotSpot VM).

> CONS
> - Larger memory footprint >> Increased costs.
> - Slow startup.

## SpringBoot Native / GraalVM
- Only static, build time elements (AOT - Ahead Of Time compilation).
- Favours libraries with build time code generation, eg.: Lombok, Mapstruct.
- All reflection and resource needs to be declared through build-time hints.
- All unused classes and even unused methods in otherwise used classes that are not declared gets removed.

> PROS
> - Low memory footprint >> Decreased costs.
> - Sub-second startup time.

> CONS
> - Adds complexity.
> - Prone to hidden runtime errors: missing reflection for serialization. (Can be eliminated with correct building and thorough testing)
> - Needs to be trained at build time to match the performance of HotSpot.
> - Building is longer and more resource-intensive.

## Configuring native builds
During a native build, GraalVM analyzes the application from the entry point and `anything that it cannot detect will not be part of the final image`.
To ensure that all the required bits are included, we need to provide `natvive hints` for the compiler. 

- This can be `complex` and can lead to `unexpected runtime behaviour`.
- `Spring` provides multiple ways for `aiding the AOT compilation`.
- When using the `Maven/Gradle Spring native plugin`, it will `generate hints` for all the reflections and proxies it knows about, like API entities for serialization.

All configuration methods can coexist and are cumulative:
- `Imperative` way (Class and method level annotations)
- `Declarative` way (Directly declaring native hints in a Spring Bean)
- `Tracing` ([GraalVM Tracing Agent](https://www.graalvm.org/22.0/reference-manual/native-image/Agent/))
- Directly modifying `GraalVM native hints files`.

## Testing
- Recommended to test the services normally for a fast feedback loop.
- Only test the native build to see if all parts are there.


## Useful links
- [GraalVM Docs](https://www.graalvm.org/22.0/reference-manual/native-image/).
- [Spring Boot Native Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.introducing-graalvm-native-images).

## Things to explore
- A closer look at GraalVM.
- A meaningful dockerized build pipeline.
- Proper native testing.
- Spring Security.
- Performance tuning - Training the native image with integration tests and stress tests.