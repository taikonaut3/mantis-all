package io.github.astro.mantis.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public interface ReflectUtils {

    static <T> T createInstance(Class<T> type, Object... args) {
        Class<?>[] parameterTypes;
        try {
            parameterTypes = findMatchingConstructor(type, args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            return parameterTypes == null ? type.getConstructor(new Class[]{}).newInstance() :
                    type.getConstructor(parameterTypes).newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static Class<?>[] findMatchingConstructor(Class<?> clazz, Object... args) throws NoSuchMethodException {
        if (args == null || args.length == 0) {
            return null;
        }
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == args.length) {
                int index = 0;
                while (index < args.length) {
                    Class<?> parameterType = parameterTypes[index];
                    Object obj = args[index];
                    if (parameterType.isAssignableFrom(obj.getClass())) {
                        index++;
                    } else {
                        break;
                    }
                }
                if (index == args.length) {
                    return constructor.getParameterTypes();
                }
            }
        }
        throw new NoSuchMethodException("Can't find the Constructor(" + Arrays.toString(args) + ")");
    }

    static List<Field> getAllFields(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<>(Arrays.asList(declaredFields));
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fields.addAll(getAllFields(superClass));
        }
        return fields;
    }

}
