FROM openjdk
ENV BOT_TOKEN=${secrets.BOT_TOKEN}
EXPOSE 8080
ADD build/libs/yaBot-1.0.jar yaBot-1.0.jar
ENTRYPOINT ["java", "-jar", "/yaBot-1.0.jar" ]
