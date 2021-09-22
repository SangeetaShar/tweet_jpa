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

# API endpoints

* /api-endpoints.md

There are 3 different endpoints

### Authentication 
These endpoints enable the login and logout functionality

#### POST
 
    /api/auth/login 
    Content-type:application/Json
    Request : 
    {
    "password": "<password>",
    "username": "<username>"
    }
    Response: 200 - with {"access_token":"some valid token"}

#### GET

    /api/auth/logout - logout and invalidates the token.
    Response: 200, 400, 401, 500 (depending upon the process)
    
    /api/me - It fetches the current authenticated user
    Response: 
    {
    }


### User
    It is set of rest endpoints to handle the user operations but only available for admin

#### POST

    /api/users 
    Content-type:application/Json
    Request : 
    {
        "username": "<username>",
        "password": "<password>",
        "name": "<name>"
    }
    Note: username has to be unique.
    Response: 201 - with 
    {
        "username":"<data sent>", 
        "name":"<name>"
    }
    Can be an Error if tweetlength is greater then 160
    401 - Unauthorized
    404 - Not Found
    500 - Internal server error

#### GET

    /api/users - 
    It fetches all users.
    Response: 200, 400, 401, 500 (depending upon the process)
    200 Response - application/jsomn
    [
    {
        "username": "admin",
        "name": "Admin"
    },
    {
        "username": "user1",
        "name": "User1"
    },..
    ]
    
    /api/users/{username} - 
    It fetches the user by username
    Response: 
    200 with data as 
        {
            "username": "admin",
            "name": "Admin"
        }
   

    401 - Unauthorized
    404 - Not Found
    500 - Internal server error

### Tweet
    It is set of rest endpoints to handle the tweet operations
#### POST

    /api/tweet 
    Content-type:application/Json
    Request : 
    {
        "tweetData": "<tweet - not more the 160 words>"
    }
    Response: 201 - with 
    {
        "tweetData":"<data sent>", 
        "tweetData":<current timestamp>
    }
    Can be an Error if tweetlength is greater then 160
    401 - Unauthorized
    404 - Not Found
    500 - Internal server error

#### GET

    /api/tweet - 
    It fetches all tweets by other users excluding the current logged in user.
    Response: 200, 400, 401, 500 (depending upon the process)
    200 Response - application/jsomn
    [
    {
        "tweetData": "This is user2 tweets 2.",
        "tweetDate": "2021-09-20T23:00:00.000+0000"
    },
    {
        "tweetData": "This is user3 tweets 1.",
        "tweetDate": "2021-08-20T23:00:00.000+0000"
    },
    {
        "tweetData": "This is user2 tweets 1.",
        "tweetDate": "2021-08-20T13:00:00.000+0000"
    }
    ...
    ]
    
    /api/tweet/myTweets - 
    It fetches the current authenticated user tweets only
    Response: 
    [] - if no tweets are available
    [
    {
        "tweetData": "This is user1 tweets 1.",
        "tweetDate": "2021-09-20T23:00:00.000+0000"
    },
    {
        "tweetData": "This is user1 tweets 1.",
        "tweetDate": "2021-09-20T23:00:00.000+0000"
    }, ...
    ]

#### PUT

    /api/tweet/{id}
    Content-type:application/Json
    Request : 
    {
        "tweetData": "<tweet - not more the 160 words>"
    }
    Response: 200 - with 
    {
        "tweetData":"<data sent>", 
        "tweetData":<current timestamp>
    }
    Can be an Error if tweetlength is greater then 160
    401 - Unauthorized
    404 - Not Found
    500 - Internal server error

#### DELETE

    /api/tweet/{id}
    Content-type:application/Json
    Response: 
    200 - OK 
    204 - NOT Found
    401 - Unauthorized
    500 - Internal server error


