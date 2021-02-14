# FROM gitpod/workspace-full

# add your tools here ...

FROM openjdk:8
EXPOSE 8080 8081
ADD xmeme-backend/target/xmeme-backend.jar xmeme-backend.jar
ENTRYPOINT ["java", "-jar","/xmeme-backend.jar"]