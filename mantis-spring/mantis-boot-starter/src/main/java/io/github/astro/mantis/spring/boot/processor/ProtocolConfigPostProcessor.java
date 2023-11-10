package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ProtocolConfigPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ProtocolConfig config) {
            MantisApplication mantisApplication = beanFactory.getBean(MantisApplication.class);
            config.setName(beanName);
            mantisApplication.addProtocolConfig(config);
        }
        return bean;
    }

}
