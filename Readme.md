# Getting Started

This service provides apis to manage job and support following features :

* submit job
* retrieve job by id
* retrieve all jobs

### Code Usage 

**Build it** : *mvnw.cmd clean install*

**Run It** : *java -jar target\jm-service-0.0.1-SNAPSHOT.jar*

**API Documentation** http://localhost:8080/api/swagger-ui.html

### Dev Operations
[Job Management Service Travis CI Build](https://travis-ci.org/CaseStudy-JobManagement/jm-service)
: Have integrated code repo with Travis CI which builds the app does following :

* perform and publish, code analysis and code coverage result to sonar cloud.
    * [Job Management Service Sonar Report](https://sonarcloud.io/dashboard?id=CaseStudy-JobManagement_jm-service)
* The service is currently hosted on Heroku on free dyno.
    * [Job Management Service Live](https://jm-service.herokuapp.com/api/swagger-ui.html)
    
### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

