package io.github.astro.mantis.spring.cloud;

import io.github.astro.mantis.common.constant.ServiceType;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.governance.directory.Directory;
import io.github.astro.mantis.spring.cloud.configuration.SpringDiscoveryDirectory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

public class SpringDirectoryFactoryBean implements FactoryBean<Directory> {

    private SpringDiscoveryDirectory directory;

    public SpringDirectoryFactoryBean(ApplicationContext context) {
        directory = (SpringDiscoveryDirectory) ServiceProviderLoader.loadService(Directory.class, ServiceType.SPRING);
        directory.setApplicationContext(context);
    }

    @Override
    public Directory getObject() throws Exception {
        return directory;
    }

    @Override
    public Class<?> getObjectType() {
        return Directory.class;
    }
}
