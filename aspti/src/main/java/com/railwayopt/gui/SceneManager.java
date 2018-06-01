package com.railwayopt.gui;


import com.railwayopt.entity.Factory;
import com.railwayopt.entity.Project;
import com.railwayopt.entity.Station;
import com.railwayopt.exceptions.SystemException;
import com.sun.glass.ui.Size;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Класс, позволяющий устанавливать сцены по требованию.
 * Требует предварительной инициализации.
 */
public class SceneManager {

    //Пути к стилям
    private static final String PATH_BASE_STYLE = "light_metro_style.css";
    private static final String PATH_CUSTOM_STYLE = "custom/style/custom.css";

    //Пути к сценам
    private static final String PATH_AUTHORIZATION_SCENE = "scenes/hello.fxml";
    private static final String PATH_OPTIMAL_LOGISTIC_CENTRE_SCENE = "scenes/optimal_logistic_centre.fxml";
    private static final String PATH_PROJECTS_SCENE = "scenes/projects.fxml";
    private static final String PATH_ADD_PROJECT_DIALOG_SCENE = "scenes/add_project_dialog.fxml";
    private static final String PATH_ADD_PROJECT_DIALOG_SELECT_SCENE = "scenes/add_project_dialog_select.fxml";
    private static final String PATH_SOLUTIONS_SCENE = "scenes/solutions.fxml";
    private static final String PATH_CREATE_SOLUTION_SCENE = "scenes/create_solution_dialog.fxml";

    //Стандартные размеры сцен
    private static final Size AUTHORIZATION_SCENE_SIZE = new Size(320, 160);
    private static final Size OPTIMAL_LOGISTIC_CENTRE_SCENE_SIZE = new Size(1000, 700);
    private static final Size AS_PTI_SCENE_SIZE = new Size(1100, 700);
    private static final Size ADD_PROJECT_DIALOG_SIZE = new Size(600, 600);
    private static final Size CREATE_SOLUTION_DIALOG_SIZE = new Size(700, 700);

    //Заголовки сцен
    private static final String AUTHORIZATION_TITLE = "Авторизация";
    private static final String OPTIMAL_LOGISTIC_CENTRE_TITLE = "Выбор оптимального КНРЦ";
    private static final String AS_PTI_SCENE_TITLE = "АС ПТИ";
    private static final String ADD_PROJECT_DIALOG_TITLE = "Создание проекта";
    private static final String CREATE_SOLUTION_DIALOG_TITLE = "Создание решения проекта";

    private static Stage stage;
    private static Stage addProjectDialog;
    private static Stage createSolutionDialog;

    private static Controller installSceneByParameters(String path, String title, Size size) throws SystemException {
        Controller sceneController = null;
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(path));
            Parent root = fxmlLoader.load();
            sceneController = fxmlLoader.getController();
            Scene scene = new Scene(root, size.width, size.height);
            scene.getStylesheets().add(SceneManager.class.getResource(PATH_BASE_STYLE).toExternalForm());
            scene.getStylesheets().add(SceneManager.class.getResource(PATH_CUSTOM_STYLE).toExternalForm());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IllegalStateException | IOException  exc) {
            exc.printStackTrace();
            throw new SystemException();
        }
        return sceneController;
    }

    /**
     * Инициализирует менеджер главной формой.
     * @param mainStage главная форма
     */
    public static void init(Stage mainStage){
        stage = mainStage;
    }


    /**
     * Устанавливает сцену авторизации
     */
    public static void installAuthorizationScene() throws SystemException{
        installSceneByParameters(PATH_AUTHORIZATION_SCENE, AUTHORIZATION_TITLE, AUTHORIZATION_SCENE_SIZE);
    }

    public static void installOptimalLogisticCentreScene() throws SystemException{
        installSceneByParameters(PATH_OPTIMAL_LOGISTIC_CENTRE_SCENE, OPTIMAL_LOGISTIC_CENTRE_TITLE,
                                                                        OPTIMAL_LOGISTIC_CENTRE_SCENE_SIZE);
    }

    public static void installProjectsScene() throws SystemException{
        ProjectsController controller = (ProjectsController) installSceneByParameters(PATH_PROJECTS_SCENE, AS_PTI_SCENE_TITLE, AS_PTI_SCENE_SIZE);
        controller.initializeScene();
    }

    public static void installSolutionsScene(Project project){
        SolutionsController controller = (SolutionsController) installSceneByParameters(PATH_SOLUTIONS_SCENE, AS_PTI_SCENE_TITLE, AS_PTI_SCENE_SIZE);
        controller.setProject(project);
    }

    public static CreateSolutionDialogController showCreateSolutionDialog(Collection<Factory> factories, Collection<Station> stations){
        CreateSolutionDialogController controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(PATH_CREATE_SOLUTION_SCENE));
            Parent root = fxmlLoader.load();
            createSolutionDialog = new Stage();
            createSolutionDialog.setScene(new Scene(root, ADD_PROJECT_DIALOG_SIZE.width, ADD_PROJECT_DIALOG_SIZE.height));
            controller = fxmlLoader.getController();
            createSolutionDialog.setTitle(CREATE_SOLUTION_DIALOG_TITLE);
            createSolutionDialog.initOwner(stage);
            createSolutionDialog.initModality(Modality.APPLICATION_MODAL);
            createSolutionDialog.showAndWait();
        } catch (IllegalStateException | IOException  exc) {
            exc.printStackTrace();
            throw new SystemException();
        }
        return controller;
    }


    public static void installSelectDataSceneForAddProjectDialog(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(PATH_ADD_PROJECT_DIALOG_SELECT_SCENE));
            Parent root = fxmlLoader.load();
            addProjectDialog.setScene(new Scene(root, 700,700));
            fxmlLoader.<Controller>getController().initializeScene();
        } catch (IllegalStateException | IOException  exc) {
            exc.printStackTrace();
            throw new SystemException();
        }
    }

    public static AddProjectDialogController showAddProjectDialog(){
        AddProjectDialogController controller;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(PATH_ADD_PROJECT_DIALOG_SCENE));
            Parent root = fxmlLoader.load();
            addProjectDialog = new Stage();
            controller = fxmlLoader.getController();
            addProjectDialog.setTitle(ADD_PROJECT_DIALOG_TITLE);
            addProjectDialog.setScene(new Scene(root, CREATE_SOLUTION_DIALOG_SIZE.width, CREATE_SOLUTION_DIALOG_SIZE.height));
            addProjectDialog.initOwner(stage);
            addProjectDialog.initModality(Modality.APPLICATION_MODAL);
            addProjectDialog.showAndWait();
        } catch (IllegalStateException | IOException  exc) {
            exc.printStackTrace();
            throw new SystemException();
        }
        return controller;
    }

    public static void addProjectDialogClose(){
        addProjectDialog.close();
    }

    /**
     * Выводит сообщение об ошибке
     * @param headMessage главный текст
     * @param description описание
     */
    public static void showErrorMessage(String headMessage, String description){
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Ошибка");
        errorDialog.setHeaderText(headMessage);
        errorDialog.setContentText(description);
        errorDialog.showAndWait();
    }

    public static File showOpenFileDialog(String title, String description, String extensions){
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle(title);//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter(description, extensions);//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(addProjectDialog);
    }


}
