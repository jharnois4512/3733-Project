package edu.wpi.cs3733.d19.teamM.utilities.AStar;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {

    Map<String, Node> nodes;

    //Path finders
    Searchable dfs, bfs, selected;
    AStar aStar;

    public Floor(){
        nodes = new HashMap<>();

        //Path finders
        aStar = new AStar();
        dfs = new DFS();
        bfs = new BFS();
        selected = aStar;
        try {
            this.populate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Find the path between a start and end node
     * @param start - The node to start at
     * @param end - The node to end at
     * @return A Path
     */
    public Path findPath(Node start, Node end){
        return selected.findPath(start, end);
    }

    public Path findPresetPath(Node start, String type){
        return aStar.findPresetPath(start, type, this.nodes);
    }

    /**
     * Get a map of all the nodes
     * @return Map of all the nodes
     */
    public Map<String, Node> getNodes(){
        return nodes;
    }

    /**
     * This method will take in the list of Nodes and Produce a transparent image with the nodes and path.
     * This version of the method will the locally store the image, while the other will actually transfer the image itself
     * @param p - A Path
     */
    public Image drawPath(List<Path> paths) {
        int width = 5000; //The given width and height of the image
        int height = 3400;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f;
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(15.0f));
        List<Node> allNodes = new ArrayList<>();
        for (Path p : paths) {
            List<Node> nodes = p.getPath();
            for (int i = 0; i < nodes.size() - 1; i++) //For every node except the last one
            {
                allNodes.add(nodes.get(i));
                Node firstNode = nodes.get(i);
                Node secondNode = nodes.get(i + 1);
                g2d.drawLine(firstNode.getXCoord(), firstNode.getYCoord(), secondNode.getXCoord(), secondNode.getYCoord()); //Draw a line from it to the next node
            }
        }
        //use the nodes to create points for each of the nodes (black points)
        for (Node node : allNodes) {
            int diameter = 7; //Diameter of the circle
            g2d.setColor(Color.YELLOW);
            Shape circle = new Ellipse2D.Double(node.getXCoord() - diameter / 2.0, node.getYCoord() - diameter / 2.0, diameter, diameter); //Draw the circle
            g2d.draw(circle);
        }
        return SwingFXUtils.toFXImage(img, null);
    }

    public void rePopulate(){
        //TODO: Look into obtaining result sets of only changed nodes
    }

    /**
     * Populate map edges and map nodes
     * @throws Exception
     */
    public void populate() throws Exception {
        populateNodes();
        populateEdges();
    }

    /**
     * Populates the map nodes
     * @throws Exception
     */
    private void populateNodes() throws Exception{
        String cmd = "select * from node";
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection conn = DriverManager.getConnection("jdbc:derby:myDB;create=true");
        Statement stmt = conn.createStatement();
        ResultSet set = stmt.executeQuery(cmd);
        while (set.next()) {
            String id = set.getString("nodeID");
            String floor = set.getString("floor");
            String building = set.getString("building");
            String type = set.getString("nodeType");
            String longName = set.getString("longName");
            String shortName = set.getString("shortName");
            int x = set.getInt("XCoord");
            int y = set.getInt("YCoord");
            Node n = new Node(id, x, y, floor, building, type, longName, shortName);
            nodes.put(id, n);
        }
        conn.close();
    }

    /**
     * Populares the map edges
     * @throws Exception
     */
    private void populateEdges() throws Exception{
        String cmd = "select * from edge";
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection conn = DriverManager.getConnection("jdbc:derby:myDB;create=true");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(cmd);
        Node n1, n2;
        String startingNode, endingNode, edgeID;

        while (rs.next()) {
            startingNode = rs.getString("startNode");
            endingNode = rs.getString("endNode");
            edgeID = rs.getString("edgeID");
            if(nodes.containsKey(startingNode) && nodes.containsKey(endingNode)){
                n1 = nodes.get(startingNode);
                n2 = nodes.get(endingNode);
                n1.addEdge(n2, edgeID);
                n2.addEdge(n1, edgeID);
            }
        }
        conn.close();
    }
}