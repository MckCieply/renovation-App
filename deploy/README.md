# Deploy (Docker Compose)

Runs PostgreSQL, the Spring Boot API and Caddy (serves Angular, proxies `/api`).
Built to share a server with other apps: it publishes only `HTTP_PORT`
(default 8090, never 80/443) and caps every container's memory and CPU
(~700 MB RAM in total).

Requirements on the server: Docker with the compose plugin, ~1.5 GB free RAM
for the first build, `HTTP_PORT` open in the firewall.

```sh
git clone -b develop https://github.com/MckCieply/renovation-App.git && cd renovation-App
sh deploy/init-env.sh          # optional argument: another port than 8090
docker compose up -d --build
```

The app is then at `http://<server>:8090`. `init-env.sh` prints the generated
admin password (login: `admin`). Secrets live in `.env` (not committed).

- Logs: `docker compose logs -f app`
- Resource use: `docker stats --no-stream`
- Update: `git pull && docker compose up -d --build`
- Stop: `docker compose down` (data stays in the `db_data` volume)

HTTPS is not handled here. To serve it on a domain with HTTPS, point the
server's main reverse proxy at `HTTP_PORT`, e.g. in a Caddyfile:
`renovation.example.com { reverse_proxy localhost:8090 }`.
