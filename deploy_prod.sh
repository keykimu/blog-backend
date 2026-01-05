#!/bin/bash

# AWS Parameter Store から機密情報を取得
# AWS CLI を使ってSecureStringパラメータを復号化して取得
echo "Retrieving secrets from AWS Parameter Store..."

# DBパスワードを取得し、シェル変数に格納
DB_PASSWORD_FROM_AWS=$(aws ssm get-parameter \
    --name "/portfolio/prod/db/password" \
    --with-decryption \
    --query "Parameter.Value" \
    --output text)

# JWTシークレットを取得し、シェル変数に格納
JWT_SECRET_FROM_AWS=$(aws ssm get-parameter \
    --name "/portfolio/prod/jwt/secret" \
    --with-decryption \
    --query "Parameter.Value" \
    --output text)

# エラーチェック
if [ -z "$DB_PASSWORD_FROM_AWS" ] || [ -z "$JWT_SECRET_FROM_AWS" ]; then
    echo "ERROR: Failed to retrieve one or more parameters from AWS SSM."
    exit 1
fi

# 取得した変数を環境変数として渡し、prod設定で起動
export DB_PASSWORD=${DB_PASSWORD_FROM_AWS}
export JWT_SECRET=${JWT_SECRET_FROM_AWS}

echo "Secrets successfully retrieved."


# Docker コンテナ内でビルドを実行し、JARを取り出す
echo "Building application JAR on EC2 host..."

# 既存の build/libs をクリーンアップ (古いJARが残らないように)
rm -rf build/libs && mkdir -p build/libs

# Dockerfileの'builder'ステージのみを使用して一時イメージをビルド
docker build -t portfolio-builder --target builder .

# ビルド用の一時コンテナを作成し、ビルドを実行
# ここでビルドが実行されるが、SSHが切れてもDockerデーモンが処理を継続する
BUILD_CONTAINER=$(docker create portfolio-builder)

# コンテナ内部のビルド結果 (/app/build/libs/にあるJAR)をホストの build/libs にコピー
# JARファイル名が一つであることを前提
docker cp $BUILD_CONTAINER:/app/build/libs/. build/libs/

# 一時コンテナを削除
docker rm $BUILD_CONTAINER

# 証明書の保存先パス
CERT_DIR="./nginx/certs/live/portfolio-api.kimuworks.dev"

# ダミー証明書がなければ作る
CERT_DIR="./nginx/certs/live/portfolio-api.kimuworks.dev"
if [ ! -f "$CERT_DIR/fullchain.pem" ]; then
    echo "Creating 10-year self-signed certificate for Cloudflare..."
    sudo mkdir -p "$CERT_DIR"
    sudo openssl req -x509 -nodes -newkey rsa:2048 -days 3650 \
        -keyout "$CERT_DIR/privkey.pem" \
        -out "$CERT_DIR/fullchain.pem" \
        -subj "/CN=portfolio-api.kimuworks.dev"
    sudo chmod -R 755 ./nginx/certs
fi

# デプロイ実行
echo "Deploying with secure & simplified configuration..."
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build --remove-orphans

echo "Deployment completed! Secured by Self-signed cert & Cloudflare."