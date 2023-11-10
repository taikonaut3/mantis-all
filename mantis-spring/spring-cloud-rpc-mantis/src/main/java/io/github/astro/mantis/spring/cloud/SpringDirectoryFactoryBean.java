package io.github.astro.mantis.spring.cloud;

import io.github.astro.mantis.common.constant.KeyValues;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.governance.directory.Directory;
import io.github.astro.mantis.spring.cloud.configuration.SpringDiscoveryDirectory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;

public class SpringDirectoryFactoryBean implements FactoryBean<Directory> {

    private SpringDiscoveryDirectory directory;

    public SpringDirectoryFactoryBean(ApplicationContext context) {
        directory = (SpringDiscoveryDirectory) ExtensionLoader.loadService(Directory.class, KeyValues.SPRING);
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
