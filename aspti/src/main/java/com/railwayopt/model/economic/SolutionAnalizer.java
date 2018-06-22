package com.railwayopt.model.economic;

import com.railwayopt.model.clustering.*;
import com.railwayopt.model.clustering.kmeanspro.ProjectionPoint;
import com.railwayopt.model.location.Point;

import java.util.Collection;

public class SolutionAnalizer
{

    public double getSumDistanceToCentre(Cluster cluster){
        double sumDistance = 0;
        Point clusterCentre = cluster.getCentre();
        for(Element element: cluster){
            sumDistance += element.distanceTo(clusterCentre);
        }
        return sumDistance;
    }

    public double getSumDistanceForClustering(Collection<? extends Cluster> clusters){
        double sumDistance = 0;
        for(Cluster cluster:clusters){
            sumDistance += getSumDistanceToCentre(cluster);
        }
        return sumDistance;
    }

    public double getSumWeightDistanceToCentre(Cluster cluster){
        double sumDistance = 0;
        Point clusterCentre = cluster.getCentre();
        for(Element element: cluster){
            sumDistance += element.distanceTo(clusterCentre)*element.getWeight();
        }
        return sumDistance;
    }

    public double getSumWeightDistanceFotClustering(Collection<? extends Cluster> clusters){
        double sumDistance = 0;
        for(Cluster cluster:clusters){
            sumDistance += getSumWeightDistanceToCentre(cluster);
        }
        return sumDistance;
    }

    public double getEconomCriterionByParameters(Collection<? extends Cluster> clusters, double tariff, double costBuild){
        double costTransaction = getSumWeightDistanceFotClustering(clusters);
        int countNewCentre = 0;
        for(Cluster cluster: clusters){
            ProjectionPoint centre = (ProjectionPoint)cluster.getCentre();
            if (!centre.isStaticCentre()){
                countNewCentre++;
            }
        }
        return (costTransaction*tariff) + (countNewCentre*costBuild);
    }

    public double getAvgDistanceToCentre(Cluster cluster){
        double avg = 0.0;
        for(Element element: cluster){
            avg += element.distanceTo(cluster.getCentre());
        }
        return avg/cluster.getSize();
    }

    public double getAvgDistanceForAllClusters(Collection<? extends Cluster> clusters){
        double avg = 0.0;
        for(Cluster cluster: clusters){
            avg += getAvgDistanceToCentre(cluster);
        }
        return  avg/clusters.size();
    }

}
