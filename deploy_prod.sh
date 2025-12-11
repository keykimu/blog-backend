#!/bin/bash

# --- 1. AWS Parameter Store から機密情報を取得 ---
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

echo "Secrets successfully retrieved."

# --- 2. Docker Compose コマンドの実行 ---

# 取得した変数を環境変数として渡し、prod設定で起動
DB_PASSWORD=${DB_PASSWORD_FROM_AWS} \
JWT_SECRET=${JWT_SECRET_FROM_AWS} \
docker compose \
  -f docker-compose.yml \
  -f docker-compose.prod.yml \
  up -d --build

echo "Production services deployed."