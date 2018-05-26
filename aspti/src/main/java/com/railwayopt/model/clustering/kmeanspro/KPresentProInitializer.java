package com.railwayopt.model.clustering.kmeanspro;

import java.util.*;

/**
 * Class for testing clustering
 */
public class KPresentProInitializer extends KProInitializer{

    private Set<Integer> projectionId;

    public KPresentProInitializer(){}

    public KPresentProInitializer(Set<Integer> projectionId){
        this.projectionId = projectionId;
        this.k = projectionId.size();
    }

    public Set<Integer> getProjectionId() {
        return projectionId;
    }

    public void setProjectionId(Set<Integer> projectionId) {
        this.projectionId = projectionId;
        this.k = projectionId.size();
    }

    @Override
    public List<ProjectionPoint> getInitPoints(){
        HashMap<Integer, ProjectionPoint> map = new HashMap<>();
        for(ProjectionPoint projectionPoint: projectionPoints){
            map.put(projectionPoint.getId(), projectionPoint);
        }
        List<ProjectionPoint> points = new LinkedList<>();
        for(Integer id: projectionId){
            points.add(map.get(id));
        }
        return points;
    }
}
