package com.osetrova.project.connection;

import com.osetrova.project.serviceconfig.ServiceConfiguration;
import lombok.experimental.UtilityClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@UtilityClass
public class ContextUtil {

    private static final AnnotationConfigApplicationContext CONTEXT = new AnnotationConfigApplicationContext(ServiceConfiguration.class);

    public static AnnotationConfigApplicationContext getContext() {
        return CONTEXT;
    }

    public static <T> T getBean(String beanId, Class<T> beanClass) {
        return CONTEXT.getBean(beanId, beanClass);
    }
}
