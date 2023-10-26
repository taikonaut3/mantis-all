package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.ProtocolConfig;
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
            MantisBootStrap mantisBootStrap = beanFactory.getBean(MantisBootStrap.class);
            config.setName(beanName);
            mantisBootStrap.addProtocolConfig(config);
        }
        return bean;
    }

}
