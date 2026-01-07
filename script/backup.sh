#!/bin/bash

# --- 設定エリア ---
BACKUP_NAME="db_backup_$(date +%Y%m%d_%H%M%S).sql"
S3_BUCKET="portfolio-db-backup"
CONTAINER_NAME="portfolio-postgres"

# 1. DockerコンテナからDBダンプを書き出す
# ※コンテナ内でpg_dumpを実行して、ホスト側に保存する
docker exec $CONTAINER_NAME pg_dump -U prod_user portfolio_prod > $BACKUP_NAME

# 2. S3へアップロード
aws s3 cp $BACKUP_NAME s3://$S3_BUCKET/$(date +%Y)/$BACKUP_NAME

# 3. サーバーに残った一時ファイルを削除
rm $BACKUP_NAME

echo "Backup completed: $BACKUP_NAME"
