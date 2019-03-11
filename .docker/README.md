##### Initial
```
 $ cp persister/src/main/resources/application.properties.dist persister/src/main/resources/application.properties
 $ cp app/src/main/resources/config.json.dist app/src/main/resources/config.json
 $ cd PROJECT_DIR/.docker
 $ cp .env.dist .env
```

##### Edit
```
persister/src/main/resources/application.properties
app/src/main/resources/config.json
.docker/.env
```

##### Install

Start docker containers for persister & mongo.

```
 $ cd .docker
 $ docker-compose docker-compose.yml up -d
```


##### Run app:
```
 $ docker-compose -f extra.yml run app
```
