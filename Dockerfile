FROM eclipse-temurin:21-jdk

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# アプリケーションの起動コマンド
ENTRYPOINT ["java","-jar","/app/app.jar"]