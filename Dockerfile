# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine
# copy WAR into image
COPY ./build/libs/myretail-rest-service-0.1.0.jar /myretail-rest-service.jar 
COPY ./aws_credential.properties /aws_credential.properties
# run application with this command line 
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/myretail-rest-service.jar"]