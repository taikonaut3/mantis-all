package io.github.astro.mantis.configuration;

import io.github.astro.mantis.configuration.invoke.Invocation;

public interface InvokerProcessor {

    default void processBeforeSelect(Invoker invoker, Invocation invocation) {
    }

    default void processAfterSelect(Invoker invoker, Invocation invocation, URL url) {
    }

    default void processBeforeInvoke(Invoker invoker, Invocation invocation, URL url) {
    }

    default void processAfterInvoke(Invoker invoker, Invocation invocation, URL url) {
    }
}
