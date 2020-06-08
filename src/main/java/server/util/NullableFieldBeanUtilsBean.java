package server.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullableFieldBeanUtilsBean extends BeanUtilsBean {
    private static NullableFieldBeanUtilsBean instance;

    public static NullableFieldBeanUtilsBean getInstance() {
        if (instance == null) {
            instance = new NullableFieldBeanUtilsBean();
        }
        return instance;
    }

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        super.copyProperty(dest, name, value);
    }

    @Override
    public void copyProperties(Object dest, Object orig) {
        try {
            super.copyProperties(dest, orig);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new NullPointerException();
        }
    }

}
