package io.github.astro.mantis.configuration.invoke;

import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.util.GenerateUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Type;

@Getter
@Setter
public class DefaultInvocation implements Invocation, Serializable {

    private Object[] args;
    private String methodKey;
    private String applicationName;
    private String exportName;
    private transient Type returnType;
    private transient Type[] parameterTypes;
    private transient Invoker invoker;

    public DefaultInvocation() {

    }

    public DefaultInvocation(Invoker invoker, Object[] args) {
        this(invoker.getMethodKey(), args, invoker.getApplicationName(), invoker.getExportName());
        this.invoker = invoker;
    }

    public DefaultInvocation(String methodKey, Object[] args, String applicationName, String exportName) {
        this.args = args;
        this.methodKey = methodKey;
        this.applicationName = applicationName;
        this.exportName = exportName;
    }

    @Override
    public String toString() {
        return GenerateUtil.generateKey(this);
    }
}
