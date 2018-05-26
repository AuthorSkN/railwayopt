package com.railwayopt.model.clustering.kmeanspro;

import com.railwayopt.model.clustering.*;

import java.util.*;

public class KProInitializer implements StartClusteringInitializer {

    protected int k;
    protected List<ProjectionPoint> projectionPoints;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public List<ProjectionPoint> getProjectionPoints() {
        return projectionPoints;
    }

    public void setProjectionPoints(List<ProjectionPoint> projectionPoints) {
        this.projectionPoints = projectionPoints;
    }

    @Override
    public List<ProjectionPoint> getInitPoints() {
        List<ProjectionPoint> freePoints = new LinkedList<>();
        for(ProjectionPoint point: projectionPoints)
            if (!point.isStaticCentre())
                freePoints.add(point);
        Random r = new Random();
        int size = freePoints.size();
        ArrayList<ProjectionPoint> result = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            int randIndex = r.nextInt(size);
            ProjectionPoint randPoint = freePoints.remove(randIndex);
            result.add(i, randPoint);
            size--;
        }
        return result;
    }
}
