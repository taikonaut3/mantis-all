package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.configuration.CallInterceptor;
import io.github.astro.mantis.configuration.MantisApplication;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class CallInterceptorPostProcessor implements BeanFactoryPostProcessor, BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CallInterceptor interceptor) {
            MantisApplication mantisApplication = beanFactory.getBean(MantisApplication.class);
            mantisApplication.addProcessor(beanName, interceptor);
        }
        return bean;
    }

}
