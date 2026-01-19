FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY . .
# ホスト側で実行権限を付与しなくても、Docker内部で実行権限を付与
RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

# ステージ2: 実行環境 (シンプルなコピー＆実行)
FROM eclipse-temurin:21-jdk
WORKDIR /app

# ホスト(PC)からではなく、上の 'builder' ステージから JAR を持ってくる
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]