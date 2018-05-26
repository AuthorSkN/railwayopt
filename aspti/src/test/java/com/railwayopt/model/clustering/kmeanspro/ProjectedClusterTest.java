package com.railwayopt.model.clustering.kmeanspro;

import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ProjectedClusterTest {

    @Test
    public void equalsTest(){
        ProjectedCluster cluster1 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)},
                                                        new ProjectionPoint(5));
        ProjectedCluster cluster2 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)},
                                                        new ProjectionPoint(5));
        Assert.assertTrue(cluster1.equals(cluster2));
    }

    @Test
    public void notEqualsDifferentElementIdsTest(){
        ProjectedCluster cluster1 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)},
                                                                                 new ProjectionPoint(5));
        ProjectedCluster cluster2 = createCluster(new Element[]{new Element(1), new Element(4, 2,4), new Element(3)},
                                                                                new ProjectionPoint(5));
        Assert.assertFalse(cluster1.equals(cluster2));
    }

    @Test
    public void notEqualsDifferentElementPointsTest(){
        ProjectedCluster cluster1 = createCluster(new Element[]{new Element(1, 3, 0), new Element(2, 1,4), new Element(3)},
                                                                                 new ProjectionPoint(5));
        ProjectedCluster cluster2 = createCluster(new Element[]{new Element(1,-1, 0), new Element(2, 2,4), new Element(3)},
                                                                                 new ProjectionPoint(5));
        Assert.assertFalse(cluster1.equals(cluster2));
    }

    @Test
    public void notEqualsDifferentCentreTest(){
        ProjectedCluster cluster1 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)},
                new ProjectionPoint(5, 1, 5));
        ProjectedCluster cluster2 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)},
                new ProjectionPoint(7, 1, 6));
        Assert.assertFalse(cluster1.equals(cluster2));
    }

    private ProjectedCluster createCluster(Element[] elements, ProjectionPoint centre){
        ProjectedCluster cluster  = new ProjectedCluster(centre);
        cluster.setElements(Arrays.asList(elements));
        return cluster;
    }
}
