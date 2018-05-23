package com.railwayopt.gui;

import com.railwayopt.gui.custom.ProjectString;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjectsController implements Controller{

    @FXML
    private Label about;
    @FXML
    private Button buttonAddString;

    @FXML
    private VBox projectsList;


    @FXML
    public void onClick(){
        System.out.println("EEeeeeee");
    }

    @FXML
    public void onCursor(){
        System.out.println("Super");
    }

    @FXML
    public void addString(){
        ProjectString string = new ProjectString(0, "Новое имя", "12-34-1234", 2, 10, "Складнев");
        projectsList.getChildren().add(string);
    }

    @Override
    public void initializeScene() {

    }
}
