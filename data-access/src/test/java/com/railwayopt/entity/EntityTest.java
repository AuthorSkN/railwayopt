package com.railwayopt.entity;

import com.railwayopt.dbao.DAORailwayImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;


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
        Station station = new Station(0, "новая станция", 34.5, 12.7, 4345.12, 5643.32, false);
        station.setRegion(new Region(2));
        new DAORailwayImpl().addInfrastructure(station);
    }
}
