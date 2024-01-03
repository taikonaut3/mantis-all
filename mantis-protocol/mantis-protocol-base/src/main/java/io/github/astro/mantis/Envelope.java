package io.github.astro.mantis;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;

import java.io.Serializable;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/3 15:34
 */
public interface Envelope extends Serializable {

    long serialVersionUID = 1L;

    URL getUrl();

    default Long getId() {
        return getUrl().getLongParameter(Key.UNIQUE_ID);
    }

}
