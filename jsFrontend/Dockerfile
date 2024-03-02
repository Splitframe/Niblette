################################################################ build image
ARG VERSION=21
ARG DISTRIBUTOR=amazoncorretto

FROM ${DISTRIBUTOR}:${VERSION}-al2-jdk as BUILD

USER root

COPY *.gradle gradle.* gradlew /src/
COPY gradle /src/gradle
WORKDIR /src
RUN chmod +x gradlew
RUN ./gradlew --version

COPY . .
ARG version
RUN chmod +x gradlew
RUN ./gradlew -Pversion=${version} --no-daemon --console plain -i jsFrontend:build

################################################################# run image
FROM nginx:stable-bullseye

COPY jsFrontend/nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /src/jsFrontend/build/dist/jsFrontend/productionExecutable /usr/share/nginx/html
COPY --from=builder /src/jsFrontend/envInject.sh /docker-entrypoint.d/40-envInject.sh

RUN chmod +x /docker-entrypoint.d/40-envInject.sh
RUN chown nginx:nginx /docker-entrypoint.d/40-envInject.sh

ENTRYPOINT ["/bin/sh", "-c", "/docker-entrypoint.sh nginx -g \"daemon off;\""]
