# gemfire-samples
### Quick start samples for GemFire, Spring Data GemFire, Springboot for Gemfire/Geode

Goal of this repository is to contain sample code/configurations to spin up all-things-gemfire.
This project contains multiple maven modules which are very losely coupled to each other and can be run independently.

#### Structure
- `pcc-samples`

    Has samples demonstrating how to talk to PCC from within PCF and outside of PCF.   
- `sdg-samples` 

  Has examples of [spring-data-gemfire/spring-data-geode](https://docs.spring.io/spring-data-gemfire/docs/current/reference/html/)
- `spring-boot-for-gemfire-geode-samples`

   Has example of running using [spring boot data geode](https://github.com/spring-projects/spring-boot-data-geode). docs are [here](https://docs.spring.io/autorepo/docs/spring-boot-data-geode-build/1.0.0.BUILD-SNAPSHOT/reference/htmlsingle/)

- `vanilla-gemfire-samples`

   Has samples of gemfire client/server code using native apis. spring/spring-boot/sdg/pcc is not involved in this module.
  

Note:
- Each of these modules further has a README file which explains further details.
- Contributions are welcome using Pull Request. 
