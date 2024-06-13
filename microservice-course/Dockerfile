FROM amazoncorretto:17-alpine as builder

LABEL author=deyvi.pajuelo

WORKDIR /app/microservice-course

COPY pom.xml /app
COPY microservice-course/.mvn ./.mvn
COPY microservice-course/mvnw .
COPY microservice-course/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY microservice-course/src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/microservice-course/target/microservice-course-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

CMD ["java", "-jar", "microservice-course-0.0.1-SNAPSHOT.jar"]