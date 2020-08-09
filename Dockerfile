FROM openjdk
EXPOSE 8080
ENV secrets.BOT_TOKEN=${secrets.BOT_TOKEN}
ADD build/libs/yaBot-1.0.jar yaBot-1.0.jar
ENTRYPOINT ["java", "-jar", "/yaBot-1.0.jar" ]
