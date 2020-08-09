FROM openjdk
RUN export ADMIN_USER=${secrets.BOT_TOKEN} \
    && echo $ADMIN_USER > ./env \
    && unset ADMIN_USER
EXPOSE 8080
ADD build/libs/yaBot-1.0.jar yaBot-1.0.jar
ENTRYPOINT ["java", "-jar", "/yaBot-1.0.jar" ]
