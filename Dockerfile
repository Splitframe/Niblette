ARG VERSION=19-al2
ARG DISTRIBUTOR=amazoncoretto

FROM ${DISTRIBUTOR}:${VERSION}-jdk as BUILD
RUN microdnf install findutils

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM ${DISTRIBUTOR}:${VERSION}-jre

COPY --from=BUILD /src/build/libs/Niblette.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]