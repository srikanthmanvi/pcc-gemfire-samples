package client;

import org.apache.geode.cache.Region;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;

@SpringBootApplication
//@ClientCacheApplication(locators = {@ClientCacheApplication.Locator(port = 10334, host = "localhost")})
@EnableEntityDefinedRegions
public class ClientApp {

  public static void main(String args[]) {

    System.out.printf("$$$$$$$$ STARTING CLIENT APP");
    new SpringApplicationBuilder(ClientApp.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);
  }


  @Bean
  ApplicationRunner runner(Region customerRegion) {

    return args -> {
      System.out.println(" $$$$$$$$$$$$$$$$$ LOADING CUSTOMER REGION");
      for (int i = 0; i < 100; i++) {
        customerRegion.put(i, new Customer("name"+i, i+10));
      }
      System.out.println(" $$$$$$$$$$$$$$$$$ DONE LOADING CUSTOMER REGION");

    };
  }
}
