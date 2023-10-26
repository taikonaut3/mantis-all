package io.github.astro.mantis.common.util;

import java.util.HashMap;
import java.util.Map;

public interface MapUtils {

    static String mapToUrlParams(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (first) {
                first = false;
            } else {
                builder.append("&");
            }
            builder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return builder.toString();
    }

    static Map<String, String> paramsToMap(String params) {
        String[] strings = params.split("&");
        HashMap<String, String> map = new HashMap<>();
        for (String string : strings) {
            String[] split = string.split("=");
            String key = split[0];
            String value = split[1];
            map.put(key, value);
        }
        return map;
    }

    static void putIfExist(Map<String, String> map, String key, String value) {
        if (!StringUtils.isBlank(value)) {
            map.put(key, value);
        }
    }
}
