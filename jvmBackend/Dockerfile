ARG VERSION=21
ARG DISTRIBUTOR=amazoncorretto

FROM ${DISTRIBUTOR}:${VERSION}-al2-jdk as BUILD
#RUN microdnf install findutils

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar --console plain

FROM ${DISTRIBUTOR}:${VERSION}

COPY --from=BUILD /src/build/libs/niblette-server.jar /bin/runner/run.jar
WORKDIR /bin/runner

ENTRYPOINT ["java","-jar","run.jar"]
