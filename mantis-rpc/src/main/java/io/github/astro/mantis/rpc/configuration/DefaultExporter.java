package io.github.astro.mantis.rpc.configuration;

import io.github.astro.mantis.common.util.AssertUtils;
import io.github.astro.mantis.common.util.ReflectUtils;
import io.github.astro.mantis.configuration.Exporter;
import io.github.astro.mantis.configuration.Invoker;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.ProviderInvoker;
import io.github.astro.mantis.configuration.annotation.Export;
import io.github.astro.mantis.configuration.annotation.Procedure;
import io.github.astro.mantis.configuration.extension.MantisContext;
import io.github.astro.mantis.configuration.invoke.Invocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultExporter<T> implements Exporter<T> {

    private static final Class<Export> EXPORT_CLASS = Export.class;

    private volatile boolean exported = false;
    private MantisBootStrap mantisBootStrap;
    private Map<String, Invoker> procedures;
    private Class<T> exporterClass;
    private T target;
    private String exportName;

    public DefaultExporter(MantisBootStrap mantisBootStrap, T target) {
        this.mantisBootStrap = mantisBootStrap;
        this.setExporter(target);
        mantisBootStrap.addExporter(this);
    }

    public static boolean checkExport(Class<?> exporterClass) {
        return exporterClass.isAnnotationPresent(EXPORT_CLASS);
    }

    @SuppressWarnings("unchecked")
    public void setExporter(T target) {
        AssertUtils.assertCondition(checkExport(target.getClass()), "Exporter this Method Only support @Export modifier's Object");
        this.target = target;
        this.exporterClass = (Class<T>) target.getClass();
        // parse @Export
        parseExporter();
        // parse @procedure
        parseProcedures();
    }

    @Override
    public String getApplicationName() {
        return mantisBootStrap.getApplicationName();
    }

    @Override
    public String getExportName() {
        return exportName;
    }

    @Override
    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    @Override
    public void export() {
        if (exported) {
            return;
        }
        synchronized (this) {
            if (exported) {
                return;
            }
            AssertUtils.assertNotBlank(getApplicationName(), "UNKONWN the applicationName");
            MantisContext.setCurrentBootStrap(mantisBootStrap);
            doExport();
            this.exported = true;
        }
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public void setTarget(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Invocation invocation) {
        return getInvoker(invocation.getMethodKey()).invoke(invocation);
    }

    protected void doExport() {
        for (Invoker invoker : getInvokers()) {
            ((ProviderInvoker) invoker).export();
        }
    }

    private void parseExporter() {
        Export export = exporterClass.getAnnotation(EXPORT_CLASS);
        this.setApplicationName(export.applicationName());
        this.setExportName(export.value());
        if (exporterClass.getDeclaredMethods().length > 0) {
            this.procedures = new HashMap<>();
        }
    }

    private void parseProcedures() {
        if (procedures == null) {
            procedures = new HashMap<>();
        }
        for (Method method : exporterClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Procedure.class)) {
                Invoker invoker = createInvoker(method);
                addInvoker(invoker);
            }
        }
    }

    @Override
    public Method getMethod(String methodKey) {
        return procedures.get(methodKey).getMethod();
    }

    @Override
    public void addInvoker(Invoker... invokers) {
        for (Invoker invoker : invokers) {
            procedures.put(invoker.getMethodKey(), invoker);
        }
    }

    @Override
    public Invoker[] getInvokers() {
        return procedures.values().toArray(new Invoker[0]);
    }

    @Override
    public Invoker getInvoker(Method method) {
        for (Invoker invoker : procedures.values()) {
            if (ReflectUtils.isSameMethod(method, invoker.getMethod())) {
                return invoker;
            }
        }
        return null;
    }

    @Override
    public Invoker getInvoker(String methodKey) {
        return procedures.get(methodKey);
    }

    @Override
    public MantisBootStrap getMantisBootStrap() {
        return mantisBootStrap;
    }

    @Override
    public Invoker createInvoker(Method method) {
        return new DefaultProviderInvoker(method, this);
    }

    @Override
    public String toString() {
        return getApplicationName() + "/" + getExportName() + ":" + target.getClass().getName();
    }
}
