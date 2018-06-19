package com.railwayopt.gui.custom;

import com.railwayopt.entity.Project;
import com.railwayopt.entity.Station;
import com.railwayopt.model.Solution;
import com.railwayopt.model.clustering.ClusteringAnalizer;
import com.railwayopt.model.clustering.Element;
import com.railwayopt.model.clustering.kmeanspro.ProjectedCluster;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KNRCData extends TitledPane {

    private static final String FXML_COMPONENT_NAME = "scenes/knrc_data.fxml";

    private class ShareKP{

        private String name;
        private double weight;
        private int countFactory;

        public ShareKP(String name, double weight, int countFactory) {
            this.name = name;
            this.weight = weight;
            this.countFactory = countFactory;
        }

        @Override
        public String toString() {
            return name+" (производств: "+countFactory+", сгружаемый объем: "+weight+" т.) ";
        }
    }

    @FXML
    private Label textFullWeight;
    @FXML
    private Label textTraffic;
    @FXML
    private Label textAvgDistance;
    @FXML
    private ListView<String> listKP;

    private Map<Integer, Station> projectStation = new HashMap<>();

    public KNRCData(Project project, Solution solution, int knrcId){
        super();
        for(Station station: project.getStations()){
            this.projectStation.put(station.getId(), station);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            ProjectedCluster secondCluster =  solution.getClusterByKNRCId(knrcId);
            ClusteringAnalizer analizer = new ClusteringAnalizer();
            this.setText(projectStation.get(secondCluster.getCentre().getId()).getName());
            double fullWeight = secondCluster.getClusterWeight();
            double traffic = analizer.getSumWeightDistanceToCentre(secondCluster);
            double avgDistance = analizer.getAvgDistanceToCentre(secondCluster);
            textFullWeight.setText("Суммарный объем КНРЦ: "+Double.toString(fullWeight)+" т.");
            textTraffic.setText("Объем перевозок: "+Double.toString(traffic)+" т*км");
            textAvgDistance.setText("Среднее расстояние до КП: "+avgDistance+" км.");
            List<String> shareKPs = new ArrayList<>();
            for(Element element: solution.getKPByKNRCId(knrcId)){
                shareKPs.add(shareKP(projectStation.get(element.getId()).getName(), element.getWeight(),
                        solution.getCountFactoryInKP(element.getId())));
            }
            listKP.setItems(FXCollections.observableArrayList(shareKPs));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String shareKP(String name, double weight, int countFactory) {
            return name+" (производств: "+countFactory+", сгружаемый объем: "+weight+" т.) ";
    }

}
