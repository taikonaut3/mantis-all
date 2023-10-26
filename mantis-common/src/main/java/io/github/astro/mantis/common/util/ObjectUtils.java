package io.github.astro.mantis.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author WenBo Zhou
 * @Date 2023/6/19 19:28
 */
public interface ObjectUtils {

    static Object isNullOrDefault(Object target, Object defaultObj) {
        return target == null ? defaultObj : target;
    }

    /**
     * 将source对象中的相同字段值拷贝到target对象中
     *
     * @param source 源对象
     * @param target 目标对象
     */
    static void copy(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        Field[] sourceFields = source.getClass().getFields();
        Field[] targetFields = target.getClass().getFields();
        doCopyFiled(source, target, sourceFields, targetFields, true);
    }

    static void copyNotBlank(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        Field[] sourceFields = source.getClass().getFields();
        Field[] targetFields = target.getClass().getFields();
        doCopyFiled(source, target, sourceFields, targetFields, false);
    }

    static void declaredCopy(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        doCopyFiled(source, target, sourceFields, targetFields, true);
    }

    static void annotationCopyToObject(Annotation annotation, Object target) {
        if (annotation == null || target == null) {
            return;
        }
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Class<?> targetClass = target.getClass();
        Method[] methods = annotationType.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            Field[] fields = targetClass.getFields();
            for (Field field : fields) {
                if (method.getName().equals(field.getName()) && method.getReturnType() == field.getType()) {
                }
            }
        }
    }

    private static void doCopyFiled(Object source, Object target, Field[] sourceFields, Field[] targetFields, boolean isAllCopy) {
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            try {
                Object sourceValue = sourceField.get(source);
                for (Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if (targetField.getName().equals(sourceField.getName())) {
                        if (isAllCopy) {
                            targetField.set(target, sourceValue);
                        } else {
                            Object value = targetField.get(target);
                            if (value == null) {
                                targetField.set(target, sourceValue);
                            }
                        }
                        break;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
