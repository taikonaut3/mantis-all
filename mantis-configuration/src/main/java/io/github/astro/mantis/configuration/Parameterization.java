package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.util.ReflectUtils;
import io.github.astro.mantis.configuration.annotation.Parameter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Parameterization {

    default Map<String, String> parameterization() {
        HashMap<String, String> map = new HashMap<>();
        List<Field> fields = ReflectUtils.getAllFields(this.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(Parameter.class)) {
                String key = field.getAnnotation(Parameter.class).value();
                String value;
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(this);
                    if (fieldValue != null) {
                        value = String.valueOf(fieldValue);
                        map.put(key, value);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return map;
    }

}
