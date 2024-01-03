package io.github.astro.mantis.configuration.config;

import io.github.astro.mantis.common.constant.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class ApplicationConfig {

    private String applicationName;

    private int weight = Constant.DEFAULT_WEIGHT;

    private String eventDispatcher = Constant.DEFAULT_EVENT_DISPATCHER;

    public ApplicationConfig() {

    }

    public ApplicationConfig(String applicationName) {
        this.applicationName = applicationName;
    }



}
