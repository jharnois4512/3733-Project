package edu.wpi.cs3733.d19.teamM.utilities.AStar;

import java.util.*;

/**
 * Breadth first search, doesn't account for path weights
 */
public class BFS implements Searchable{

    private PriorityQueue<Node> queue;
    private Map<String, Node> visited;

    /**
     * Consturctor for Bredth first search
     */
    public BFS(){
    }

    /**
     * Find the path between two nodes
     * @param start The start of the path
     * @param end The end of the path
     * @return A path object
     */
    public Path findPath(Node start, Node end){
        List<Node> queue = new ArrayList<>();
        visited = new HashMap<>();
        List<Node> toAdd;
        Node cur = start;
        cur.setParent(null);
        Node eN;
        // add start to queue
        start.setG(0);
        queue.add(start);
        // add start to visited
        visited.put(start.getId(), start);

        while (queue.size() > 0 && !cur.getId().equals(end.getId())){ // while not empty
            toAdd = new ArrayList<>();
            queue.remove(0);
            //  > once in queue mark as visited
            for (Edge e : cur.getEdgeList()){ // for all of that nodes neighbors
                eN = e.getEndNode();
                if (!beenVisited(eN) && eN.isEnabled()){ // if hasn't been visited
                    eN.setParent(cur); // set parent to current
                    setHeuristics(eN, end);
                    toAdd.add(eN);
                    visited.put(eN.getId(), eN);

                }
            }
            // get next node in queue
            addToQueue(queue, toAdd);
            cur = queue.get(0);
        }
        Path p = new Path();
        if (cur.getId().equals(end.getId())){
            while (cur.getParent() != null){
                p.add(cur);
                cur = cur.getParent();
            }
        }
        else {
            System.out.println("No path");
        }
        p.add(start);
        return p;
    }

    public Path findPresetPath(Node start, String destType, Map<String, Node> map){
        Iterator it = map.entrySet().iterator();
        Node closest = null;
        start.setG(0);
        double lowestCost = 500000;
        while(it.hasNext()) {
            Map.Entry set = (Map.Entry) it.next();
            Node n = (Node) set.getValue();
            n.setG(n.getDistance(start)); //set its g, h, f, and parent
            n.setP(getDeltaFloor(n, start) * 500);
            n.setB(getDeltaBuilding(n, start) * 1000);
            n.setF(n.getG() + n.getP() + n.getB());
            if (n != null && (n.getNodeType().equals(destType))) {
                System.out.println("Checking Node "+   n.getId());
                if(n.getF() < lowestCost){
                    closest = n;
                    lowestCost = n.getF();
                }
            }
        }
        if(closest != null){
            return findPath(start, closest);
        }
        else{
            return null;
        }
    }

    private void addToQueue(List<Node> queue, List<Node> toAdd){
        Collections.sort(toAdd);
        for (Node n : toAdd){
            queue.add(n);
        }
    }

    final protected void setHeuristics(Node curNode, Node endNode){
        curNode.setH(curNode.getDistance(endNode));
        curNode.setG(curNode.getParent().getDistance(curNode) + curNode.getParent().getG());
        curNode.setB(getDeltaBuilding(curNode, endNode) * 5000);
        if (curNode.getB() == 0) {
            curNode.setP(getDeltaFloor(curNode, endNode) * 1000);
        }
        else {
            curNode.setF(getDeltaFloor(curNode, new Node("", 0, 0, "2", "", "", "", "")) * 1000);
        }
        curNode.setF(curNode.getG() + curNode.getP() + curNode.getH() + curNode.getB());
    }

    private double getDeltaFloor(Node n1, Node n2){
        /*
        L2 - 0
        L1 - 1
        G  - 2
        1  - 3
        2  - 4
        3  - 5
        */
        double floor1 = getFloorValue(n1.getFloor());
        double floor2 = getFloorValue(n2.getFloor());
        return Math.abs(floor1 - floor2);
    }

    private double getFloorValue(String f){
        if (f.equals("L2")) return  0;
        else if (f.equals("L1")) return  1;
        else if (f.equals("G")) return  2;
        else if (f.equals("1")) return  4;
        else if (f.equals("2")) return  5;
        else if (f.equals("3")) return  6;
        else if (f.equals("4")) return  5;
        return -1;
    }

    private double getBuildingValue(String build){
        if(build.equals("BTM")) return 0;
        else if(build.equals("Shapiro")) return 1;
        else if (build.equals("Tower")) return 2;
        else if(build.equals("45 Francis")) return 2;
        else if(build.equals("15 Francis")) return 2;
        return -1;
    }

    private double getDeltaBuilding(Node n1, Node n2){
        /*
        BTM - 0
        Shapiro  - 1
        Tower  - 2
        45 Francis  - 3
        15 Francis  - 4
        */
        double n1BuildingValue = getBuildingValue(n1.getBuilding());
        double n2BuildingValue = getBuildingValue(n2.getBuilding());
        return Math.abs(n1BuildingValue - n2BuildingValue);
    }

    /**
     * Checks if the node has been visited before
     *
     * @param n - Node to check
     * @return If it has been visited
     */
    private boolean beenVisited(Node n){return this.visited.containsKey(n.getId());}

}
