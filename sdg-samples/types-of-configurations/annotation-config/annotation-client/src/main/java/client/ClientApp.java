package client;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableClusterConfiguration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.config.annotation.EnableLogging;
import org.springframework.data.gemfire.config.annotation.EnablePdx;

import java.util.Properties;

@SpringBootApplication
@ClientCacheApplication(
    locators = {@ClientCacheApplication.Locator(port = 10334, host = "localhost")})
@EnableLogging(logFile = "MyClientLog.txt")
@EnableEntityDefinedRegions
@EnablePdx
@EnableClusterConfiguration
@EnableGemFireProperties
public class ClientApp {

  public static void main(String args[]) {

    new SpringApplicationBuilder(ClientApp.class)
        .web(WebApplicationType.NONE)
        .build()
        .run(args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer properties(){
    PropertySourcesPlaceholderConfigurer pspc
        = new PropertySourcesPlaceholderConfigurer();
    Properties properties = new Properties();
    properties.setProperty("gemfire.durable-client-id", "MySpringClient");
    pspc.setProperties(properties);
    return pspc;
  }


  @Bean
  ApplicationRunner runner(Region customerRegion,PropertySourcesPlaceholderConfigurer obj ) {
    return args -> {
      customerRegion.put(123, "HELLO");
      Object value = customerRegion.get(2);
      System.out.printf("########## VALUE IS " + value);
    };
  }
}
