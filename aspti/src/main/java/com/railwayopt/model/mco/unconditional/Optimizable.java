package com.railwayopt.model.mco.unconditional;

import com.railwayopt.exceptions.RailwayOptException;

import java.util.*;

public interface Optimizable {

    int getId();

    Map<Criterion, Double> parametersMapping(List<Criterion> criterionList) throws RailwayOptException;

}
