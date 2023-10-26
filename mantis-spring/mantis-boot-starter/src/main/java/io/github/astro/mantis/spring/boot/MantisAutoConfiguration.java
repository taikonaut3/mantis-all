package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.configuration.MantisBootStrap;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class MantisAutoConfiguration {

    @Bean
    public MantisBootStrap mantisBootStrap() {
        return new MantisBootStrap();
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
//    public MonitorExporter monitorExporter(MantisBootStrap mantisBootStrap) {
//        return new MonitorExporter(mantisBootStrap);
//    }

}
