package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Project;
import com.railwayopt.gui.custom.selectdata.FactorySelected;
import com.railwayopt.gui.custom.selectdata.RegionGroupForSelectFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;


public class AddProjectDialogController implements Controller {

    private static Project newProject = new Project(0, "", "");

    @FXML
    private TextField textFieldNameProject;
    @FXML
    private TextArea textAreaProjectDescription;

    @FXML
    private VBox vboxFactories;

    List<Factory> factoriesFromDB;

    @Override
    public void initializeScene() {
        factoriesFromDB = DB.getAllFactories();
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

    @FXML
    public void loadFromDB(){
        vboxFactories.getChildren().add(new RegionGroupForSelectFactory(FactorySelected.convertToSelected(factoriesFromDB)));

    }

}
