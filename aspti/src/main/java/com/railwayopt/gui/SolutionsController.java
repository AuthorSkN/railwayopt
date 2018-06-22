package com.railwayopt.gui;

import com.railwayopt.entity.Project;
import com.railwayopt.gui.custom.SolutionShared;
import com.railwayopt.gui.custom.SolutionString;
import com.railwayopt.util.DateManager;
import com.railwayopt.Solution;
import com.railwayopt.SolutionStorage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionsController implements Controller{

    private Project project;
    private Map<Integer, Solution> solutions;

    @FXML
    private Label labelProjectName;
    @FXML
    private VBox vboxSolutions;
    @FXML
    private AnchorPane solutionSharedPanel;

    private SolutionString selectedSolutionString;

    public void setProject(Project project){
        this.project = project;
        labelProjectName.setText(project.getName());
    }

    @Override
    public void initializeScene() {
        List<Solution> solutionsFromDB = SolutionStorage.getAllSolution(project.getId());
        solutions = new HashMap<>();
        if (solutionsFromDB != null) {
            vboxSolutions.getChildren().remove(1, vboxSolutions.getChildren().size());
            for (Solution solution : solutionsFromDB) {
                solutions.put(solution.getId(), solution);

                SolutionString solutionString = new SolutionString(solution);
                solutionString.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        SolutionString solutionString = (SolutionString) event.getSource();
                        if (selectedSolutionString != null) {
                            selectedSolutionString.getStyleClass().remove("list-string-project-selected");
                            selectedSolutionString.getStyleClass().add("list-string-project");
                        }
                        solutionString.getStyleClass().remove("list-string-project");
                        solutionString.getStyleClass().add("list-string-project-selected");
                        selectedSolutionString = solutionString;
                        resetSolutionForShared();
                    }
                });
                vboxSolutions.getChildren().add(solutionString);

            }
        }
    }

    @FXML
    public void createSolution() {
        CreateSolutionDialogController controller = SceneManager.showCreateSolutionDialog(project.getFactories(), project.getStations());
        Solution solution = controller.getSolution();
        solution.setProjectId(project.getId());
        solution.setDate(DateManager.getNowDate());
        SolutionStorage.addSolution(solution);
        initializeScene();
    }

    private void resetSolutionForShared() {
        solutionSharedPanel.getChildren().remove(0);
        Solution solution = solutions.get(selectedSolutionString.getSolutionId());
        SolutionShared shared = new SolutionShared(project, solution);
        solutionSharedPanel.getChildren().add(shared);
        AnchorPane.setRightAnchor(shared, 0.0);
        AnchorPane.setLeftAnchor(shared, 0.0);
        AnchorPane.setTopAnchor(shared, 0.0);
        AnchorPane.setBottomAnchor(shared, 0.0);
    }
}
