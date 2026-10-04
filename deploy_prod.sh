#!/bin/bash

# エラーが発生したら即座に停止
set -e

echo "Syncing source code with GitHub..."
cd /home/ec2-user/portfolio/backend/blog-backend/
git pull origin main

echo "Cleaning up old docker resources to save space..."
# タグの有無にかかわらず、どのコンテナからも参照されていないイメージを削除
# 実行中コンテナが使うイメージと DB ボリュームは削除されない
docker image prune -af

# 未使用のビルドキャッシュをすべて削除
docker builder prune -af

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


# 証明書の保存先パス
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
# --build でソースの変更を反映し、--remove-orphans で古い不要なコンテナを削除
echo "Building and Deploying with Docker Compose..."
docker compose -f docker-compose.yml -f docker-compose.prod.yml up -d --build --remove-orphans

# ビルド後に不要になったイメージとビルドキャッシュを再度削除
docker image prune -af
docker builder prune -af

echo "Deployment completed! Secured by Self-signed cert & Cloudflare."
