package sdg.annotation;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import sdg.annotation.domain.Customer;

@SpringBootApplication
@CacheServerApplication
@EnableEntityDefinedRegions(basePackageClasses = Customer.class)
@EnableLocator
@EnableManager(start = true)
public class ServerAndLocator {

  public static void main(String args[]) {
    new SpringApplicationBuilder(ServerAndLocator.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);
  }

  @Autowired
  Region customerRegion;

  @Bean
  ApplicationRunner applicationRunner() {
    return args -> {
      customerRegion.put(2, "AA");
      System.out.println("########## Completed Data Load");
    };
  }
}
