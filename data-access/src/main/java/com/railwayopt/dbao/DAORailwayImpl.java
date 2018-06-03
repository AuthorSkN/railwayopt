package com.railwayopt.dbao;

import com.railwayopt.entity.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

public class DAORailwayImpl extends DAOAbstract{

    public List<Factory> getAllFactories(){
        Session session = getSession();
        session.beginTransaction();
        List<Factory> factories = session.createQuery("From Factory").list();
        session.getTransaction().commit();
        return factories;
    }


    public void deleteFactory(Factory factory){
        Session session = getSession();
        session.beginTransaction();
        session.createQuery("Delete Factory Where id=:id").setParameter("id", factory.getId()).executeUpdate();
        session.createQuery("Delete Infrastructure Where id=:id").setParameter("id", factory.getId()).executeUpdate();
        session.getTransaction().commit();
    }

    public List<RailwayPart> getAllRailways(){
        Session session = getSession();
        session.beginTransaction();
        List<RailwayPart> railways = session.createQuery("From RailwayPart").list();
        session.getTransaction().commit();
        return railways;
    }

    public List<RailwayBranch> getAllBranches(){
        Session session = getSession();
        session.beginTransaction();
        List<RailwayBranch> branches = session.createQuery("From RailwayBranch").list();
        session.getTransaction().commit();
        return branches;
    }

    public void addInfrastructure(Infrastructable object) {
        Session session = getSession();
        session.beginTransaction();
        List<BigInteger> id = session.createSQLQuery("Select nextval('geo_object_sequence')").list();
        object.setId(id.get(0).intValue());
        Infrastructure infrastructure = object.getAndDeleteInfrastructure();
        session.save(infrastructure);
        session.save(object);
        session.getTransaction().commit();
    }



}
