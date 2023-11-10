package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.util.GenerateUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Type;

@Setter
@Getter
public class DefaultCallData implements CallData, Serializable {

    private Object[] args;

    private String ApplicationName;

    private String remoteServiceName;

    private String callName;

    private transient Type returnType;

    private transient Type[] parameterTypes;

    private transient Caller caller;

    public DefaultCallData() {

    }

    public DefaultCallData(Caller caller, Object[] args) {
        this(caller.getCallName(), args, caller.getApplicationName(), caller.getRemoteServiceName());
        this.caller = caller;
        this.returnType = caller.getReturnType();
        this.parameterTypes = caller.getMethod().getGenericParameterTypes();

    }

    public DefaultCallData(String callName, Object[] args, String ApplicationName, String remoteServiceName) {
        this.args = args;
        this.callName = callName;
        this.ApplicationName = ApplicationName;
        this.remoteServiceName = remoteServiceName;
    }

    @Override
    public String toString() {
        return GenerateUtil.generateKey(this);
    }

}
