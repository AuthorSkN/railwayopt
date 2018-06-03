package com.railwayopt.model.location;


import com.railwayopt.DB;
import com.railwayopt.entity.Infrastructable;
import com.railwayopt.entity.Infrastructure;

import java.util.*;

public class RegionManager {

    private static final String NONAME_REGION = "неизвестный регион";

    private List<String> regions = DB.getAllRegions();

    public RegionManager(){}


    public <T extends Infrastructable> Map<String, List<T>> groupingByRegion(List<T> infrastructures){
        Map<String, List<T>> groups = new HashMap<>();
        groups.put(NONAME_REGION, new ArrayList<>());
        for(T infrastructure: infrastructures){
            String infrastructureRegion = getCorrectRegionName(infrastructure.getRegion().getName());
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


    public String getCorrectRegionName(String name){
        if (name == null) return null;
        name = name.toLowerCase().replaceAll("\\s+", "");
        for(String region: regions){
            String universalRegion = region.toLowerCase().replaceAll("\\s+", "");
            if(universalRegion.equals(name)){
                return region;
            }
        }
        return null;
    }

}
