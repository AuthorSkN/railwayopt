package com.railwayopt.model.mco.unconditional;

import com.railwayopt.exceptions.RailwayOptException;

import java.util.*;

public class OptimizableObject implements Optimizable{

    private int id;
    private Map<Criterion, Double> parameters = new HashMap<>();

    public OptimizableObject(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setParameter(Criterion criterion, double value){
        if(criterion.getOptimumDirection() == Criterion.MIN_OPTIMUM_DIRECTION){
            parameters.put(criterion, -value);
        } else {
            parameters.put(criterion, value);
        }
    }

    public Double getValue(Criterion criterion){
        return parameters.get(criterion);
    }

    @Override
    public Map<Criterion, Double> parametersMapping(List<Criterion> criterionList) throws RailwayOptException{
        return parameters;
    }
}
