# Deploy (Docker Compose)

Runs PostgreSQL, the Spring Boot API and Caddy (serves Angular, proxies `/api`,
gets the HTTPS certificate). Every container has a memory and CPU cap
(~700 MB RAM in total), so the stack can share a server with other apps.

Requirements on the server: Docker with the compose plugin, ~1.5 GB free RAM
for the first build, ports 80/443 free and open in the firewall, and the
domain's DNS A record pointing at the server.

```sh
git clone -b develop https://github.com/MckCieply/renovation-App.git && cd renovation-App
sh deploy/init-env.sh demo.example.com
docker compose up -d --build
```

`init-env.sh` prints the generated admin password (login: `admin`). Secrets
live in `.env` (not committed).

- Logs: `docker compose logs -f app` (certificate issues: `docker compose logs web`)
- Resource use: `docker stats --no-stream`
- Update: `git pull && docker compose up -d --build`
- Stop: `docker compose down` (data stays in the `db_data` volume)

If another app on the server needs 80/443 later, its reverse proxy has to
serve this domain instead: remove the `ports` of `web`, publish it on a
local port (e.g. `127.0.0.1:8090:80` with `SITE_ADDRESS=:80`) and proxy the
domain there, e.g. in a Caddyfile:
`demo.example.com { reverse_proxy localhost:8090 }`.
