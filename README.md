# Template sample

> from fmk-template version 0.0.3

## run inside docker

The whole app can be run with docker compose.

```
cd docker
docker compose up
```

## local run

### prerequisites

- JDK >= 17 <= 21 (tested with sdkman 17.0.2-open 20.0.1-open 21.0.2-open)
- nodejs (tested with nodejs v20.7.0)
- psql 15 (tested with 15.6)
    - and psql tooling (createdb)

### bootstrap database

```
createdb templatesample
```

### Run Kotlin

The backend can be run with command line or as any Kotlin app in IntelliJ.

#### command line

```
../gradlew bootRun
```

#### IntelliJ

Just run TemplateSampleApplication.kt as a Kotlin app.

### Run React dev

```
cd template-sample-client
yarn ; yarn start
```

## open in browser

http://127.0.0.1:8123/
