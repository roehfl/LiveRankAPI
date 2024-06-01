FROM openjdk:17
RUN mkdir -p /app
COPY build/libs/LiveRankAPI-0.0.1-SNAPSHOT.jar /app/LiveRankAPI.jar
ENTRYPOINT ["java", "-jar", "/app/LiveRankAPI.jar"]