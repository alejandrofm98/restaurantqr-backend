FROM openjdk:21

WORKDIR /app

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod u+x ./mvnw
RUN ./mvnw -T 4 dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080