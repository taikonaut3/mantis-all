package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.configuration.InvokerProcessor;
import io.github.astro.mantis.configuration.MantisBootStrap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class InvokeProcessorPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof InvokerProcessor invokerProcessor) {
            MantisBootStrap mantisBootStrap = beanFactory.getBean(MantisBootStrap.class);
            mantisBootStrap.addProcessor(beanName, invokerProcessor);
        }
        return bean;
    }

}
