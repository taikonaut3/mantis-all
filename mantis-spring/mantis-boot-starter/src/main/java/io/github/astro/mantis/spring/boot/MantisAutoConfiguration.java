package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.configuration.MantisApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class MantisAutoConfiguration {

    @Bean
    public MantisApplication mantisBootStrap() {
        return new MantisApplication();
    }

    @Bean
    public MantisConfigurationProperties mantisConfigurationProperties() {
        return new MantisConfigurationProperties();
    }

    @Bean
    public SpringBootMantisStarter springBootMantisStarter() {
        return new SpringBootMantisStarter();
    }

//    @Bean
//    public MonitorremoteService monitorremoteService(MantisBootStrap mantisBootStrap) {
//        return new MonitorremoteService(mantisBootStrap);
//    }

}
