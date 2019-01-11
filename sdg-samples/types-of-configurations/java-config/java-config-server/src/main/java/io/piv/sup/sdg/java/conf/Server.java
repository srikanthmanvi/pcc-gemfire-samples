package io.pivotal.support.sdg.java.conf;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;

@SpringBootApplication
@CacheServerApplication
public class Server {

	public static void main(String args[]) {
		new SpringApplicationBuilder(Server.class).web(WebApplicationType.NONE).build().run(args);
	}

	/**
	 * Creates a GemFire Cache Server
	 *
	 * @return
	 */
	@Bean CacheFactoryBean gemfireCache() {
		CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();
		cacheFactoryBean.setPdxReadSerialized(true);
		return cacheFactoryBean;
	}

	@Bean @Qualifier("Customer") PartitionedRegionFactoryBean customerRegion(Cache cache,
			@Qualifier("regionAttributes") RegionAttributes attributes) {
		PartitionedRegionFactoryBean partitionedRegionFactoryBean = new PartitionedRegionFactoryBean();
		partitionedRegionFactoryBean.setCache(cache);
		partitionedRegionFactoryBean.setRegionName("Customer");
		partitionedRegionFactoryBean.setAttributes(attributes);
		return partitionedRegionFactoryBean;
	}

	@Bean RegionAttributesFactoryBean regionAttributes(Cache cache, PartitionAttributes partitionAttributes) {
		RegionAttributesFactoryBean regionAttributesFactoryBean = new RegionAttributesFactoryBean();
		regionAttributesFactoryBean.setPartitionAttributes(partitionAttributes);
		return regionAttributesFactoryBean;
	}

	@Bean PartitionAttributesFactoryBean partitionAttributesFactoryBean() {
		PartitionAttributesFactoryBean partitionAttributesFactoryBean = new PartitionAttributesFactoryBean();

		partitionAttributesFactoryBean.setRedundantCopies(1);
		partitionAttributesFactoryBean.setLocalMaxMemory(600);

		return partitionAttributesFactoryBean;
	}

}
