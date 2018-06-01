package com.railwayopt.gui.custom;

import com.railwayopt.entity.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProjectShared extends VBox{

    private static final String FXML_COMPONENT_NAME = "scenes/project_shared.fxml";

    private class SharedFactory extends Factory {

    }

    private class SharedStation extends Station {

        public SharedStation(int id, String name, Double latitude, Double longitude)
        {
            super(id, name, latitude, longitude);
        }
    }

    private Project project;

    @FXML
    private Label projectName;
    @FXML
    private TextArea projectDesc;



    public ProjectShared(Project project){
        super();
        this.project = project;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_COMPONENT_NAME));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        try {
            fxmlLoader.load();
            projectName.setText(project.getName());
            projectDesc.setText(project.getDescription());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
