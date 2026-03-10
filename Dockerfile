FROM eclipse-temurin:8-jre

VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 设置Java的headless模式
ENV JAVA_OPTS="-Djava.awt.headless=true"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]
