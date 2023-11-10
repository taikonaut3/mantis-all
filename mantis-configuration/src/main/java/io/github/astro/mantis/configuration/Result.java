package io.github.astro.mantis.configuration;

public interface Result {

    Object getValue();

    boolean hasResult();

    boolean hasException();

    Exception getException();

}
