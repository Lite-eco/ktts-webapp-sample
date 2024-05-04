FROM eclipse-temurin:21-jdk

RUN apt-get update
RUN apt-get -y install git

WORKDIR /src

CMD git clone --depth 1 file:///repo ; cd repo && git pull && ./gradlew template-sample-server:bootRun --args='--spring.profiles.active=dev,dev-docker' --no-daemon
