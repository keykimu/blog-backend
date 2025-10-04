# Portfolio API

このリポジトリは、ポートフォリオサイト用のバックエンド API です。  
Vue + TypeScript で作られたフロントエンド（管理者ページ・公開ページ）と連携してデータを提供します。

---

## 技術スタック
- バックエンド: Spring Boot 3.5.6
- データベース: PostgreSQL 15 (Docker)
- ORM / DB マッピング: MyBatis 3.0.5
- DTO マッピング: MapStruct 1.6.3
- コード簡略化: Lombok
- API ドキュメント: Springdoc OpenAPI 2.8.13 + Swagger UI
- コンテナ管理: Docker, docker-compose
- 言語: Java 21

---

## 開発環境の構築

### 1. Docker で PostgreSQLとバックエンドを起動
```bash
docker-compose up --build
```

## 開発時の便利手順

### 1. Spring Boot をビルド
```bash
./gradlew build
```

### 2. アプリをローカルで起動
```bash
./gradlew bootRun
```

### 3. PostgreSQL に接続（開発用）
```bash
docker exec -it portfolio-postgres psql -U postgres -d portfolio
```

## Swagger / OpenAPI

- 開発環境のみ有効化
- URL: http://localhost:8080/swagger-ui.html
- ここで API エンドポイント、リクエスト/レスポンス例を確認可能
- Springdoc OpenAPI 2.8.13 を使用