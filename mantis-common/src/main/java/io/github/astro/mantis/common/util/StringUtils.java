package io.github.astro.mantis.common.util;

public interface StringUtils {

    static boolean isBlank(String str) {
        return (str == null || str.isEmpty() || isWhitespace(str));
    }

    static String isBlankOrDefault(String target, String defaultValue) {
        return isBlank(target) ? defaultValue : target;
    }

    private static boolean isWhitespace(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
