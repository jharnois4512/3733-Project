package edu.wpi.cs3733.d19.teamM.controllers.ServiceRequests;

import com.jfoenix.controls.JFXCheckBox;
import edu.wpi.cs3733.d19.teamM.Main;
import edu.wpi.cs3733.d19.teamM.User.User;
import edu.wpi.cs3733.d19.teamM.utilities.Clock;
import edu.wpi.cs3733.d19.teamM.utilities.DatabaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LanguageRequests implements Initializable {

    @FXML
    private Text userText;

    @FXML
    private javafx.scene.control.Label lblClock;

    @FXML
    private javafx.scene.control.Label lblDate;

    @FXML
    private Text userText;

    ObservableList<String> languages = FXCollections.observableArrayList("Acholi",
            "Afrikaans", "Akan", "Albanian","Amharic","Arabic", "Ashante", "Asl", "Assyrian", "Azerbaijani", "Azeri", "Bajuni", "Basque",
            "Behdini", "Belorussian", "Bengali", "Berber", "Bosnian", "Bravanese", "Bulgarian","Burmese", "Cakchiquel", "Cambodian", "Cantonese","Catalan",
            "Chaldean", "Chamorro", "Chao-chow", "Chavacano", "Chin", "Chuukese", "Cree", "Croatian", "Czech", "Dakota", "Danish", "Dari", "Dinka",
            "Diula", "Dutch", "Edo", "English", "Estonian", "Ewe", "Fante", "Farsi", "Fijian Hindi", "Finnish", "Flemish", "French", "French Canadian", "Fukienese",
            "Fula", "Fulani", "Fuzhou", "Ga", "Gaddang", "Gaelic", "Gaelic-irish", "Gaelic-scottish", "Georgian", "German", "Gorani", "Greek", "Gujarati",
            "Haitian Creole", "Hakka", "Hakka-chinese", "Hausa", "Hebrew", "Hindi", "Hmong", "Hungarian", "Ibanag", "Ibo", "Icelandic", "Igbo", "Ilocano",
            "Indonesian", "Inuktitut", "Italian", "Jakartanese", "Japanese", "Javanese", "Kanjobal", "Karen", "Karenni", "Kashmiri", "Kazakh", "Kikuyu",
            "Kinyarwanda", "Kirundi", "Korean", "Kosovan", "Kotokoli", "Krio", "Kurdish", "Kurmanji", "Kyrgyz", "Lakota", "Laotian",
            "Latvian", "Lingala", "Lithuanian", "Luganda", "Luo", "Maay", "Macedonian", "Malay", "Malayalam", "Maltese", "Mandarin", "Mandingo", "Mandinka",
            "Marathi", "Marshallese", "Mien", "Mina", "Mirpuri", "Moldavan", "Mongolian", "Montenegrin", "Navajo", "Neapolitan", "Nepali", "Nigerian Pidgin",
            "Norwegian", "Oromo", "Pahari", "Papago", "Papiamento", "Pashto", "Patois", "Pidgin English", "Polish", "Portug.creole", "Portuguese", "Pothwari",
            "Pulaar", "Punjabi", "Putian", "Quichua", "Romanian", "Russian", "Samoan", "Serbian", "Shanghainese", "Shona", "Sichuan", "Sicilian", "Sinhalese", "Slovak",
            "Somali", "Sorani", "Spanish", "Sudanese Arabic", "Sundanese", "Susu", "Swahili", "Swedish", "Sylhetti", "Tagalog", "Taiwanese", "Tajik", "Tamil",
            "Telugu", "Thai", "Tibetan", "Tigre", "Tigrinya", "Toishanese", "Tongan", "Toucouleur", "Trique", "Tshiluba", "Turkish", "Twi", "Ukrainian", "Urdu", "Uyghur", "Uzbek",
            "Vietnamese", "Visayan", "Welsh", "Wolof", "Yiddish", "Yoruba", "Yupik");

    //Tesxt Field for flower type input
    @FXML
    private TextField Language;
    @FXML
    private ListView available;


    //Text field for room location input
    @FXML
    private TextField room;

    @FXML
    private JFXCheckBox Urgent;

    //Text field for additional specifications
    @FXML
    private javafx.scene.control.TextArea notes;

    @FXML
    private Button submitReuqest;


    /**
     * This method is for the logout button which allows the user to go back to the welcome screen
     * @throws Exception: Any exception that is encountered
     */
    @FXML
    public void logout() throws Exception {
        Main.setScene("welcome");
    }

    /**
     * This method is for the logout button which allows the user to go back to the welcome screen
     * @throws Exception: Any exception that is encountered
     */
    @FXML
    private void navigateBack() throws Exception {
        Main.setScene("serviceRequests");
    }

    @FXML
    private void goToList() throws Exception {
        Main.setScene("serviceRequestsList");
    }

    /**
     * This method allows the user to create a flowers request using the button
     * @param : The action that is associated with making the flowers request
     */

    @FXML
    public void makeLanguageRequest() throws IOException {
        new ServiceRequests().makeRequest("interpreter", room.getText(), Language.getText(), notes.getText(), Urgent.isSelected());

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
//TODO add user text
        new Clock(lblClock, lblDate);
        ObservableList<String> list = FXCollections.observableArrayList();

        String query = "select * FROM users Where ACCOUNTINT = ?";
        Connection conn = new DatabaseUtils().getConnection();
        try{
            PreparedStatement s = conn.prepareStatement(query);
            s.setInt(1, 2);
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                list.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
            for(String s1 : list){
                available.getItems().add(s1);
            }
            conn.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        userText.setText(User.getUsername());
        TextFields.bindAutoCompletion(Language,languages);
        userText.setText(User.getUsername());
    }
}


