FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
# ホスト側で実行権限を付与しなくても、Docker内部で実行権限を付与
RUN chmod +x gradlew
RUN ./gradlew build -x test

# ステージ2: 実行環境 (シンプルなコピー＆実行)
FROM eclipse-temurin:21-jdk
WORKDIR /app
# ビルドステージから生成されたJARファイルのみをコピーする
# ただし、今回は deploy.sh でJARを取り出すため、ここはホスト側のJARをコピーするように戻す
# **[変更]** ステージ1からのコピーではなく、ホスト側の build/libs をコピーする
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]