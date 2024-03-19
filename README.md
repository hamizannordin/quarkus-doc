# quarkus-doc

*This project uses Quarkus, the Supersonic Subatomic Java Framework.*

*If you want to learn more about Quarkus, please visit its website: https://quarkus.io/*

This app allows you to convert any file extension into DOCX format.

You can run the code in IDE, or package the app to begin.

The app will ask for the target directory and file extension, then save the converted files in new folder `.../quarkus-doc` inside the target folder.

Feel free to fork this repository and customise it the way you liked!

## Prerequisite
This code requires at least **JDK version 17** and later.
<br>

## Coding with Quarkus

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

### Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.
<br>
*authored by @hamizannordin*