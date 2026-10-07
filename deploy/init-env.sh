#!/usr/bin/env sh
# Generates .env with random secrets. Usage: deploy/init-env.sh [http-port]
# http-port: host port the app is published on, default 8090
set -e
cd "$(dirname "$0")/.."
if [ -f .env ]; then
  echo ".env already exists, not overwriting" >&2
  exit 1
fi
cat > .env <<ENV
HTTP_PORT=${1:-8090}
DB_PASSWORD=$(openssl rand -hex 16)
APP_JWT_SECRET=$(openssl rand -base64 32)
APP_ADMIN_PASSWORD=$(openssl rand -base64 12 | tr -d '/+=')
ENV
chmod 600 .env
echo "Created .env. Admin login: admin / $(grep APP_ADMIN_PASSWORD .env | cut -d= -f2)"
