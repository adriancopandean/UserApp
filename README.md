TO RUN THE APPLICATION:

Open terminal / command prompt(windows) & navigate to project: ---> $ cd my-projects/UserApp

Download the project dependencies: ---> $ ./gradlew clean build 

Run the application ---> $ ./gradlew bootRun

Application is running @ localhost:8080

FUNCTIONALITY:

1. GET localhost:8080/greeting?name=Adrian         
   query param is optional and defaults to "hello world"


2. GET localhost:8080/give-number?number=3    
  accepts only integer query param and header field "Session-Token" is mandatory. Value can be any string.


3. POST http://localhost:8080/register      
  json body {
    "username" : mandatory | max size 5 |
    "password" : mandatory | min size 5 | max size 10 |
    "email" : mandatory | max size 20 | typical email format validation |
  }
  

4. GET http://localhost:8080/users    
  Basic Auth : username | password
