FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/tpAchatProjet.jar tpAchatProjet-1.0.jar
ENTRYPOINT ["java","-jar","/tpAchatProjet.jar"]
