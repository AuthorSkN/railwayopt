package com.railwayopt.gui;

import com.railwayopt.entity.Project;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class SolutionsController implements Controller{

    private Project project;

    @FXML
    private Label labelProjectName;

    public void setProject(Project project){
        this.project = project;
        labelProjectName.setText(project.getName());
    }

    @Override
    public void initializeScene() { }

    @FXML
    public void createSolution() {
        SceneManager.showCreateSolutionDialog(project.getFactories(), project.getStations());
    }
}
