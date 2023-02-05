ARG VERSION=19

FROM java:${VERSION}-jdk as BUILD
RUN microdnf install findutils

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM java:${VERSION}-jre

COPY --from=BUILD /src/build/libs/Niblette.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]