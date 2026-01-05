# Portfolio API

このリポジトリは、ポートフォリオサイト用のバックエンド API です。  
Vue + TypeScript で作られたフロントエンド（公開ページ・管理者ページ）と連携してデータを提供します。

---

## 技術スタック
- バックエンド: Spring Boot / Java
- データベース: PostgreSQL 15 (Docker)
- Webサーバー/リバースプロキシ:Nginx & Let's Encrypt(HTTPS)
- DBマイグレーション:Flyway
- ORM / DB マッピング: MyBatis
- 認証: JWT (JSON Web Token)
- API ドキュメント: Springdoc OpenAPI + Swagger UI
- コンテナ管理: Docker, docker-compose
---

## 認証・セキュリティ設定
* **認証方式**: JWT (JSON Web Token) を採用しています。
* **トークン管理**: 認証情報は、セキュリティを考慮し、セキュアなCookieを利用して管理しています。
* **パスワード処理**: パスワードのハッシュ化には、業界標準の強力な暗号化アルゴリズムを採用しています。
---

## 開発環境の起動
本プロジェクトは Docker Compose で構築されており、以下のコマンドで全コンポーネントを起動できます。

```bash
docker compose up -d --build
```
---

## 開発時のヒント
### コードの修正を反映する
Java のソースコードを書き換えた後など
```bash
docker compose up -d --build app
```

### ログを確認する
バックエンドの動作ログやエラーを確認したい場合
```bash
docker compose logs -f app
```

### PostgreSQL に接続（開発用）
```bash
docker exec -it [DB_CONTAINER_NAME] psql -U [DB_USERNAME] -d [DB_NAME]
```
---

## Swagger / OpenAPI
- 開発環境のみ有効化（本番環境ではセキュリティ上の理由により無効）
- URL: `http://localhost/api/swagger-ui/index.html`
- ここで API エンドポイント、リクエスト/レスポンス例を確認可能
- Springdoc OpenAPI を使用