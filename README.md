##### Initial
```
 $ cp persister/src/main/resources/application.properties.dist persister/src/main/resources/application.properties
 $ cp app/src/main/resources/config.json.dist app/src/main/resources/config.json
```

##### Edit
```
persister/src/main/resources/application.properties
app/src/main/resources/config.json
```

##### Install

```
 $ persister/gradlew build
 $ ./gradlew install
```


##### Run app:
```
 $ java -jar persister/build/libs/persister-1.0-SNAPSHOT.jar
 $ app/build/install/app/bin/app --source example
```

##### Alternative
```Check .docker/README.md```