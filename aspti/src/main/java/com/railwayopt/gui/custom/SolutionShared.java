package com.railwayopt.gui.custom;

import com.railwayopt.entity.Project;
import com.railwayopt.gui.SceneManager;
import com.railwayopt.gui.custom.shareddata.SharedFactory;
import com.railwayopt.gui.custom.shareddata.SharedStation;
import com.railwayopt.model.Solution;
import com.railwayopt.model.XlsSaver;
import com.railwayopt.model.clustering.ClusteringAnalizer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SolutionShared extends VBox {

    private static final String FXML_COMPONENT_NAME = "scenes/solution_shared.fxml";

    private Solution solution;

    @FXML
    private TextArea textDesc;
    @FXML
    private Label textCostKP;
    @FXML
    private Label textCostKNRC;
    @FXML
    private Label textTariff;
    @FXML
    private Label textfullCriterion;
    @FXML
    private VBox vboxKNRCs;

    private Project project;

    public SolutionShared(Project project, Solution solution){
        super();
        this.solution = solution;
        this.project = project;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            textDesc.setText(solution.getDescr());
            textCostKP.setText("Цена постройки одного КП: "+String.format(Locale.FRENCH, "%12.2f", solution.getCostBuildKP())+"р. ");
            textCostKNRC.setText("Цена постройки одного КНРЦ : "+String.format(Locale.FRENCH, "%13.2f", solution.getCostBuildKNRC())+"р. ");
            textTariff.setText("Тариф перевозки: "+String.format(Locale.FRENCH, "%5.2f", solution.getTariff())+" р.");
            ClusteringAnalizer analizer = new ClusteringAnalizer();
            double commonEconomCriterion = analizer.getEconomCriterionByParameters(solution.getFirstLayerList(), solution.getTariff(), solution.getCostBuildKP())
                    + analizer.getEconomCriterionByParameters(solution.getSecondLayerList(), solution.getTariff(), solution.getCostBuildKNRC());
            textfullCriterion.setText("Общие затраты на структуру: "+Double.toString(commonEconomCriterion)+" р.");
            Collection<Integer> knrcIds = solution.getAllKNRCId();
            for(Integer knrcId: knrcIds){
                vboxKNRCs.getChildren().add(new KNRCData(project, solution, knrcId));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void createReport(){
        File file = SceneManager.openSaveReportDialog();
        try {
            if (file == null) throw new IOException();
            XlsSaver.saveReport(project, solution.getFirstLayer(), solution.getSecondLayer(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
