package io.github.astro.mantis.spring.boot.processor;

import io.github.astro.mantis.configuration.annotation.RemoteService;
import io.github.astro.mantis.spring.boot.EnableMantis;
import io.github.astro.mantis.spring.boot.MantisConfigurationProperties;
import io.github.astro.mantis.spring.rpc.SpringRemoteService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RemoteServicePostProcessor implements BeanDefinitionRegistryPostProcessor, BeanPostProcessor {

    private final static List<Class<? extends Annotation>> EXPORT_ANNOTATION_TYPES = List.of(
            RemoteService.class
    );

    private final String[] exportPackages;

    private BeanFactory beanFactory;

    public RemoteServicePostProcessor(EnableMantis enableMantis) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, enableMantis.scanBasePackages());
        Collections.addAll(list, enableMantis.exportsScan());
        this.exportPackages = list.toArray(new String[0]);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        for (Class<? extends Annotation> annotationType : EXPORT_ANNOTATION_TYPES) {
            scanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
        }
        for (String exportPackage : exportPackages) {
            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(exportPackage);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                String className = beanDefinition.getBeanClassName();
                assert className != null;
                Class<?> type = ClassUtils.resolveClassName(className, null);
                if (type.isAnnotationPresent(RemoteService.class)) {
                    RemoteService export = type.getAnnotation(RemoteService.class);
                    registry.registerBeanDefinition(export.value(), beanDefinition);
                }
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(RemoteService.class)) {
            MantisConfigurationProperties properties = beanFactory.getBean(MantisConfigurationProperties.class);
            new SpringRemoteService<>(properties.getMantisBootStrap(), bean);
        }
        return bean;
    }

}
