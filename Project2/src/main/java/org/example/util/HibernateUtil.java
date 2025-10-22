package org.example.util;

import org.example.exceptions.MyCustomException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sf=buildSessionFactory();

    private static SessionFactory buildSessionFactory(){

        try{
            return new Configuration().configure().buildSessionFactory();
        }
        catch(Throwable e){
            throw new MyCustomException("cannot build session factory");
        }
    }

    public static SessionFactory getSessionFactory(){
        return sf;
    }

    public static void closeSessionFactory(){
        getSessionFactory().close();
    }
}
