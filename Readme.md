# Getting Started

### Project Architecture

### Project Modules
The project consists of following modules:
* [Job Management Service](https://github.com/CaseStudy-JobManagement/jm-service) : spring boot service for submitting and retrieving jobs, with following features.
    * Submit job
    * Retrieve job by id
    * Retrieve all jobs
* Job Modules
    * [Job Starter](https://github.com/CaseStudy-JobManagement/jm-job-starter) : spring boot starter job for creating jobs. 
    * [Job Logger](https://github.com/CaseStudy-JobManagement/jm-job-logger) : simple job which logs 'Hello'. 
    * [Job Weather Report Dweet](https://github.com/CaseStudy-JobManagement/jm-job-weather-report-dweet) : retrieves the weather report for specified city from 'openweathermap' API's and push the same to dweet thing service. 
    * [Job Weather Report Email](https://github.com/CaseStudy-JobManagement/jm-job-weather-report-email) : retrieves the weather report for specified city from 'openweathermap' API's and emails the same to specified mail id.

### Build & Run
* Build [Job Starter](https://github.com/CaseStudy-JobManagement/jm-job-starter) : job starter is to be built before building any job.
    * **Build it** : *mvnw.cmd clean install*

* Build Actual Jobs:
    * [Job Logger](https://github.com/CaseStudy-JobManagement/jm-job-logger)  
    * [Job Weather Report Dweet](https://github.com/CaseStudy-JobManagement/jm-job-weather-report-dweet) 
    * [Job Weather Report Email](https://github.com/CaseStudy-JobManagement/jm-job-weather-report-email)
        * **Build it** : *mvnw.cmd clean package*
    **Note**: the generated jar files are to be used as job files for job service.

* Build Job Service:        
    * **Build it** : *mvnw.cmd clean package*
    * **Run It** : *java -jar target\jm-service-0.0.1-SNAPSHOT.jar*
    * **Test Via API Swagger** http://localhost:8080/api/swagger-ui.html

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

