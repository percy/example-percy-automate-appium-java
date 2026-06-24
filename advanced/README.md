# Advanced App Percy on Automate + Appium-Java

Exercises the full applicable App Percy on Automate feature surface for `io.percy:percy-appium-app` (PercyOnAutomate class).

## Run locally

```bash
cd advanced
make install
export BROWSERSTACK_USERNAME="<your username>"
export BROWSERSTACK_ACCESS_KEY="<your access key>"
export APP="bs://<your hashed app id>"
export PERCY_TOKEN="<your project token>"
make test
```

## CI note

`workflow_dispatch`-only — App Percy on Automate CI requires a real BrowserStack App Automate session.

## Coverage matrix

Source of truth: [`matrix.yml`](./matrix.yml).
