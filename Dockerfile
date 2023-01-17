FROM openjdk:17 AS build

WORKDIR /test/build

COPY . /test/build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests=true

FROM openjdk:17.0.2-jdk-slim
WORKDIR /test/app

COPY --from=build /test/build/target/gadgetarium-b7-0.0.1-SNAPSHOT.jar /test/app/

EXPOSE 7000

ENTRYPOINT ["java","-jar","/test/app/gadgetarium-b7-0.0.1-SNAPSHOT.jar"]