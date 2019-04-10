package edu.wpi.cs3733.d19.teamM;

import edu.wpi.cs3733.d19.teamM.utilities.DatabaseUtils;
import edu.wpi.cs3733.d19.teamM.utilities.AStar.Floor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.InputStream;
import java.net.URL;

/**
 * Primary class that runs the JavaFx Application
 */
public class Main extends Application {

    //Create the primary stage and set it to null
    private static Stage primaryStage = null;

    private static Parent homePane;
    private static Parent adminPane;
    private static Parent pathFindingPane;
    private static Parent schedulerPane;
    private static Parent serviceRequestPane;
    private static Parent serviceRequestListPane;
    private static Parent welcomePane;
    private static Parent loginPane;
    private static Parent addUserPane;

    private static Scene homeScene;
    private static Scene adminScene;
    private static Scene pathFindingScene;
    private static Scene schedulerScene;
    private static Scene serviceRequestScene;
    private static Scene serviceRequestListScene;
    private static Scene welcomeScene;
    private static Scene loginScene;
    private static Scene addUserScene;


    /**
     * This method is to return the current stage we are working on for referencing the stage
     * @return Stage: The current stage we are using
     */
    public static Stage getStage(){
        return Main.primaryStage;
    }

    public static void setScene(String scene){
        if(scene == "addUser"){
            primaryStage.setScene(addUserScene);
        }
        else if(scene == "admin"){
            primaryStage.setScene(adminScene);
        }
        else if(scene == "pathfinding"){
            primaryStage.setScene(pathFindingScene);
        }
        else if(scene == "scheduling"){
            primaryStage.setScene(schedulerScene);
        }
        else if(scene == "serviceRequest"){
            primaryStage.setScene(serviceRequestScene);
        }
        else if(scene == "serviceRequestList"){
            primaryStage.setScene(serviceRequestListScene);
        }
        else if(scene == "welcome"){
            primaryStage.setScene(welcomeScene);
        }
        else if(scene == "login"){
            primaryStage.setScene(loginScene);
        }else{
            try{primaryStage.setScene(new Scene(FXMLLoader.load(Main.getFXMLURL(scene))));}catch(Exception e){e.printStackTrace();}
       }
    }

    /**
     * This method is to reference and access FXML pages to display. The name of the page is input and a URL is output
     * @param name: The name of the FXML page we wish to access
     * @return URL: The url of the page we want to access
     */
    public static URL getFXMLURL(String name){
        return Main.class.getResource("views/"+name+".fxml");
    }



    public static InputStream getResource(String path){
        System.out.println("Fetching Resource: "+path);
        return Main.class.getResourceAsStream(path);
    }

    public static InputStream getResourceFromRoot(String path){
        System.out.println("Fetching Resource from Root: "+path);
        return Main.class.getClassLoader().getResourceAsStream(path);
    }

    public static void loadScenes() throws Exception{
        homePane = FXMLLoader.load(Main.getFXMLURL("home"));
        homeScene= new Scene(Main.homePane);

        Runnable loadAdminThread = () -> {
            try {
                System.out.println("Loading scenes");
                adminPane = FXMLLoader.load(Main.getFXMLURL("adminUI"));
                adminScene = new Scene(adminPane);
            }catch(Exception e){
                e.printStackTrace();
            }
        };
        Runnable loadPathfindingThread = () -> {
            try {
                pathFindingPane = FXMLLoader.load(Main.getFXMLURL("pathfinding"));
                pathFindingScene = new Scene(pathFindingPane);
            }catch(Exception e) {
                e.printStackTrace();
            }
        };
        Runnable loadSchedulerThread = () -> {
            try {
                schedulerPane = FXMLLoader.load(Main.getFXMLURL("scheduler"));
                schedulerScene = new Scene(schedulerPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable loadServiceRequestsThread = () -> {
            try {
                serviceRequestPane = FXMLLoader.load(Main.getFXMLURL("serviceRequests"));
                serviceRequestScene = new Scene(serviceRequestPane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable loadSRListThread = () -> {
            try {
                serviceRequestListPane = FXMLLoader.load(Main.getFXMLURL("serviceRequestsList"));
                serviceRequestListScene= new Scene(serviceRequestListPane);
            }catch(Exception e){
                e.printStackTrace();
            }
        };


        new Thread(loadAdminThread).start();
        new Thread(loadPathfindingThread).start();
        new Thread(loadSchedulerThread).start();
        new Thread(loadServiceRequestsThread).start();
        new Thread(loadSRListThread).start();

    }

    public static void loadAddUsers() throws Exception{
        addUserPane = FXMLLoader.load(Main.getFXMLURL("addUser"));
        addUserScene = new Scene(addUserPane);
    }

    /**
     * This method creates and sets the stage of the viewable JavaFX screen
     * @param primaryStage: The stage to display on start
     * @throws Exception: Any possible Exception that may arise while trying to start and display the application
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        //Set the reference to the primary stage
        this.primaryStage = primaryStage;

        //Load the fonts that we want to use for the application
        //Fonts have been taken from what B & H hospital uses as their official fonts
        System.out.println(Main.getResourceFromRoot("resources/palatino-linotype/palab.ttf").toString());
        Font.loadFont(Main.getResourceFromRoot("resources/palatino-linotype/palab.ttf"), 10);
        Font.loadFont(Main.getResourceFromRoot("resources/palatino-linotype/pala.ttf"), 10);

        //Get the main parent scene and load the FXML
        Parent root = FXMLLoader.load(Main.getFXMLURL("welcome"));
        Scene mainScene = new Scene(root);

        loginPane = FXMLLoader.load(Main.getFXMLURL("login"));
        loginScene = new Scene(loginPane);
        welcomePane = FXMLLoader.load(Main.getFXMLURL("welcome"));
        welcomeScene= new Scene(welcomePane);

        //Set the color and the title and the screen
        mainScene.setFill(Color.web("#012d5a"));
        primaryStage.setTitle("Brigham and Women's Hospital");
        primaryStage.setScene(mainScene);
        primaryStage.setMaximized(true);

        //Set the bounds and other height and width attributes
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setFullScreen(true);
        primaryStage.show();


    }

    /**
     * The primary method that is run once the application starts
     * @param args: The arguments for the application, if any (none in this case)
     */
    public static void main(String[] args) {


        //CSVParser parse = new CSVParser("C:\\Users\\kenne\\IdeaProjects\\3733-Project\\src\\app\\nodes.csv", "C:\\Users\\kenne\\IdeaProjects\\3733-Project\\src\\app\\edges.csv");
        //AStar aS = new AStar();
        //Map<String, Node> mappedNodes = parse.getNodes();
//        List<Node> path = aS.findPath(mappedNodes.get("GHALL002L1"), mappedNodes.get("GHALL006L1"));
        //aS.drawPath(path);
        DatabaseUtils parser = new DatabaseUtils();
        parser.connect();
        parser.nodeParse();
        parser.edgeParse();

       /* Floor myFloor = new Floor("1");
        myFloor.populateFloor();*/
        launch(args);
    }

}
