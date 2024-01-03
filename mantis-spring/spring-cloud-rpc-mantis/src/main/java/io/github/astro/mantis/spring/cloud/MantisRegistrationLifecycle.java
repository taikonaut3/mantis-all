package io.github.astro.mantis.spring.cloud;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.config.ProtocolConfig;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.RegistrationLifecycle;

import java.util.List;

public class MantisRegistrationLifecycle<R extends Registration> implements RegistrationLifecycle<R> {

    private final BeanFactory beanFactory;

    public MantisRegistrationLifecycle(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void postProcessBeforeStartRegister(R registration) {
        MantisApplication mantisApplication = beanFactory.getBean(MantisApplication.class);
        List<ProtocolConfig> protocolConfigs = mantisApplication.getConfigurationManager().getProtocolManager().getApplicationScopeConfigs();
        for (ProtocolConfig protocolConfig : protocolConfigs) {
            String key = GenerateUtil.generateMetaProtocolKey(protocolConfig.getType());
            registration.getMetadata().put(key, protocolConfig.toUrl().toString());
        }
        registration.getMetadata().put(Key.REGISTRY_META_WEIGHT, String.valueOf(mantisApplication.getAppConfiguration().getWeight()));
    }

    @Override
    public void postProcessAfterStartRegister(R registration) {

    }

    @Override
    public void postProcessBeforeStopRegister(R registration) {

    }

    @Override
    public void postProcessAfterStopRegister(R registration) {

    }

}
