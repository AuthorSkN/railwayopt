package com.railwayopt.gui.custom;

import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Station;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SharedFactory extends Factory {

    private int number;

    public SharedFactory(Factory factory){
        super(factory.getId(), factory.getName(), factory.getLatitude(), factory.getLongitude(), factory.getWeight());
    }

    public SharedFactory(int number, Factory factory){
        this(factory);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static List<SharedFactory> convertToShared(Collection<Factory> factories){
        List<SharedFactory> sharedFactories = new ArrayList<>();
        int number = 1;
        for(Factory factory:factories){
            sharedFactories.add(new SharedFactory(number++, factory));
        }
        return sharedFactories;
    }
}

