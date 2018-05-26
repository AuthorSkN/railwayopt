package com.railwayopt.model.clustering;


import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class ClusterTest {

    @Test
    public void equalsTest(){
        Cluster cluster1 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)});
        Cluster cluster2 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)});
        Assert.assertTrue(cluster1.equals(cluster2));
    }

    @Test
    public void notEqualsDifferentIdTest(){
        Cluster cluster1 = createCluster(new Element[]{new Element(1), new Element(2, 2,4), new Element(3)});
        Cluster cluster2 = createCluster(new Element[]{new Element(1), new Element(4, 2,4), new Element(3)});
        Assert.assertFalse(cluster1.equals(cluster2));
    }

    @Test
    public void notEqualsDifferentPointTest(){
        Cluster cluster1 = createCluster(new Element[]{new Element(1, 3, 0), new Element(2, 1,4), new Element(3)});
        Cluster cluster2 = createCluster(new Element[]{new Element(1,-1, 0), new Element(2, 2,4), new Element(3)});
        Assert.assertFalse(cluster1.equals(cluster2));
    }

    private Cluster createCluster(Element[] elements){
        Cluster cluster  = new Cluster();
        cluster.setElements(Arrays.asList(elements));
        return cluster;
    }


}
