package com.railwayopt;

import com.railwayopt.entity.*;
import com.sun.prism.impl.FactoryResetException;

import java.util.*;

public class DB {

    private static Map<Integer, Project> projects = new HashMap<>();
    private static Map<Integer, Factory> factories = new HashMap<>();
    private static Map<Integer, Station> stations = new HashMap<>();
    private static List<String> regions = new ArrayList<>();

    public static void initializer(){
        //проекты
        for(int i = 0; i < 10; i++){
            Project project = new Project(i, "Новое имя "+i, "Некоторый проект "+i+", который содержит станции и производства.");
            project.setDate(i+"-"+(10+i)+"-"+"2018");
            project.setAuthor("Складнев");
            projects.put(i, project);
        }
        //производства
        double longitude = 60;
        double latitude = 38;
        for(int i = 0; i < 20; i++){
            longitude += i;
            latitude -= i;
            Factory factory = new Factory(i, "Производство из БД "+i, latitude, longitude, 1.0/(i+1));
            if(i % 2 == 0){
                factory.setRegion(new Region(0, "самарская область"));
            }else{
                factory.setRegion(new Region(0,"крым"));
            }
            factory.setFullWeight(12234.124);
            factories.put(factory.getId(), factory);
        }
        //Регионы
        regions = Arrays.asList("Самарская область", "Ульяновская область", "Кировская область");
        //Станции
        //производства
        longitude = 60;
        latitude = 20;
        for(int i = 0; i < 10; i++){
            longitude += i;
            latitude -= i;
            Station station = new Station(i, "Станция из БД "+i, latitude, longitude);
            if(i % 2 == 0){
                station.setRegion(new Region(0, "самарская область"));
            }else{
                station.setRegion(new Region(0,"крым"));
            }
            station.setExistLogisticCentre(false);
            stations.put(station.getId(), station);
        }
    }

    public static void addProject(Project project){
        projects.put(project.getId(), project);
    }

    public static List<Project> getAllProjects(){
        return new ArrayList<>(projects.values());
    }

    public static List<Factory> getAllFactories(){
        return new ArrayList<>(factories.values());
    }

    public static List<String> getAllRegions(){
        return regions;
    }

    public static List<Station> getAllStations(){ return new ArrayList<>(stations.values()); }
}
