FROM openjdk:8-jre-alpine

USER root

ENV JAR_FILE vertx-tcp-1.0.0.jar

COPY build/libs/$JAR_FILE .

ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $JAR_FILE"]