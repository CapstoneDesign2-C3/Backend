FROM openjdk:21-jdk-slim
WORKDIR /app
ENV TZ=Asia/Seoul
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
COPY build/libs/control-automation-0.0.1-SNAPSHOT.jar /app/control-automation.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "control-automation.jar"]

EXPOSE 8080