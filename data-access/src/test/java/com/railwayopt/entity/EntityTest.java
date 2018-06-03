package com.railwayopt.entity;

import com.railwayopt.dbao.DAORailwayImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


import java.util.List;

public class EntityTest {

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


    @Test
    public void getEntityTest(){
        Factory factory = new Factory(12, "Новое производство", 45.2, 56.9, 1055.6);
        new DAORailwayImpl().deleteFactory(factory);
    }
}
