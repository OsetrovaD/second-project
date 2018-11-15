package com.osetrova.project.connection;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class ApplicationSessionFactory {

    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
