ARG VERSION=19
ARG DISTRIBUTOR=amazoncorretto

FROM ${DISTRIBUTOR}:${VERSION}-al2-jdk as BUILD
#RUN microdnf install findutils

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar --console plain

FROM ${DISTRIBUTOR}:${VERSION}

COPY --from=BUILD /src/build/libs/Niblette-1.0-SNAPSHOT-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]
