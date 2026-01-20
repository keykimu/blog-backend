# Portfolio API

このリポジトリは、ポートフォリオサイト用のバックエンド API です。  
Vue + TypeScript で作られたフロントエンド（公開ページ・管理者ページ）と連携してデータを提供します。

---

## 技術スタック
- バックエンド: Spring Boot / Java
- データベース: PostgreSQL 15 (Docker)
- DBマイグレーション:Flyway
- ORM / DB マッピング: MyBatis
- API ドキュメント: Springdoc OpenAPI + Swagger UI
- コンテナ管理: Docker, docker-compose
---

## インフラ・セキュリティ
- Webサーバー/リバースプロキシ: Nginx
- SSL/TLS: Cloudflare (Full Mode) + 自己署名証明書による Origin Shielding
- ネットワーク: AWS セキュリティグループによる Cloudflare IP 帯域制限
- 機密情報管理: AWS Systems Manager (Parameter Store) による環境変数の秘匿化
---

## 認証・認可
- 認証方式: JWT (JSON Web Token) を採用
- トークン管理: セキュアCookie（HttpOnly / Secure / SameSite）を利用した堅牢な管理
- パスワード処理: パスワードのハッシュ化には、業界標準の強力な暗号化アルゴリズム(BCrypt)を採用
---

## CI/CD・運用
- CI/CD: GitHub Actions による継続的デプロイ (CD)
- main ブランチへのプッシュを検知し、EC2 上でビルド・デプロイを自動実行
- データベース管理: シェルスクリプトによる定期バックアップを実行し、AWS S3へ外部保存することでデータの冗長性を確保
- ログ管理: Dockerボリュームとホスト側の logrotate を連携させ、ディスク容量を圧迫しないようログの世代管理・自動削除を実施
---

## テスト
- 単体・結合テスト: JUnit 5, Mockito
- APIテスト: curl および Swagger UI による検証
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
- URL: `http://localhost/swagger-ui/index.html`
- ここで API エンドポイント、リクエスト/レスポンス例を確認可能
- Springdoc OpenAPI を使用