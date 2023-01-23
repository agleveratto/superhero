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
For use the following endpoints, you need generate a token to one of these two users:

`{
"email" : "agleveratto@gmail.com",
"password" : "password"
}`

or 

`{
"email" : "agleveratto@gmail.com",
"password" : "password"
}`
