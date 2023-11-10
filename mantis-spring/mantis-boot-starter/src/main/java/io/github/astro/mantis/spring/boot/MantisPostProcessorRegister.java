package io.github.astro.mantis.spring.boot;

import io.github.astro.mantis.spring.boot.processor.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

public class MantisPostProcessorRegister implements ImportBeanDefinitionRegistrar {

    private final static List<Class<?>> SCAN_POSTPROCESSORS = List.of(
            RemoteServicePostProcessor.class,
            RemoteCallerPostProcessor.class
    );

    private final static List<Class<?>> GENERAL_POSTPROCESSORS = List.of(
            MantisApplicationPostProcessor.class,
            CallInterceptorPostProcessor.class,
            ProtocolConfigPostProcessor.class,
            RegistryConfigPostProcessor.class
    );

    /**
     * 注册后置处理器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MergedAnnotation<EnableMantis> annotation = importingClassMetadata.getAnnotations().get(EnableMantis.class);
        EnableMantis enableMantis = annotation.synthesize();
        registerPostProcessor(registry, enableMantis);

    }

    private void registerPostProcessor(BeanDefinitionRegistry registry, EnableMantis enableMantis) {
        for (Class<?> postprocessor : SCAN_POSTPROCESSORS) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(postprocessor);
            builder.addConstructorArgValue(enableMantis);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
        for (Class<?> postprocessor : GENERAL_POSTPROCESSORS) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(postprocessor);
            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
        }
    }

}