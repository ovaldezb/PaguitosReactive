FROM eclipse-temurin:17-jdk-alpine
RUN apk add curl
VOLUME /tmp
EXPOSE 9080
ADD target/Paguitos-1.0.0.jar Paguitos-1.0.0.jar
ENTRYPOINT ["java","-jar","/Paguitos-1.0.0.jar"]