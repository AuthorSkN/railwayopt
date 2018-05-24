package com.railwayopt.gui.custom;

import com.railwayopt.entity.Project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProjectShared extends VBox{

    private static final String FXML_COMPONENT_NAME = "scenes/project_shared.fxml";

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
