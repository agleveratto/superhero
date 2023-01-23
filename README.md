# Superhero app
This is a spring-boot app that retrieve, update and delete a superhero 
and connect to redis for cache the last data.

## Execute app
Go into project location and execute this commands

    # Generate jar app
    mvn clean install
    
    # Create docker image app
    `docker-compose build`

    # Run docker image
    `docker-compose up`

## Endpoints
### Authenticate user
For use the following endpoints, you need generate a token to one of these two users:

`POST`
``` http request
http://localhost:8080/api/v1/auth/authenticate
```
Body

```json
{
"email" : "agleveratto@gmail.com",
"password" : "password"
}
```

or 

```json
{
"email" : "enzo.guido@mindata.es",
"password" : "mindata"
}
```

Response 200
```json
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZ2xldmVyYXR0b0BnbWFpbC5jb20iLCJleHAiOjE2NzQ1ODM0MzMsImlhdCI6MTY3NDQ5NzAzMywiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV19.whtDXwR3U76OPycPcN8C-T5bkL9VuIaUVn_XPaq-dWs
```



### Superhero endpoints
Use this response into the following endpoints on bearer token in headers.

#### FindAll
This endpoint returns all the superheroes, the first time, from the database and then from a cache that expires every 10 mins. If the cache is expired, the database is queried again and then added to the cache.

`GET`
``` http request
http://localhost:8080/api/v1/
```

| HEADER | VALUE |
| ------ | ----- |
| Authorization | Bearer + response del authenticate |

Response 
```json
[
	{
		"id": 1,
		"name": "superman"
	},
	{
		"id": 2,
		"name": "batman"
	},
	{
		"id": 3,
		"name": "wonderwoman"
	},
//...
  {
    "id": 31,
    "name": "groot"
  }
]
```

#### FindById
This endpoint retrieve a superhero by id. If not exist into DB, retrieve a custom object with the message and status.

`GET`
``` http request
http://localhost:8080/api/v1/1
```

| HEADER | VALUE |
| ------ | ----- |
| Authorization | Bearer + response del authenticate |

Response 200
```json
{
	"id": 1,
	"name": "superman"
}
```

Response 404
```json
{
	"timestamp": "2023-01-23T21:19:52.239226",
	"message": "superhero not found by id 134"
}
```

#### FindByContains
This endpoint returns a superhero list if contains the string request in their name

`GET`
```http request
http://localhost:8080/api/v1/name/man
```

| HEADER | VALUE |
| ------ | ----- |
| Authorization | Bearer + response del authenticate |

Response 200
```json
	{
		"id": 1,
		"name": "superman"
	},
	{
		"id": 2,
		"name": "batman"
	},
	{
		"id": 3,
		"name": "wonderwoman"
	},
//...
    {
		"id": 29,
		"name": "mantis"
	}
```

Response 404 
```json
{
	"timestamp": "2023-01-23T21:24:13.308616",
	"message": "superheroes not found that contains the word [man213] into their name"
}
```

#### Update
This endpoint modify a superhero by id. If not exist into DB, retrieve a custom object with the message and status.

`PUT`
```http request
http://localhost:8080/api/v1/
```

| HEADER | VALUE |
| ------ | ----- |
| Authorization | Bearer + response del authenticate |

Body
```json
	{
		"id": 2,
		"name": "batman"
	}
```

Response 200
```json
superhero modified
```

Response 404
```json
{
	"timestamp": "2023-01-23T21:28:30.615758",
	"message": "superhero not found by id 241"
}
```

#### Delete
This endpoint delete a superhero by id. If not exist into DB, retrieve a custom object with the message and status.

`DELETE`
```http request
http://localhost:8080/api/v1/31
```

| HEADER | VALUE |
| ------ | ----- |
| Authorization | Bearer + response del authenticate |

Response 200
```json
superhero deleted
```

Response 404
```json
{
  "timestamp": "2023-01-23T21:48:12.318134",
  "message": "superhero not found by id 312"
}
```