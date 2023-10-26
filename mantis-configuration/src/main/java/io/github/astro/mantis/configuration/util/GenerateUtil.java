package io.github.astro.mantis.configuration.util;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.ProtocolType;
import io.github.astro.mantis.configuration.ConsumerInvoker;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 生成工具类
 */
public interface GenerateUtil {

    static String generateKey(URL url) {
        return url.pathsToString() + "/" + url.getProtocol();
    }

    static String generateKey(Invocation invocation) {
        ConsumerInvoker invoker = (ConsumerInvoker) invocation.getInvoker();
        return "/" + invocation.getApplicationName() + "/" + invocation.getExportName() + "/" + invocation.getMethodKey() + "/" + invoker.getProtocol().getName();
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

    static String generateMetaProtocolKey(ProtocolType protocolType) {
        return Key.PROTOCOL_PREFIX + protocolType.getName();
    }
}
