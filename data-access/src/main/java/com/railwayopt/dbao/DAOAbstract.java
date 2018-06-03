package com.railwayopt.dbao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class DAOAbstract<T> {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    protected Session getSession(){
        Session session = null;
        try{
            session = sessionFactory.getCurrentSession();
        }catch (Throwable exc){
            System.out.println("Initial session error!");
            exc.printStackTrace();
        }
        return session;
    }


}
