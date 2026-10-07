#!/usr/bin/env sh
# Generates .env with random secrets. Usage: deploy/init-env.sh [site-address]
# site-address: domain for HTTPS (e.g. demo.example.com), default ":80" (plain HTTP)
set -e
cd "$(dirname "$0")/.."
if [ -f .env ]; then
  echo ".env already exists, not overwriting" >&2
  exit 1
fi
cat > .env <<ENV
SITE_ADDRESS=${1:-:80}
DB_PASSWORD=$(openssl rand -hex 16)
APP_JWT_SECRET=$(openssl rand -base64 32)
APP_ADMIN_PASSWORD=$(openssl rand -base64 12 | tr -d '/+=')
HTTP_PORT=80
HTTPS_PORT=443
ENV
chmod 600 .env
echo "Created .env. Admin login: admin / $(grep APP_ADMIN_PASSWORD .env | cut -d= -f2)"
