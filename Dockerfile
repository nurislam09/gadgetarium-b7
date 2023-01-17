FROM openjdk:17 AS build

WORKDIR /onroad/build

COPY . /onroad/build

RUN mvn clean install -DskiptTests=true

FROM openjdk:17.0.2-jdk-slim
WORKDIR /onroad/app

COPY --from=build /onroad/build/target/gadgetarium-b7-0.0.1-SNAPSHOT.jar /onroad/app/

EXPOSE 7000

ENTRYPOINT ["java","-jar","/onroad/app/gadgetarium-b7-0.0.1-SNAPSHOT.jar"]