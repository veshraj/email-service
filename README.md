# emailservice

## Project setup
```
./gradlew assemble
```

### Create new files and application.properties file fro

Rename the application.properties.example to application.properties

### Run project 
```
./gradlew bootRun
```

### Clone vuejs repository which consumes APIs of this api
See [VueJs Repository](https://github.com/veshraj/email-service-vuejs).

### Update the vuejs store file if springboot application not running in 8080
edit the file of vuejs repository of path - src/store/index.js (replace 8080 port by currently running port)

