FROM openjdk:17
CMD mkdir /apps
COPY build/libs/*.jar /apps/app.jar
EXPOSE 9080
ENTRYPOINT echo JAVA_OPTS=$JAVA_OPTS && java $JAVA_OPTS -jar /apps/app.jar