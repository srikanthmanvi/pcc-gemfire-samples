package io.pivotal.support;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableGemFireProperties;
import org.springframework.data.gemfire.config.annotation.EnableLogging;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

import java.util.Optional;
import java.util.Properties;

@ClientCacheApplication(locators =
		{@ClientCacheApplication.Locator(host = "localhost", port = 10334)})
@EnableGemfireRepositories
//@EnableLogging(logFile = "AMYCLIENTLOG.log")
@EnableEntityDefinedRegions
@EnableGemFireProperties
@EnablePdx
public class ClientApp {

	public static void main(String args[]) {

		new SpringApplicationBuilder(ClientApp.class)
				.web(WebApplicationType.NONE)
				.build()
				.run(args);
	}
//
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer properties(){
//		PropertySourcesPlaceholderConfigurer pspc
//				= new PropertySourcesPlaceholderConfigurer();
//
//		PropertyS
//		pspc.
//		Properties properties = new Properties();
//		properties.setProperty("gemfire.ssl-endpoint-identification-enabled", "true");
//		properties.setProperty("RANDOMVAR", "true");
//		properties.setProperty("gemfire.log-file", "MySpringClient.log");
//		properties.setProperty("gemfire.durable-client-id", "MySpringClient");
//		pspc.setProperties(properties);
//		return pspc;
//	}


	//	@Profile("tls")
	@Bean
	Properties properties(@Qualifier("gemfireProperties") Properties gemfireProperties){
//		gemfireProperties.setProperty("ssl-use-default-context","true");
				System.setProperty("gemfire.ssl-use-default-context","true");
//				System.setProperty("gemfire.log-file","A1.log");
		return gemfireProperties;
	}

//	@Bean
//	public static Properties properties(@Qualifier("gemfireProperties") Properties properties){
//		PropertySourcesPlaceholderConfigurer pspc
//				= new PropertySourcesPlaceholderConfigurer();
////		Properties properties = new Properties();
////		properties.setProperty("gemfire.ssl-endpoint-identification-enabled", "true");
////		properties.setProperty("RANDOMVAR", "true");
////		properties.setProperty("log-file", "A123.log");
////		properties.setProperty("ssl-use-default-context", "true");
////		System.setProperty("gemfire.ssl-use-default-context", "true");
////		System.setProperty("gemfire.log-file", "BB.log");
////		properties.setProperty("gemfire.durable-client-id", "MySpringClient");
//
//		pspc.setProperties(properties);
//		return properties;
//	}

	/**
	 * Create a region using GFSH command
	 * create region --name=/Customer --type=REPLICATE --entry-idle-time-expiration=60 --entry-idle-time-expiration-action=DESTROY --enable-statistics
	 *
	 *
	 * 1. Loads 10 Customers into Customer region which has TTI set to 60 sec
	 * 2. Waits for 30 secs and does a get on Customer5
	 * 3. Waits for 50 secs and does a get on Customer5 again. Succesfully finds it.
	 * 4. Then does a get on Customer8 which will not be found as expected.
	 * @param customerRepository
	 * @return
	 */
	@Bean
	ApplicationRunner runner(CustomerRepository customerRepository, @Qualifier("systemProperties") Properties properties) {
		return args -> {

			System.out.println("$$$$$$$$$$ PROPERTY : "+properties.getProperty("RANDOMVAR"));
			for (int i = 100; i < 300; i++) {
				customerRepository.save(Customer.of(i,"Name"+i));
			}

//			Thread.sleep(30*1000);
//			Optional<Customer> firstAttemptCustomer = customerRepository.findById(5);
//			System.out.println("$$$ FirstAttempt "+firstAttemptCustomer.toString());
//
//			//TTI expiration set to 60 sec, So after this sleep, if customer
////			object is found then the lastaccesstime is updated correctly.
//			Thread.sleep(50 * 1000);
//
//			Optional<Customer> secondAttemptCustomer = customerRepository.findById(5);
//			System.out.println("$$$ SecondAttempt "+secondAttemptCustomer.toString());
//
//
//			//customer8 should not be found, as we did not a get within 60 sec of put
//			Optional<Customer> shouldNotBeFoundCustomer = customerRepository.findById(8);
//			if (shouldNotBeFoundCustomer.isPresent()){
//				System.err.println("$$$ ERROR FOUND EXPIRED ENTRY "+shouldNotBeFoundCustomer);
//			}else {
//				System.out.println("$$$ Expired customer not found");
//			}
		};
	}
}
