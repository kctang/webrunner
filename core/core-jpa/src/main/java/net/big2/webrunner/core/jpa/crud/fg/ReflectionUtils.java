package net.big2.webrunner.core.jpa.crud.fg;

import net.big2.webrunner.core.jpa.crud.CrudEntity;

import java.lang.annotation.Annotation;

public class ReflectionUtils {
    public static boolean isFieldAnnotatedWith(
            String fieldName,
            Class<? extends CrudEntity> crudEntityClass,
            Class<? extends Annotation> aClass) {
        boolean retVal = false;

        try {
            java.lang.reflect.Field javaField = crudEntityClass.getDeclaredField(fieldName);
            if (javaField.isAnnotationPresent(aClass)) {
                retVal = true;
            }
        } catch (NoSuchFieldException e) {
            // safe to ignore, but let's log as trace
        }

        return retVal;
    }

    public static boolean isFieldType(String fieldName, Class<? extends CrudEntity> crudEntityClass, Class type) {
        boolean retVal = false;

        try {
            java.lang.reflect.Field javaField = crudEntityClass.getDeclaredField(fieldName);
            if (type.isAssignableFrom(javaField.getType())) {
                retVal = true;
            }
        } catch (NoSuchFieldException e) {
            // safe to ignore, but let's log as trace
        }

        return retVal;
    }
}
