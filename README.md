TO RUN THE APPLICATION:

inside terminal/command promt(windows) navigate to project:$ cd my-projects/UserApp
to download the project dependecies and build run $ ./gradlew clean build 
to run the project run $ ./gradlew bootRun

interact with the app : localhost:8080

FUNCTIONALITY:

greet visitor : GET localhost:8080/greeting?name=Adrian
  query param is optional and defaults to "hello world"

query parameter and header validation : GET localhost:8080/give-number?number=3    
  accepts only integer query param and header field "Session-Token" is mandatory. Value can be any string.

register: POST http://localhost:8080/register
  json body {
    "username" : mandatory | max size 5 |
    "password" : mandatory | min size 5 | max size 10 |
    "email" : mandatory | max size 20 | typical email format validation |
  }
  
show all users: GET http://localhost:8080/users
  Basic Auth : username | password
