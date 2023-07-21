FROM openjdk:11
ADD TechnicalTest.jar app.jar
ADD TechnicalTest.properties application.properties
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.config.location=/application.properties","/app.jar"]
