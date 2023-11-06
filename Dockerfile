# Stage 1: Build the application
FROM krmp-d2hub-idock.9rum.cc/goorm/gradle:8.2.1-jdk17

WORKDIR /home/gradle/project

COPY ./animory .

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN gradle wrapper

RUN gradle clean bootJar

CMD ["java", "-jar", "-Dspring.profiles.active=production", "-Duser.timezone=Asia/Seoul", "/home/gradle/project/build/libs/animory-0.0.1-SNAPSHOT.jar"]
