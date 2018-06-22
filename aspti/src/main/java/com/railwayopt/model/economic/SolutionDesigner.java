package com.railwayopt.model.economic;

import com.railwayopt.Solution;
import com.railwayopt.entity.*;
import com.railwayopt.model.clustering.*;
import com.railwayopt.model.clustering.kmeanspro.*;
import com.railwayopt.model.location.Point;

import java.util.*;
import java.util.stream.Collectors;

public class SolutionDesigner {

    private int projectId;
    private Map<Integer, Factory> mapFactories = new HashMap<>();
    private Map<Integer, Station> mapStations = new HashMap<>();
    private Map<Integer, Cluster> firstLayer;
    private Map<Integer, Cluster> secondLayer;
    private SolutionAnalizer analizer = new SolutionAnalizer();

    private int countCS;
    private int countCDC;
    private double tariff = 1;
    private double costBuildCS = 1.0;
    private double costBuildCDC = 1.0;
    private int repeateClusteringCount = 20;


    public SolutionDesigner(Project project){
        this.projectId = project.getId();
        project.getFactories().forEach(factory -> this.mapFactories.put(factory.getId(), factory));
        project.getStations().forEach(station -> this.mapStations.put(station.getId(), station));
    }

    public SolutionDesigner(Project project, int countCS, int countCDC){
        this(project);
        this.countCS = countCS;
        this.countCDC = countCDC;
    }

    public void createSolution(){
        List<ProjectionPoint> stationProjectionSet = mapStations.values().stream()
                .map(station -> new ProjectionPoint(station.getId(), convertGeoPointToPlacePoint(station), false)).collect(Collectors.toList());
        List<Element> factoryElements = mapFactories.values().stream()
                .map(factory -> new Element(factory.getId(), convertGeoPointToPlacePoint(factory))).collect(Collectors.toList());

    }

    private void doubleClusteringByCountLogisticCentres(List<Element> factoryElements, List<ProjectionPoint> stationProjectionSet, int countLC1, int countLC2){
        //Создание одно- или двухуровневого решения по заданным параметрам дизайнера
        double criterion = Double.MAX_VALUE;
        List<ProjectedCluster> firstLayerClusteringResult;
        List<ProjectedCluster> secondLayerClusteringResult;
        for (int i = 0; i < repeateClusteringCount; i++) {
            firstLayerClusteringResult = clustering(factoryElements, stationProjectionSet, countCS);

        }
    }

    private Point convertGeoPointToPlacePoint(Infrastructable infrastructure){
        return new Point(infrastructure.getX(), infrastructure.getY());
    }

    private List<ProjectedCluster> clustering(List<Element> elements, List<ProjectionPoint> projectionPoints, int k) {
        KProInitializer initializer = new KProInitializer();
        initializer.setK(k);
        KMeansProClustering clustering = new KMeansProClustering(countCS);
        clustering.setClusterInitializer(initializer);
        clustering.setProjectionPoints(projectionPoints);
        clustering.setElements(elements);
        return (List<ProjectedCluster>) clustering.clustering();
    }


    public void changeCDCInCluster(int oldCDCId, int newCDCId){
        //Изменение КНРЦ
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCountCS() {
        return countCS;
    }

    public void setCountCS(int countCS) {
        this.countCS = countCS;
    }

    public int getCountCDC() {
        return countCDC;
    }

    public void setCountCDC(int countCDC) {
        this.countCDC = countCDC;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    public double getCostBuildCS() {
        return costBuildCS;
    }

    public void setCostBuildCS(double costBuildCS) {
        this.costBuildCS = costBuildCS;
    }

    public double getCostBuildCDC() {
        return costBuildCDC;
    }

    public void setCostBuildCDC(double costBuildCDC) {
        this.costBuildCDC = costBuildCDC;
    }

    public Solution getSolution(){
        return null;
    }

    public SolutionReport getSolutionReport(){
        return null;
    }

    public static SolutionReport createSolutionReport(Solution solution){
        return null;
    }

    public int getRepeateClusteringCount() {
        return repeateClusteringCount;
    }

    public void setRepeateClusteringCount(int repeateClusteringCount) {
        this.repeateClusteringCount = repeateClusteringCount;
    }
}
