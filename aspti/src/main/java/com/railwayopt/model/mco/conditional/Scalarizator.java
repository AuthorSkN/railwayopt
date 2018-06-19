package com.railwayopt.model.mco.conditional;

import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.mco.unconditional.*;

import java.util.*;

public class Scalarizator implements Comparator<Optimizable> {

    private List<Criterion> criteria;

    public Scalarizator(List<Criterion> criteria){
        this.criteria = criteria;
    }

    @Override
    public int compare(Optimizable o1, Optimizable o2) {
        return (int)(scalarization(o1) - scalarization(o2));
    }

    public double scalarization(Optimizable object){
        Map<Criterion, Double> objectParameterValues = object.parametersMapping(criteria);
        double scalar = 0;
        for(Criterion criterion: criteria){
            if(criterion.getOptimumDirection() == Criterion.MAX_OPTIMUM_DIRECTION){
                scalar += objectParameterValues.get(criterion);
            }else{
                scalar -= objectParameterValues.get(criterion);
            }
        }
        return scalar;
    }


}
