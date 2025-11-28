# Cloud Run用Node.js Dockerfile - 最もシンプルで信頼性が高い
FROM node:18-alpine AS builder

# 作業ディレクトリを設定
WORKDIR /app

# package.jsonとpackage-lock.jsonをコピー
COPY package*.json ./

# すべての依存関係をインストール
RUN npm ci

# ソースコードをコピー
COPY . .

RUN npm run build

# 本番段階 - Node.jsで静的ファイルを提供
FROM node:18-alpine AS production

# 作業ディレクトリを設定
WORKDIR /app

# serveパッケージをグローバルにインストール
RUN npm install -g serve

# ビルド段階からビルド成果物をコピー
COPY --from=builder /app/dist ./dist

# ポートを公開
EXPOSE 8080

# サーバーを起動
CMD ["sh", "-c", "echo 'Starting production server on port ${PORT:-8080}' && serve -s dist -l ${PORT:-8080}"]