ARG VERSION=19
ARG DISTRIBUTOR=amazoncorretto

FROM ${DISTRIBUTOR}:${VERSION}-al2-jdk as BUILD
#RUN microdnf install findutils

COPY ../Niblette-Carryover/Niblette-669becc38c161c1c2a5c15b70e32c8adc55dd0a0 /src
WORKDIR /src
RUN ./gradlew --no-daemon shadowJar

FROM ${DISTRIBUTOR}:${VERSION}

COPY --from=BUILD /src/build/libs/Niblette-all.jar /bin/runner/run.jar
WORKDIR /bin/runner

CMD ["java","-jar","run.jar"]