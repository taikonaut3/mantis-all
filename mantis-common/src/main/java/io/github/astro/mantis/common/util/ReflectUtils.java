package io.github.astro.mantis.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
public interface ReflectUtils {

    static List<Class<?>> getClasses(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replace(".", "/");
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File subfile : Objects.requireNonNull(file.listFiles())) {
            if (subfile.isDirectory()) {
                classes.addAll(getClasses(basePackage + "." + subfile.getName()));
            } else if (subfile.getName().endsWith(".class")) {
                String className = basePackage + "." + subfile.getName().substring(0, subfile.getName().length() - 6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return classes;
    }

    static Class<?>[] getParameterTypes(Object... args) {
        Class<?>[] classes;
        if (args == null) {
            classes = new Class[]{};
        } else {
            classes = new Class[args.length];
        }
        int index = 0;
        for (Object arg : args) {
            classes[index++] = arg.getClass();
        }
        return classes;
    }

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

    static byte[] toBytes(Object obj) {
        if (obj instanceof byte[] bytes) {
            return bytes;
        } else if (obj instanceof String str) {
            return str.getBytes();
        } else {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeObject(obj);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static boolean isSameMethod(Method method1, Method method2) {
        if (method1 == method2) {
            // 如果引用相等，返回true
            return true;
        }
        if (!method1.getName().equals(method2.getName())) {
            // 如果方法名不同，返回false
            return false;
        }
//        if (!method1.getReturnType().equals(method2.getReturnType())) {
//            // 如果返回类型不同，返回false
//            return false;
//        }
        if (method1.getParameterCount() != method2.getParameterCount()) {
            // 如果参数数量不同，返回false
            return false;
        }
        // 判断每个参数类型是否都相同
        Class<?>[] params1 = method1.getParameterTypes();
        Class<?>[] params2 = method2.getParameterTypes();
        for (int i = 0; i < params1.length; i++) {
            if (!params1[i].equals(params2[i])) {
                return false;
            }
        }
        return true;
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

    static Field getField(Class<?> clazz, String name) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(name)) {
                return declaredField;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            getField(superClass, name);
        }
        return null;
    }

    static String getSetMethodName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    static Field[] getDeclaredFields(Object obj) {
        return obj.getClass().getDeclaredFields();
    }

    static Field[] getFields(Object obj) {
        return obj.getClass().getFields();
    }

    static Class<?> getGenericType(Class<?> clazz, int index) {
        return getGenericType((Type) clazz, index);
    }

    static Class<?> getGenericType(Class<?> clazz) {
        return getGenericType((Type) clazz, 0);
    }

    static Class<?> getSubGenericType(Class<?> clazz, int index) {
        // 获取泛型的参数类型
        Type type = clazz.getGenericSuperclass();
        return getGenericType(type, index);
    }

    static Class<?> getSubGenericType(Class<?> clazz) {
        return getSubGenericType(clazz, 0);
    }

    private static Class<?> getGenericType(Type type, int index) {
        if (type instanceof ParameterizedType paramType) {
            Type[] typeArray = paramType.getActualTypeArguments();
            if (typeArray.length > index) {
                Type genericType = typeArray[index];
                if (genericType instanceof Class) {
                    return (Class<?>) genericType;
                }
            }
        }
        return null;
    }

    static Class<?> getInterfaceGenericType(Class<?> clazz, Class<?> interfaceClazz, int index) {
        Type[] types = clazz.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType paramType) {
                if (paramType.getRawType() == interfaceClazz) {
                    Type[] typeArray = paramType.getActualTypeArguments();
                    if (typeArray.length > index) {
                        Type genericType = typeArray[index];
                        if (genericType instanceof Class) {
                            return (Class<?>) genericType;
                        }
                    }
                }
            }
        }
        return null;
    }

    static Class<?> getInterfaceGenericType(Class<?> clazz, Class<?> interfaceClazz) {
        return getInterfaceGenericType(clazz, interfaceClazz, 0);
    }
}
