package com.railwayopt.dbao;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Infrastructable;
import com.railwayopt.entity.Infrastructure;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

public class DAORailwayImpl extends DAOAbstract{

    public List<Factory> getAllFactory(){
        Session session = getSession();
        session.beginTransaction();
        List<Factory> factories = session.createQuery("From Factory").list();
        session.getTransaction().commit();
        return factories;
    }

    public void addFactory(Factory factory){
        Session session = getSession();
        session.beginTransaction();
        List<BigInteger> id = session.createSQLQuery("Select nextval('geo_object_sequence')").list();
        factory.setId(id.get(0).intValue());
        Infrastructure infrastructure = factory.getAndDeleteInfrastructure();
        session.save(infrastructure);
        session.save(factory);
        session.getTransaction().commit();
    }

    public void deleteFactory(Factory factory){
        Session session = getSession();
        session.beginTransaction();
        session.createQuery("Delete Factory Where id=:id").setParameter("id", factory.getId()).executeUpdate();
        session.createQuery("Delete Infrastructure Where id=:id").setParameter("id", factory.getId()).executeUpdate();
        session.getTransaction().commit();
    }



}
