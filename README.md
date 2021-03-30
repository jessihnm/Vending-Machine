# Vending Machine


A Java project to exercise primarily the following computer science topics:

- State Machines
- Serialization
- TCP networking


**Requires Java 8 or higher.**

## Running Tests


```bash
./gradlew test
```


## Dependencies

This project uses Gradle 6.1 or higher for dependency management.

Below is a list of dependencies with their equivalent Gradle entry.

#### Jackson Databind

For JSON serialization and deserialization.

```groovy
implementation 'com.fasterxml.jackson.core:jackson-databind:2.12.2'
```


#### JUnit Jupiter 5 API and Engine

As test framework and engine.

```groovy

testImplementation 'org.junit.jupiter:junit-jupiter-api:5.4.2'
testRuntimeOnly "org.junit.jupiter:junit-jupiter-engine:5.4.2"
```

Note: requires the following lines added to `build.gradle`

```groovy
test {
    useJUnitPlatform()
}
```

#### Hamcrest 2.2

For more readable assertions and matchers.

```groovy
testImplementation 'org.hamcrest:hamcrest:2.2'
```
