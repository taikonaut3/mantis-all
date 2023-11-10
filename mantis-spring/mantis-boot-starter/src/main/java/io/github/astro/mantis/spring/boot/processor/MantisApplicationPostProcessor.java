package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.spring.boot.EnvironmentKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

public class MantisApplicationPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MantisApplication mantisApplication) {
            mantisApplication.init();
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MantisApplication mantisApplication) {
            Environment environment = beanFactory.getBean(Environment.class);
            String app_name = environment.getProperty(EnvironmentKey.APPLICATION_NAME);
            if (!StringUtils.hasText(app_name)) {
                app_name = environment.getProperty(EnvironmentKey.SPRING_APPLICATION_NAME);
                if (!StringUtils.hasText(app_name)) {
                    throw new SourceException("缺少application-name");
                }
            }
            mantisApplication.setApplicationName(app_name);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
