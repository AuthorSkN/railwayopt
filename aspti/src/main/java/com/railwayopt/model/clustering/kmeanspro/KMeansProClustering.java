package com.railwayopt.model.clustering.kmeanspro;


import com.railwayopt.model.clustering.*;

import java.util.*;

public class KMeansProClustering implements Clustering {

    private int k;
    private List<Element> elements;
    private List<ProjectionPoint> projectionPoints;
    private KProInitializer clusterInitializer = new KProInitializer();

    public KMeansProClustering(int k){
        this.k = k;
    }

    @Override
    public void setElements(List<Element> elements) {
        this.elements = elements;
    }


    public void setProjectionPoints(List<ProjectionPoint> projectionPoints){
        clusterInitializer.setProjectionPoints(projectionPoints);
        this.projectionPoints = projectionPoints;
    }

    public List<Element> getElements() {
        return elements;
    }

    public List<ProjectionPoint> getProjectionPoints() {
        return projectionPoints;
    }

    public int getK() {
        return k;
    }

    public KProInitializer getClusterInitializer() {
        return clusterInitializer;
    }

    public void setClusterInitializer(KProInitializer clusterInitializer) {
        this.clusterInitializer = clusterInitializer;
    }

    @Override
    public List<? extends Cluster> clustering() {
        List<ProjectedCluster> clusters = null;
        try {
             clusters = new LinkedList<>();

            //Определение статических центов и начальных центров для дополнительных кластеров
            List<ProjectionPoint> staticCentres = new LinkedList<>();
            for (ProjectionPoint projectionPoint : projectionPoints) {
                if (projectionPoint.isStaticCentre())
                    staticCentres.add(projectionPoint);
            }
            int additionalK = k - staticCentres.size();
            clusterInitializer.setK(additionalK);
            List<ProjectionPoint> initialProjectionsForAddClusters = clusterInitializer.getInitPoints();

            //Инициализация кластеров
            for (ProjectionPoint staticCentre : staticCentres) {
                clusters.add(new ProjectedCluster(staticCentre));
            }
            for (ProjectionPoint additionalCentre : initialProjectionsForAddClusters) {
                clusters.add(new ProjectedCluster(additionalCentre));
            }
            for (Element element : elements) {
                Cluster nearestCluster = searchNearestCluster(element, clusters);
                nearestCluster.addElement(element);
            }

            //Цикл оптимизации
            List<ProjectedCluster> prevClusters = new LinkedList<>();
            while (!isEndOptimization(prevClusters, clusters)) {
                //сохранение предыдущей итерации
                prevClusters.clear();
                for (ProjectedCluster cluster : clusters) {
                    prevClusters.add((ProjectedCluster) cluster.clone());
                }
                //Перепроецирование не статических кластеров
                for(ProjectedCluster cluster: clusters){
                    if(!cluster.getCentre().isStaticCentre()){
                        projection(cluster, projectionPoints);
                    }
                }
                //Перепривязка элементов к новым проекциям
                for(ProjectedCluster cluster: clusters){
                    cluster.clear();
                }
                for (Element element : elements) {
                    Cluster nearestCluster = searchNearestCluster(element, clusters);
                    nearestCluster.addElement(element);
                }
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }

        return clusters;
    }


    public Cluster searchNearestCluster(Element element, List<? extends Cluster> clusters){
        Cluster nearestCluster = null;
        double nearestDistance = Double.MAX_VALUE;
        for(Cluster cluster: clusters){
            double distance = element.distanceTo(cluster.getCentre());
            if(distance < nearestDistance){
                nearestCluster = cluster;
                nearestDistance = distance;
            }
        }
        return nearestCluster;
    }

    public void projection(ProjectedCluster cluster, List<ProjectionPoint> projectionPoints){
        if(cluster.getSize() != 0) {
            Point realClusterCentre = cluster.getRealCentre();
            double minDistance = Double.MAX_VALUE;
            ProjectionPoint nearestProjectionPoint = null;
            for (ProjectionPoint projectionPoint : projectionPoints) {
                if (!projectionPoint.isStaticCentre()) {
                    double distance = realClusterCentre.distanceTo(projectionPoint);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestProjectionPoint = projectionPoint;
                    }
                }
            }
            cluster.setCentre(nearestProjectionPoint);
        }
    }

    private boolean isEndOptimization(List<ProjectedCluster> prevClusters, List<ProjectedCluster> clusters){
        if ((prevClusters == null) || (clusters == null))
            return false;

        if (prevClusters.size() != clusters.size()){
            return false;
        }
        boolean res = true;
        Map<ProjectionPoint, ProjectedCluster> mapClusters = new HashMap<>();
        for(ProjectedCluster cluster: clusters){
            mapClusters.put(cluster.getCentre(), cluster);
        }

        for(ProjectedCluster prevCluster: prevClusters){
            ProjectedCluster cluster = mapClusters.get(prevCluster.getCentre());
            if( !prevCluster.equals(cluster) ){
                res = false;
                break;
            }
        }

        return res;
    }

}
