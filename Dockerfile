ARG VERSION=19

FROM openjdk:${VERSION}-jdk as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM openjdk:${VERSION}-jre
RUN microdnf install findutils

COPY --from=BUILD /src/build/libs/Niblette.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]