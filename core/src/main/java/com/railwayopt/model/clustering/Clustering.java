package com.railwayopt.model.clustering;


import java.util.List;

public interface Clustering {

    void setElements(List<Element> elements);

    List<? extends Cluster> clustering();
}
