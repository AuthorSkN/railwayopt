package com.railwayopt.gui.custom.shareddata;

import com.railwayopt.entity.Factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SharedFactory extends Factory implements Numberable{

    private int number;

    public SharedFactory(Factory factory){
        super(factory.getId(), factory.getName(), factory.getLatitude(), factory.getLongitude(),
                                factory.getX(),factory.getY(),  factory.getWeight());
        this.setRegion(factory.getRegion());
        this.setCargoes(factory.getCargoes());
        this.setFullWeight(factory.getFullWeight());
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

