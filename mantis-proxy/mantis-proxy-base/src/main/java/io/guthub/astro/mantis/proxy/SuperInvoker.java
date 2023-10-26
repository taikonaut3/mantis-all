package io.guthub.astro.mantis.proxy;

/**
 * Invoke the original method
 */
@FunctionalInterface
public interface SuperInvoker<R> {

    R invoke() throws Throwable;
}
