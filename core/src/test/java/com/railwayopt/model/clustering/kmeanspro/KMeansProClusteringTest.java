package com.railwayopt.model.clustering.kmeanspro;

import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class KMeansProClusteringTest {

    @Test
    public void searchNearestClusterTest(){
        KMeansProClustering clustering = new KMeansProClustering(0);
        Element element = new Element(0, 4, 6);
        //Элемент id=1 ближайший
        ProjectedCluster[] clusters = {new ProjectedCluster(new ProjectionPoint(1, 2, 2)),
                                        new ProjectedCluster(new ProjectionPoint(2, 6, 2)),
                                        new ProjectedCluster(new ProjectionPoint(3, 11, 2))};
        ProjectedCluster result = (ProjectedCluster) clustering.searchNearestCluster(element, Arrays.asList(clusters));
        Assert.assertEquals(result.getCentre().getId(), 1);
    }

    @Test
    public void projectionTest(){
        KMeansProClustering clustering = new KMeansProClustering(0);
        ProjectedCluster cluster = new ProjectedCluster(new ProjectionPoint(0));
        cluster.setElements(Arrays.asList(new Element[]{new Element(1,1,6),
                                                        new Element(2,4,6),
                                                        new Element(3,5,4)}));
        //Ближайшая к реальному центру точка - id=21
        ProjectionPoint[] projectionPoints = {new ProjectionPoint(21, 2,2),
                                                new ProjectionPoint(22, 8,2),
                                                new ProjectionPoint(23, 11,2)};
        clustering.projection(cluster, Arrays.asList(projectionPoints));
        Assert.assertTrue(cluster.getCentre().getId() == 21);
    }

    @Test
    public void clusteringCommonPositionTest() {
        Element[] elements = new Element[]{new Element(1,6,10),
                                            new Element(2,5,14),
                                            new Element(3,6,13),
                                            new Element(4,7,14),
                                            new Element(5,8,12),
                                            new Element(6,13,9),
                                            new Element(7,13,12),
                                            new Element(8,14,10),
                                            new Element(9,14,12),
                                            new Element(10,15,14),
                                            new Element(11,15,11),
                                            new Element(12, 15,9),
                                            new Element(13,16,9),
                                            new Element(14,17,10),
                                            new Element(15,17,8),
                                            new Element(16,21,6),
                                            new Element(17,21,3),
                                            new Element(18,22,7),
                                            new Element(19,23,9),
                                            new Element(20,24,6),
                                            new Element(21,24,8),
                                            new Element(22, 26,7)};
        ProjectionPoint[] projectionPoints = new ProjectionPoint[]{new ProjectionPoint(1,25,8),
                                                                    new ProjectionPoint(2,22,5),
                                                                    new ProjectionPoint(3,17,5),
                                                                    new ProjectionPoint(4,13,10),
                                                                    new ProjectionPoint(5,8,13),
                                                                    new ProjectionPoint(6,4,9),
                                                                    new ProjectionPoint(7,3,6)};
        projectionPoints[5].setIsStaticCentre(true);
        KPresentProInitializer initializer = new KPresentProInitializer();
        Set<Integer> selectedAddId = new HashSet<>();
        selectedAddId.add(1);
        selectedAddId.add(2);
        initializer.setProjectionId(selectedAddId);
        KMeansProClustering clustering = new KMeansProClustering(3);
        clustering.setClusterInitializer(initializer);
        clustering.setElements(Arrays.asList(elements));
        clustering.setProjectionPoints(Arrays.asList(projectionPoints));
        for(int i = 0; i < 3; i++) {
            List<ProjectedCluster> clusters = (List<ProjectedCluster>)clustering.clustering();
            List<ProjectedCluster> assertClusters = new LinkedList<>();
            ProjectedCluster cluster1 = new ProjectedCluster(projectionPoints[5]);
            cluster1.setElements(Arrays.asList(new Element[]{elements[0], elements[1], elements[2], elements[3], elements[4]}));
            ProjectedCluster cluster2 = new ProjectedCluster(projectionPoints[3]);
            cluster2.setElements(Arrays.asList(new Element[]{elements[5], elements[6], elements[7], elements[8], elements[9], elements[10], elements[11], elements[12],
                                                                elements[13], elements[14],}));
            ProjectedCluster cluster3 = new ProjectedCluster(projectionPoints[1]);
            cluster3.setElements(Arrays.asList(new Element[]{elements[15], elements[16], elements[17], elements[18], elements[19], elements[20], elements[21]}));
            assertClusters.add(cluster1);
            assertClusters.add(cluster2);
            assertClusters.add(cluster3);
            Assert.assertTrue(assertClusters.containsAll(clusters));
        }
    }



}
