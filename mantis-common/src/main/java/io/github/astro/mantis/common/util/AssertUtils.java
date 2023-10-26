package io.github.astro.mantis.common.util;

import java.util.Objects;

public interface AssertUtils {
    static void assertCondition(boolean condition) {
        assertCondition(condition, null);
    }

    static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void assertNotNull(Object object) {
        assertNotNull(object, null);
    }

    static void assertNotBlank(String str, String message) {
        assertCondition(!StringUtils.isBlank(str), message);
    }

    static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError(message);
        }
    }

    static void assertEquals(Object expected, Object actual) {
        assertEquals(expected, actual, null);
    }

    static void assertEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionError(message);
        }
    }
}
