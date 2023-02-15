package messenger.menegment;

import messenger.annotation.Autowired;
import messenger.annotation.Singleton;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InstanceFactory {

    private static final Map<Class<?>, Object> objectsContainer = new HashMap<>();

    public static <T> T getInstance(Class<T> instanceType) {
        if (objectsContainer.containsKey(instanceType)) {
            return (T) objectsContainer.get(instanceType);
        } else {
            if (instanceType.isAnnotationPresent(Singleton.class)) {
                Constructor<T> constructor = null;
                try {
                    constructor = instanceType.getDeclaredConstructor();
                    constructor.setAccessible(true);

                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                T newInstance = null;
                try {
                    newInstance = constructor.newInstance();
                    System.out.println("Created instance " + newInstance.getClass().getSimpleName() + " wish hashcode " + newInstance.hashCode());
                    Field[] declaredFields = instanceType.getDeclaredFields();

                    for (Field declaredField : declaredFields) {
                        if (declaredField.isAnnotationPresent(Autowired.class)) {
                            declaredField.setAccessible(true);
                            Object dependencyInstance = getInstance(declaredField.getType());
                            System.out.println("HashCode created dependency instance  " + declaredField.getType().getSimpleName()
                                    + " " + dependencyInstance.hashCode());

                            declaredField.set(newInstance, dependencyInstance);

                        }
                    }

                    objectsContainer.put(instanceType, newInstance);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                return newInstance;
            }
        }
        throw new RuntimeException("Can't create singleton");
    }

    public static void registerAllInstances() {
        Class<?>[] classes;
        try {
            classes = ReflectionUtil.getClasses("messenger");
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        for (Class<?> aClass : classes) {
            if (aClass.isAnnotationPresent(Singleton.class)) {
                if (!aClass.getAnnotation(Singleton.class).lazy()) {
                    InstanceFactory.getInstance(aClass);
                }
            }
        }
    }
}
