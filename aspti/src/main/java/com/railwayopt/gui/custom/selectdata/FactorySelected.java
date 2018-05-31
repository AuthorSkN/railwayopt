package com.railwayopt.gui.custom.selectdata;

import com.railwayopt.entity.Factory;

import java.util.ArrayList;
import java.util.List;

public class FactorySelected extends Factory implements Selectable{

    private boolean selected = false;

    public FactorySelected(){}

    public FactorySelected(Factory factory){
        setId(factory.getId());
        setName(factory.getName());
        setLatitude(factory.getLatitude());
        setLongitude(factory.getLongitude());
        setWeight(factory.getWeight());
        setRegion(factory.getRegion());
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static List<FactorySelected> convertToSelected(List<Factory> factories){
        List<FactorySelected> result = new ArrayList<>();
        for(Factory factory: factories){
            result.add(new FactorySelected(factory));
        }
        return result;
    }
}
