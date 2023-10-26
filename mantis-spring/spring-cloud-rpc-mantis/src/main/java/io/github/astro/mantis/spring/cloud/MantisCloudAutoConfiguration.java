package io.github.astro.mantis.spring.cloud;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

public class MantisCloudAutoConfiguration {

    @Bean
    public ServiceRegistrationPostProcessor serviceRegistrationPostProcessor() {
        return new ServiceRegistrationPostProcessor();
    }

    @Bean
    public SpringDirectoryFactoryBean springDirectoryFactoryBean(ApplicationContext context) {
        return new SpringDirectoryFactoryBean(context);
    }

//    @Bean
//    @ConditionalOnClass({ConsulAutoServiceRegistration.class})
//    public DiscoveryController discoveryController() {
//        return new DiscoveryController();
//    }
}
