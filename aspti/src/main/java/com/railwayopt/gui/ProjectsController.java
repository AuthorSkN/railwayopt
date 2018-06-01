package com.railwayopt.gui;

import com.railwayopt.DB;
import com.railwayopt.entity.Project;
import com.railwayopt.gui.custom.ProjectShared;
import com.railwayopt.gui.custom.ProjectString;
import com.railwayopt.model.DateManager;
import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsController implements Controller{

    @FXML
    private Label emptyProject;

    private ProjectString selectedProjectString = null;

    @FXML
    private VBox vboxProjectsList;
    @FXML
    private AnchorPane projectSharedPanel;
    @FXML
    private Button buttonAddProject;

    private Map<Integer, Project> projects;



    @Override
    public void initializeScene() {
        List<Project> projectsFromDB = DB.getAllProjects();
        projects = new HashMap<>();
        vboxProjectsList.getChildren().remove(1, vboxProjectsList.getChildren().size());
        for(Project project: projectsFromDB){
            projects.put(project.getId(), project);
            ProjectString projectString = new ProjectString(project.getId(),project.getName(), project.getDate(),
                        project.getStations().size(), project.getFactories().size(), project.getAuthor());
            projectString.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
            vboxProjectsList.getChildren().add(projectString);
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
        Project newProject = AddProjectDialogController.getNewProject();
        if(newProject != null){
            newProject.setDate(DateManager.getNowDate());
            DB.addProject(newProject);
            initializeScene();
        }
    }

}
