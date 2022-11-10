FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/tpAchatProjet.jar tpAchatProjet.jar
ENTRYPOINT ["java","-jar","/tpAchatProjet.jar"]
