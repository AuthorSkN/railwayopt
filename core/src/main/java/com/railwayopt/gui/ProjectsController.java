package com.railwayopt.gui;

import com.railwayopt.entity.Project;
import com.railwayopt.gui.custom.ProjectShared;
import com.railwayopt.gui.custom.ProjectString;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ProjectsController implements Controller{

    @FXML
    private Label emptyProject;

    private ProjectString selectedProjectString = null;

    @FXML
    private VBox projectsList;
    @FXML
    private AnchorPane projectSharedPanel;
    @FXML
    private Button buttonAddProject;
    private Map<Integer, Project> projects;



    @Override
    public void initializeScene() {
        projects = new HashMap<>();
        for(int i=0; i < 10; i++){
            ProjectString string = new ProjectString(i, "Новое имя "+i, "12-34-1234", 2, 10, "Складнев");
            string.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ProjectString projectString = (ProjectString) event.getSource();
                    if(selectedProjectString != null){
                        selectedProjectString.getStyleClass().remove("list-string-project-selected");
                        selectedProjectString.getStyleClass().add("list-string-project");
                    }
                    projectString.getStyleClass().remove("list-string-project");
                    projectString.getStyleClass().add("list-string-project-selected");
                    selectedProjectString = projectString;
                    resetProjectForShared();
                }
            });
            projectsList.getChildren().add(string);
            projects.put(i, new Project(i, "Новое имя "+i, "Некоторый проект "+i+", который содержит станции и производства."));
        }

    }

    private void resetProjectForShared() {
        projectSharedPanel.getChildren().remove(0);
        Project project = projects.get(selectedProjectString.getProjectId());
        ProjectShared shared = new ProjectShared(project);
        projectSharedPanel.getChildren().add(shared);
        AnchorPane.setRightAnchor(shared, 0.0);
        AnchorPane.setLeftAnchor(shared, 0.0);
        AnchorPane.setTopAnchor(shared, 0.0);
        AnchorPane.setBottomAnchor(shared, 0.0);
    }

    public void addProject(){
        SceneManager.showAddProjectDialog();
        System.out.print("Super uper");
    }
}
