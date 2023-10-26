package io.github.astro.mantis.configuration.invoke;

public interface Result {

    Object getValue();

    boolean hasResult();

    boolean hasException();

    Exception getException();

}
