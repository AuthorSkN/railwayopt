package com.railwayopt;


import com.railwayopt.DB;
import com.railwayopt.dbao.DAORailwayImpl;
import com.railwayopt.entity.Infrastructable;
import com.railwayopt.entity.Infrastructure;
import com.railwayopt.entity.Region;

import java.util.*;

public class RegionManager {

    private static final String NONAME_REGION = "неизвестный регион";

    private List<Region> regions = new DAORailwayImpl().getAllRegions();

    public RegionManager(){}


    public <T extends Infrastructable> Map<String, List<T>> groupingByRegion(List<T> infrastructures){
        Map<String, List<T>> groups = new HashMap<>();
        groups.put(NONAME_REGION, new ArrayList<>());
        for(T infrastructure: infrastructures){
            String infrastructureRegion = getCorrectRegionName(infrastructure.getRegion());
            if(infrastructureRegion == null){
                groups.get(NONAME_REGION).add(infrastructure);
            }else if(groups.containsKey(infrastructureRegion)){
                groups.get(infrastructureRegion).add(infrastructure);
            }else{
                groups.put(infrastructureRegion, new ArrayList<>());
                groups.get(infrastructureRegion).add(infrastructure);
            }
        }
        return groups;
    }


    public String getCorrectRegionName(Region regionName){
        if (regionName == null) return null;
        String name = regionName.getName();
        name = name.toLowerCase().replaceAll("\\s+", "");
        for(Region region: regions){
            String universalRegion = region.getName().toLowerCase().replaceAll("\\s+", "");
            if(universalRegion.equals(name)){
                return region.getName();
            }
        }
        return null;
    }

    public Region getCorrectRegion(String name){
        name = name.toLowerCase().replaceAll("\\s+", "");
        for(Region region: regions){
            String universalRegion = region.getName().toLowerCase().replaceAll("\\s+", "");
            if(universalRegion.equals(name)){
                return region;
            }
        }
        return null;
    }

}
