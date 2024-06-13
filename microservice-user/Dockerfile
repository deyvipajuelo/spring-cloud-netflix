ARG MSCV_NAME=microservice-user

FROM amazoncorretto:17-alpine as builder

LABEL author=deyvi.pajuelo

ARG MSCV_NAME

WORKDIR /app/$MSCV_NAME

COPY pom.xml /app
COPY $MSCV_NAME/.mvn ./.mvn
COPY $MSCV_NAME/mvnw .
COPY $MSCV_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY $MSCV_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM amazoncorretto:17-alpine

ARG MSCV_NAME

WORKDIR /app

RUN mkdir "logs"

COPY --from=builder /app/$MSCV_NAME/target/microservice-user-0.0.1-SNAPSHOT.jar .
ENV PORT=8001
EXPOSE $PORT

CMD ["java", "-jar", "microservice-user-0.0.1-SNAPSHOT.jar"]