# Deploy (Docker Compose)

Runs PostgreSQL, the Spring Boot API and Caddy (serves Angular, proxies `/api`, handles HTTPS).

Requirements on the server: Docker with the compose plugin, ~1.5 GB free RAM, ports 80/443 free.

```sh
git clone https://github.com/MckCieply/renovation-App.git && cd renovation-App
git checkout develop
sh deploy/init-env.sh demo.example.com   # or no argument for plain HTTP on the server IP
docker compose up -d --build
```

`init-env.sh` prints the generated admin password (login: `admin`). Secrets live in `.env` (not committed).

- Logs: `docker compose logs -f app`
- Update: `git pull && docker compose up -d --build`
- Stop: `docker compose down` (data stays in the `db_data` volume)

For HTTPS, point the domain's DNS A record at the server before the first start. Without a domain, `<ip-with-dashes>.sslip.io` works (e.g. `1-2-3-4.sslip.io`).
