package com.railwayopt.model.clustering.kmeanspro;

import org.junit.*;

import java.util.*;

public class KProInitializerTest {

    @Test
    public void getKInitPointRandomTest(){
        ProjectionPoint[] points = {new ProjectionPoint(0, false),
                new ProjectionPoint(1, false),
                new ProjectionPoint(2, true),
                new ProjectionPoint(3, false),
                new ProjectionPoint(4, true),
                new ProjectionPoint(5, false)};
        List<ProjectionPoint> pointList = Arrays.asList(points);
        KProInitializer initializer = new KProInitializer();
        initializer.setK(3);
        initializer.setProjectionPoints(pointList);
        List<ProjectionPoint> superPoints = initializer.getInitPoints();
        List<ProjectionPoint> duplicateSuperPoints = new LinkedList<>();
        Assert.assertEquals(superPoints.size(), 3);
        for(ProjectionPoint point: superPoints){
            Assert.assertFalse(point.isStaticCentre());
            if(!pointList.contains(point))
                Assert.fail();
            if(!duplicateSuperPoints.contains(point)){
                duplicateSuperPoints.add(point);
            }else{
                Assert.fail();
            }
        }

    }

}
