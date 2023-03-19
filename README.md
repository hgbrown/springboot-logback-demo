# Demo: Logging using Logback in Spring Boot

Demonstrates how to configure logging in a Spring Boot (3) application by specifying an external `logback.xml` file.
We use the system property: `logging.config` to point the application at the logging file when we run the application.

Demo Video: [Debugging Your Spring Boot 3 App with Effective Logging](https://youtu.be/iBRB_WqqMSo)

## Logging Configuration

In the logging configuration we set the following loggers and levels explicitly:

| Logger (name)       | Level |
|---------------------|-------|
| root                | WARN  |
| za.co               | INFO  |
| org.apache          | OFF   |
| org.springframework | OFF   |

There are 2 file appenders:

1) `ErrorFileAppender` for all error level logs; and
2) `NoHupFileAppender` for all other levels

## Actuator Endpoints

We use the actuator endpoints to see the logging levels at runtime:

```shell
curl -X GET --location "http://localhost:8080/actuator/loggers" \
    -H "Accept: application/json"
```

For example, we can see the logging level of the logger: `za.co.vine.springbootlogbackdemo.controller.HomeController` as follows:

```shell
curl -X GET --location "http://localhost:8080/actuator/loggers/za.co.vine.springbootlogbackdemo.controller.HomeController" \
    -H "Accept: application/json"
```

We can also change the logging level at runtime:

```shell
curl -X POST --location "http://localhost:8080/actuator/loggers/za.co.vine.springbootlogbackdemo.controller.HomeController" \
    -H "Content-Type: application/json" \
    -d "{
          \"configuredLevel\": \"ERROR\"
        }"
```

## Running the application

```shell
java -Dlogback.debug=true -Dlogging.config=./logback.xml -jar build/libs/springboot-logback-demo.jar --spring.profiles.active=dev
```
We set the `logback.debug` property to `true` so we can confirm the logging levels are set correctly as specified in our logging configuration.

We set the logging configuration file using the `logging.config` property to the path where our logging configuration file is located (in this case in the same directory where we are running the command from).

We set the active profile to `dev` to enable all actuator endpoints for web access.
