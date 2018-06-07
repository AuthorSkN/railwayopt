package com.railwayopt.dbao;

import com.railwayopt.entity.*;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.Collection;
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

    public void deleteAllFactory(){
        Session session = getSession();
        session.beginTransaction();
        session.createQuery("Delete Factory").executeUpdate();
        session.createQuery("Delete Infrastructure Where is_station=:factory").setParameter("factory", false).executeUpdate();
        session.getTransaction().commit();
    }

    public List<Station> getAllStations(){
        Session session = getSession();
        session.beginTransaction();
        List<Station> stations = session.createQuery("From Station").list();
        session.getTransaction().commit();
        return stations;
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

    public void addAllInfrastructure(List<? extends Infrastructable> objects){
        Session session = getSession();
        session.beginTransaction();
        for(Infrastructable object: objects) {
            List<BigInteger> id = session.createSQLQuery("Select nextval('geo_object_sequence')").list();
            object.setId(id.get(0).intValue());
            Infrastructure infrastructure = object.getAndDeleteInfrastructure();
            session.save(infrastructure);
            session.save(object);
        }
        session.getTransaction().commit();
    }

    public List<Region> getAllRegions(){
        Session session = getSession();
        session.beginTransaction();
        List<Region> regions = session.createQuery("From Region").list();
        session.getTransaction().commit();
        return regions;
    }

    public Region getRegionByName(String regionName){
        Region region = null;
        Session session = getSession();
        session.beginTransaction();
        List<Region> regions = session.createQuery("From Region Where lower(name) Like :regionName")
                .setParameter("regionName", regionName).list();
        session.getTransaction().commit();
        if (regions != null)
            region = regions.get(0);
        return region;
    }



}
