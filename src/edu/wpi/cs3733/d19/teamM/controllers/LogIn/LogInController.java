package edu.wpi.cs3733.d19.teamM.controllers.LogIn;

/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import edu.wpi.cs3733.d19.teamM.utilities.General.Encrypt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import edu.wpi.cs3733.d19.teamM.Main;
import edu.wpi.cs3733.d19.teamM.User.User;
import edu.wpi.cs3733.d19.teamM.utilities.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.TextField;

public class LogInController {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML // fx:id="mainContent"
    private AnchorPane mainContent; // Value injected by FXMLLoader

    @FXML // fx:id="mediaView"
    private MediaView mediaView; // Value injected by FXMLLoader

    @FXML
    void guestLogIn(ActionEvent event) {
        try {
            User.getUser();
            User.setUsername("Guest");
            username.setText("");
            password.setText("");
            errorMessage.setText("");
            User.setPrivilege(0);
            System.out.println("Logged in Guest");
            Main.loadScenes();
            Main.setScene("home");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void logIn(ActionEvent event) {
        /*
        TODO add username to pages: scheduler, service requests, sr list
         */
        try {
            String query = "SELECT * from USERS where USERNAME = ?";
            Connection conn = new DatabaseUtils().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username.getText());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (Encrypt.getMd5(password.getText()).compareTo(rs.getString("USERPASS")) == 0) {
                User.getUser();
                User.setUsername(rs.getString("USERNAME"));
                User.setPathToPic(rs.getString("pathtopic"));
                username.setText("");
                password.setText("");
                errorMessage.setText("");
                User.setPrivilege(rs.getInt("ACCOUNTINT"));
                System.out.println("Logged in " + User.getUsername() + " with privilege " + User.getPrivilege());
                Main.loadScenes();
                Main.setScene("home");
                conn.close();
                //Main.startIdleCheck();
                //uncomment this line to start using idle check
                if(User.getUsername().compareTo(Main.savedState.getUserName()) != 0){
                    Main.savedState.setUserName(User.getUsername());
                    Main.savedState.setState("home");
                }
                System.out.println(User.getUsername());
                System.out.println(Main.savedState.getUserName());
                System.out.println(Main.savedState.getState());
                Main.setScene(Main.savedState.getState());
            } else {
               System.out.println("user not found");
               errorMessage.setText("Incorrect Credentials");
               conn.close();
           }
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("User " + username.getText() + " Not Found");
            username.setText("");
            password.setText("");
        }

    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        //Gets the video resources and stores it as a media file
        Media media = new Media(getClass().getResource("/resources/Pressure.mp4").toExternalForm());

        //Creates the media player to play the video
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        //Sets the video on infinite loop and passes it to the mediaView to display on the screen
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaView.setMediaPlayer(mediaPlayer);

        assert mainContent != null : "fx:id=\"mainContent\" was not injected: check your FXML file 'login.fxml'.";
        assert mediaView != null : "fx:id=\"mediaView\" was not injected: check your FXML file 'login.fxml'.";

    }
}
