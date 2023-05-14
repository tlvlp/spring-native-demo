# Intro
Demo project for exploring and presenting some aspects of Spring Native builds.

Experience-wise it's comparable to reactive programming, it can be `frustrating` to understand and fix 
but `very rewarding` when things fall into place.

I partially ventured down this rabbit hole, so that you don't have to 
(unless `saving ops costs` are a priority, then you should definitely give it a go).

# Differences

## Spring Boot
- Dynamic runtime elements with heavy use of reflection and proxies.
- Generally more flexible.

> PROS
> - Runtime flexibility.
> - Slightly more performant out of the box (with a warmed up HotSpot VM).

> CONS
> - Larger memory footprint >> Increased costs.
> - Slow startup.

## Spring Native / GraalVM
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

# Configuring native builds
During a native build, GraalVM analyzes the application from the entry point and `anything that it cannot detect will not be part of the final image`.
To ensure that all the required bits are included, we need to provide `natvive hints` for the compiler. 

- This can be `complex` and can lead to `unexpected runtime behaviour`.
- `Spring` provides multiple ways for `aiding the AOT compilation`.
- When using the `Maven/Gradle Spring native plugin`, it will `generate hints` for all the reflections and proxies it knows about, like API entities for serialization.

All configuration methods can coexist and are cumulative:
- `Imperative` way (Class and method level annotations)
- `Declarative` way (Directly declaring native hints in a Spring Bean)
- `Profiling` ([GraalVM Tracing Agent](https://www.graalvm.org/22.0/reference-manual/native-image/Agent/))
- Directly modifying `GraalVM native hints files`.

# Building
Depends on your configuration:
- If `all native hints are provided`, then a simple `mvn clean package -Pnative` is enough.
- If `training is necessary` to discover all components, multiple builds will be necessary.
  - Generate native hints through GraalVM Tracer Agent.
  - Run native build with hints on the classpath.

# Useful links
- [GraalVM Docs](https://www.graalvm.org/22.0/reference-manual/native-image/).
- [Spring Boot Native Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.introducing-graalvm-native-images).

# Things to explore
- A closer look at GraalVM.
- A meaningful dockerized build pipeline.
- Proper native testing.
- Spring Security.
- Performance tuning - Training the native image with integration tests and stress tests.