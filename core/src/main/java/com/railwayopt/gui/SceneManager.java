package com.railwayopt.gui;


import com.railwayopt.exceptions.SystemException;
import com.sun.glass.ui.Size;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Класс, позволяющий устанавливать сцены по требованию.
 * Требует предварительной инициализации.
 */
public class SceneManager {

    //Пути к стилям
    private static final String PATH_BASE_STYLE = "light_metro_style.css";

    //Пути к сценам
    private static final String PATH_AUTHORIZATION_SCENE = "scenes/hello.fxml";
    private static final String PATH_OPTIMAL_LOGISTIC_CENTRE_SCENE = "scenes/optimal_logistic_centre.fxml";
    private static final String PATH_PROJECTS_SCENE = "scenes/projects.fxml";

    //Стандартные размеры сцен
    private static final Size AUTHORIZATION_SCENE_SIZE = new Size(320, 160);
    private static final Size OPTIMAL_LOGISTIC_CENTRE_SCENE_SIZE = new Size(1000, 700);
    private static final Size AS_PTI_SCENE_SIZE = new Size(1000, 700);

    //Заголовки сцен
    private static final String AUTHORIZATION_TITLE = "Авторизация";
    private static final String OPTIMAL_LOGISTIC_CENTRE_TITLE = "Выбор оптимального КНРЦ";
    private static final String AS_PTI_SCENE_TITLE = "АС ПТИ";

    private static Stage stage;

    private static Controller installSceneByParameters(String path, String title, Size size) throws SystemException {
        Controller sceneController = null;
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneManager.class.getResource(path));
            Parent root = fxmlLoader.load();
            sceneController = fxmlLoader.getController();
            Scene scene = new Scene(root, size.width, size.height);
            scene.getStylesheets().add(SceneManager.class.getResource(PATH_BASE_STYLE).toExternalForm());
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

}
