# tweet_jpa

# Build Secured Rest APIs with Spring Security and Postgres 

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. Postgres - 13.X

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/SangeetaShar/tweet_jpa.git
```

**2. Build and run the app using maven**

This will run the app with in Memory database. 
If you have postgres running update the application-local.properties with postgres 
properties and run the 'SpringBootRestApiSecuredApplication.java' as spring boot with profile 'local'

```bash
cd tweet_jpa
mvn clean install
java -jar target//tweet_jp.0-exec.jar 
```

Alternatively, you can run the app with docker, 
this will start the postgres and deploy the app -

```bash
docker-compose up
```

The server will start at <http://localhost:8080>.

## Exploring the Rest APIs

http://localhost:8080/swagger-ui.html 

As Api are secure, you first need to login with endpoint 
'/api/auth/login' - On successful login it will generate the accesstoken. 

On SwaggerUi page on 'Authorize button' populate the token field as 'Bearer <generated token>'

Now you can test the api.

UserApi is only available to Admin user. 