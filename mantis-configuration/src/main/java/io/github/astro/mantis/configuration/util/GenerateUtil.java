package io.github.astro.mantis.configuration.util;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.ConsumerCaller;
import io.github.astro.mantis.configuration.URL;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 生成工具类
 */
public interface GenerateUtil {

    static String generateKey(URL url) {
        return url.pathsToString() + "/" + url.getProtocol();
    }

    static String generateKey(CallData data) {
        ConsumerCaller caller = (ConsumerCaller) data.getCaller();
        return "/" + caller.getApplicationName() + "/" + caller.getRemoteServiceName() + "/" + caller.getCallName() + "/" + caller.getProtocol().name();
    }

    static String generateKey(Method method) {
        StringBuilder builder = new StringBuilder();
        String name = method.getName();
        builder.append(name);
        Type[] types = method.getGenericParameterTypes();
        if (types.length > 0) {
            builder.append("(");
            for (int i = 0; i < types.length; i++) {
                builder.append(types[i].getTypeName());
                if (i != types.length - 1) {
                    builder.append(",");
                }
            }
            builder.append(")");
        }

        return builder.toString();
    }

    static String generateMetaProtocolKey(String protocol) {
        return Key.PROTOCOL_PREFIX + protocol;
    }

}
