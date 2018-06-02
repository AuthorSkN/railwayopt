package com.railwayopt.model.clustering;

import com.railwayopt.model.location.Point;

import java.util.List;

public interface StartClusteringInitializer {

    List<? extends Point> getInitPoints();

}
