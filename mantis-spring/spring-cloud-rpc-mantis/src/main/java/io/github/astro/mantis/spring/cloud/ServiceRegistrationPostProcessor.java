package io.github.astro.mantis.spring.cloud;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cloud.client.serviceregistry.AbstractAutoServiceRegistration;

public class ServiceRegistrationPostProcessor implements BeanPostProcessor, BeanFactoryPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractAutoServiceRegistration<?> autoServiceRegistration) {
            autoServiceRegistration.addRegistrationLifecycle(new MantisRegistrationLifecycle<>(beanFactory));
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
