package io.github.astro.mantis.configuration.manager;

import io.github.astro.mantis.common.constant.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class ApplicationConfiguration {

    private String applicationName;

    private int weight = Constant.DEFAULT_WEIGHT;

    public ApplicationConfiguration() {

    }

    public ApplicationConfiguration(String applicationName) {
        this.applicationName = applicationName;
    }

}
