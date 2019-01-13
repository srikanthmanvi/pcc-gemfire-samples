# gemfire-samples
### Quick start samples for GemFire, Pivotal Cloud Cache (pcc), Spring Data GemFire, Springboot for Gemfire/Geode

Goal of this repository is to contain sample code/configurations to spin up all-things-gemfire.
This project contains multiple maven modules which are to be loosely coupled to each other and can be run independently.

#### Structure
- `bootstrap` 
    
    Has script (_startServers.sh_) to spin up a GemFire cluster on localhost.
    You need to spin up a cluster before running the client side samples from other modules.
    
- `pcc-samples`

    Has samples demonstrating how to talk to PCC from within PCF. These samples use
    Spring boot for Pivotal GemFire to start the the application.
       
- `sdg-samples` 

  Has examples of [spring-data-gemfire/spring-data-geode](https://docs.spring.io/spring-data-gemfire/docs/current/reference/html/)
- `spring-boot-for-gemfire-geode-samples`

   Has example of running using [spring boot data geode](https://github.com/spring-projects/spring-boot-data-geode). docs are [here](https://docs.spring.io/autorepo/docs/spring-boot-data-geode-build/1.0.0.BUILD-SNAPSHOT/reference/htmlsingle/)

- `vanilla-gemfire-samples`

   Has samples of gemfire client/server code using GemFire java native apis. 
   spring/spring-boot/sdg/pcc is not involved in this module. This module has 
   samples for advanced GemFire usecases like WAN replication etc. 
  

Note:
- Each of these modules further has a README file which explains further details.
- Contributions are welcome using Pull Request. 
