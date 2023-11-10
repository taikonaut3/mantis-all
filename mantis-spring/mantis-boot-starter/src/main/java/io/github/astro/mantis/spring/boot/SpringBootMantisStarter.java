package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.configuration.MantisApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 启动 mantis
 */
public class SpringBootMantisStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MantisApplication mantisApplication = event.getApplicationContext().getBean(MantisApplication.class);
        mantisApplication.start();
    }

}
