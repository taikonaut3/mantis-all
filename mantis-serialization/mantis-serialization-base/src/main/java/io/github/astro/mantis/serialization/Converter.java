package io.github.astro.mantis.serialization;

import io.github.astro.mantis.common.exception.ConversionException;

import java.lang.reflect.Type;

/**
 * For cross-system transmission, often used for common serialization to local Type
 */
public interface Converter {

    default Object[] convert(Object[] args, Type[] type) throws ConversionException {
        return args;
    }

    default Object convert(Object arg, Type type) throws ConversionException {
        return arg;
    }

}

