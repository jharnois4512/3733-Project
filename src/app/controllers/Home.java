package app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The controller for the Home screen
 */
public class Home {

    /**
     * This method
     * @throws Exception
     */
    @FXML
    public void navigateToPathfinding() throws Exception{
        Parent pane = FXMLLoader.load(app.Main.getFXMLURL("pathfinding"));
        Scene scene = new Scene(pane);
        app.Main.getStage().setScene(scene);
    }

    @FXML
    public void navigateToServiceRequests() throws Exception{
        Parent pane = FXMLLoader.load(app.Main.getFXMLURL("serviceRequests"));
        Scene scene = new Scene(pane);
        app.Main.getStage().setScene(scene);
    }

    @FXML
    public void logout() throws Exception{
        Parent pane = FXMLLoader.load(app.Main.getFXMLURL("welcome"));
        Scene scene = new Scene(pane);
        app.Main.getStage().setScene(scene);
    }
    @FXML
    public void navigateToScheduling() throws Exception{
        Parent pane = FXMLLoader.load(app.Main.getFXMLURL("scheduler"));
        Scene scene = new Scene(pane);
        app.Main.getStage().setScene(scene);
    }

    @FXML
    public void navigateToAdmin() throws Exception{
        Parent pane = FXMLLoader.load(app.Main.getFXMLURL("adminUI"));
        Scene scene = new Scene(pane);
        app.Main.getStage().setScene(scene);
    }
}
