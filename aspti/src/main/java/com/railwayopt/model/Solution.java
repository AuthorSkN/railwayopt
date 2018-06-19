package com.railwayopt.model;

import com.railwayopt.model.clustering.Cluster;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.ProjectedCluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private int id;
    private Map<Integer, ProjectedCluster> firstLayer = new HashMap<>();
    private Map<Integer, ProjectedCluster> secondLayer = new HashMap<>();
    private List<ProjectedCluster> firstLayerList;
    private List<ProjectedCluster> secondLayerList;
    private String descr;
    private double tariff;
    private double costBuildKP;
    private double costBuildKNRC;
    private int projectId;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Solution(List<ProjectedCluster> firstLayerList, List<ProjectedCluster> secondLayerList){
        for(ProjectedCluster cluster:firstLayerList){
            firstLayer.put(cluster.getCentre().getId(), cluster);
        }
        this.firstLayerList = firstLayerList;
        for(ProjectedCluster cluster:secondLayerList){
            secondLayer.put(cluster.getCentre().getId(), cluster);
        }
        this.secondLayerList = secondLayerList;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public double getTariff() {
        return tariff;
    }

    public void setTariff(double tariff) {
        this.tariff = tariff;
    }

    public double getCostBuildKP() {
        return costBuildKP;
    }

    public void setCostBuildKP(double costBuildKP) {
        this.costBuildKP = costBuildKP;
    }

    public double getCostBuildKNRC() {
        return costBuildKNRC;
    }

    public void setCostBuildKNRC(double costBuildKNRC) {
        this.costBuildKNRC = costBuildKNRC;
    }

    public Collection<Integer> getAllKNRCId(){
        return secondLayer.keySet();
    }

    public List<Element> getKPByKNRCId(int knrcId){
        return secondLayer.get(knrcId).getElements();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountKP(){
        return firstLayer.values().size();
    }

    public ProjectedCluster getClusterByKNRCId(int knrcId){
        return secondLayer.get(knrcId);
    }

    public int getCountFactoryInKP(int kpId){
        return firstLayer.get(kpId).getSize();
    }

    public List<ProjectedCluster> getFirstLayerList() {
        return firstLayerList;
    }

    public List<ProjectedCluster> getSecondLayerList() {
        return secondLayerList;
    }

    public Map<Integer, ProjectedCluster> getFirstLayer() {
        return firstLayer;
    }

    public Map<Integer, ProjectedCluster> getSecondLayer() {
        return secondLayer;
    }
}
