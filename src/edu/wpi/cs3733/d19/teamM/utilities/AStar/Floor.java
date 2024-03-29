package edu.wpi.cs3733.d19.teamM.utilities.AStar;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Floor {
    private static Floor thisFloor;
    Map<String, Node> nodes;
    SearchAlgorithm dijkstra, aStar, selected;
    Searchable bfs, dfs, selected2;


    private Floor(){
        nodes = new HashMap<>();

        //Path finders
        aStar = new AStar();
        dfs = new DFS();
        bfs = new BFS();
        dijkstra = new Dijkstra();
        selected = aStar;
        selected2 = null;
        try {
            this.populate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    synchronized public static Floor getFloor(){
        if(thisFloor == null){
            thisFloor = new Floor();
        }
        return thisFloor;
    }

    public void setAStar(){
        selected = aStar;
        selected2 = null;
    }
    public void setBFS(){
        selected2 = bfs;
        selected = null;
    }
    public void setDFS(){
        selected2 = dfs;
        selected = null;
    }
    public void setDijkstra() {
        selected = dijkstra;
        selected2 = null;
    }

    /**
     * Find the path between a start and end node
     * @param start - The node to start at
     * @param end - The node to end at
     * @return A Path
     */
    public Path findPath(Node start, Node end){
        Path p = null;
        if(selected != null){
            p = selected.findPath(start, end);
            return p;
        }
        p = selected2.findPath(start,end);
        return p;
    }

    public Path findPresetPath(Node start, String type,Map<String, Node> n){
        Path p = null;
        if(selected != null){
            p = selected.findPresetPath(start,type,n);
            return p;
        }
        p = selected2.findPresetPath(start,type,n);
        return p;
    }


    /**
     * Get a map of all the nodes
     * @return Map of all the nodes
     */
    synchronized public Map<String, Node> getNodes(){
        return nodes;
    }


    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /**
     * This method will take in the list of Nodes and Produce a transparent image with the nodes and path.
     * This version of the method will the locally store the image, while the other will actually transfer the image itself
     * @param paths - A Path
     */
    public Image drawPath(List<Path> paths) {
        return null;
        /*int width = 5000; //The given width and height of the image
        int height = 3400;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f;
        Graphics2D g2d = img.createGraphics();
        g2d.setStroke(new BasicStroke(15.0f));
        List<Node> allNodes = new ArrayList<>();
        //use the nodes to create points for each of the nodes (black points)
        for (Node node : allNodes) {
            int diameter = 0; //Diameter of the circle
            g2d.setColor(Color.BLACK);
            Shape circle = new Ellipse2D.Double(node.getXCoord() - diameter / 2.0, node.getYCoord() - diameter / 2.0, diameter, diameter); //Draw the circle
            g2d.draw(circle);
        }

        for (Path p : paths) {
            List<Node> nodes = p.getPath();
            for (int i = 0; i < nodes.size() - 1; i++) //For every node except the last one
            {
                allNodes.add(nodes.get(i));
                Node firstNode = nodes.get(i);
                Node secondNode = nodes.get(i + 1);
                g2d.setColor(hex2Rgb("#002041"));
                g2d.rotate(0);
                g2d.drawLine(firstNode.getXCoord(), firstNode.getYCoord(), secondNode.getXCoord(), secondNode.getYCoord()); //Draw a line from it to the next node

                g2d.setFont(new Font("Open Sans", Font.PLAIN, 14));
                g2d.setColor(hex2Rgb("#f6bd38"));
                if (secondNode.getYCoord() - firstNode.getYCoord() > 10 && Math.abs(secondNode.getXCoord() - firstNode.getXCoord()) < 20) {
                    //Line is moving down.
                    g2d.drawString("", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2) - 5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2));
                }
                else if (secondNode.getYCoord() - firstNode.getYCoord() < -10 && Math.abs(secondNode.getXCoord() - firstNode.getXCoord()) < 20) {
                    //Line is moving down.
                    g2d.drawString("↑", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2) - 5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2));
                }

                else if (secondNode.getXCoord() - firstNode.getXCoord() > 10 && Math.abs(secondNode.getYCoord() - firstNode.getYCoord()) < 20) {
                    //Line is moving down.
                    g2d.drawString("→", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2), firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }
                else if (secondNode.getXCoord() - firstNode.getXCoord() < -10 && Math.abs(secondNode.getYCoord() - firstNode.getYCoord()) < 20) {
                    //Line is moving down.
                    g2d.drawString("←", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2), firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }
                else if(secondNode.getXCoord() - firstNode.getXCoord() < -10 && secondNode.getYCoord() - firstNode.getYCoord() > 10){
                    g2d.drawString("↙", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2)-5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }
                else if(secondNode.getXCoord() - firstNode.getXCoord() > 10 && secondNode.getYCoord() - firstNode.getYCoord() > 10){
                    g2d.drawString("↘", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2)-5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }
                else if(secondNode.getXCoord() - firstNode.getXCoord() < -10 && secondNode.getYCoord() - firstNode.getYCoord() < -10){
                    g2d.drawString("↖", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2)-5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }
                else if(secondNode.getXCoord() - firstNode.getXCoord() > 10 && secondNode.getYCoord() - firstNode.getYCoord() < -10){
                    g2d.drawString("↗", firstNode.getXCoord() + ((secondNode.getXCoord() - firstNode.getXCoord()) / 2)-5, firstNode.getYCoord() + ((secondNode.getYCoord() - firstNode.getYCoord()) / 2)+5);
                }


            }
        }

        return SwingFXUtils.toFXImage(img, null);*/
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
     * Populates the map edges
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