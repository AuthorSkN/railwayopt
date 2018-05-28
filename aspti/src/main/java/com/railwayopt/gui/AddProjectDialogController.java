package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.dataimport.XlsLoader;
import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Project;
import com.railwayopt.gui.custom.selectdata.FactorySelected;
import com.railwayopt.gui.custom.selectdata.RegionGroupForSelectFactory;
import com.railwayopt.model.RegionManager;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class AddProjectDialogController implements Controller {

    private static Project newProject = new Project(0, "", "");

    @FXML
    private TextField textFieldNameProject;
    @FXML
    private TextArea textAreaProjectDescription;
    @FXML
    private VBox vboxFactories;
    @FXML
    private CheckBox checkSelectAll;

    private List<Factory> factoriesFromDB;
    private RegionManager regionManager = new RegionManager();

    @Override
    public void initializeScene() {
        factoriesFromDB = DB.getAllFactories();
        checkSelectAll.selectedProperty().addListener(this::selectAll);
    }

    @FXML
    public void selectData(){
        newProject.setName(textFieldNameProject.getText());
        newProject.setDescription(textAreaProjectDescription.getText());
        SceneManager.installSelectDataSceneForAddProjectDialog();
    }

    public static Project getNewProject(){
        return newProject;
    }

    public void selectAll(ObservableValue observableValue, Boolean oldValue, Boolean newValue){
        if (newValue && !oldValue) {
            for(Node group: vboxFactories.getChildren()){
                ((RegionGroupForSelectFactory)group).selectAll(true);
            }
        }else if (!newValue && oldValue){
            for(Node group: vboxFactories.getChildren()){
                ((RegionGroupForSelectFactory)group).selectAll(false);
            }
        }

    }

    @FXML
    public void loadFromDB(){
        Map<String, List<FactorySelected>> regionGroups = regionManager.groupingByRegion(FactorySelected.convertToSelected(factoriesFromDB));
        Set<String> foundRegions = regionGroups.keySet();
            for(String region: foundRegions){
            vboxFactories.getChildren().add(new RegionGroupForSelectFactory(region, regionGroups.get(region)));
        }
    }

    @FXML
    public void loadFromFile(){
        File file = SceneManager.showOpenFileDialog("Загрузить файл", "Excel файл", "*");
        XlsLoader loader = null;
        try {
            loader = new XlsLoader(file);
            List<Factory> factories = loader.readProductions();
            Map<String, List<FactorySelected>> regionGroups = regionManager.groupingByRegion(FactorySelected.convertToSelected(factories));
            Set<String> foundRegions = regionGroups.keySet();
            for(String region: foundRegions){
                vboxFactories.getChildren().add(new RegionGroupForSelectFactory(region, regionGroups.get(region)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
