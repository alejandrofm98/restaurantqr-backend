FROM maven:3.9.9-amazoncorretto-21-debian AS build
WORKDIR /app
COPY ../pom.xml .
COPY ../src/ ./src
RUN  mvn clean package -DskipTests

FROM  amazoncorretto:21.0.5
WORKDIR /app
COPY --from=build /app/target/*.jar click2eat.jar
EXPOSE 8080 5005
CMD ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "click2eat.jar"]
