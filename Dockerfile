# ステージ1: ビルダー
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
# ビルドに必要なファイルだけをコピー
COPY . .
RUN chmod +x gradlew
# Gradle のビルドを実行し、JARファイルを生成
RUN ./gradlew build -x test

# ステージ2: 実行環境
FROM eclipse-temurin:21-jdk
WORKDIR /app
# ビルドステージから生成されたJARファイルのみをコピーする
COPY --from=builder /app/build/libs/*.jar app.jar