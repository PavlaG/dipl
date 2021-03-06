package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.BlockComparator;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author Pavla
 */
public class Dijkstra extends Pathfinder {

    PriorityQueue<Block> unvisited = new PriorityQueue<Block>(new BlockComparator());
    boolean finishFound = false;

    public Dijkstra(Graph graph) {
        super(graph);
    }

    @Override
    public boolean findPath() {
        resetValues();
        start.setStartDistance(0);
        start.countFinishDistance(finish);
        start.setPreviousBlock(null);
        unvisited.addAll(blocks);
        Block current = new Block();
        current = start;
        while (!finishFound) {
            steps++;
            current = unvisited.poll();
            if (current.getStartDistance() >= (Integer.MAX_VALUE)) {
                return false;
            }
            if (current.equals(finish)) {
                finishFound = true;
            } else {
                for (Edge edge : current.getEdges()) {
                    if (unvisited.contains(edge.getBlockTo())) {
                        edge.getBlockTo().countFinishDistance(finish);
                        if (edge.getBlockTo().getStartDistance() > (current.getStartDistance() + edge.getWeight())) {
                            edge.getBlockTo().setPreviousBlock(current);
                            edge.getBlockTo().setStartDistance(current.getStartDistance() + edge.getWeight());
                            unvisited.remove(edge.getBlockTo());
                            unvisited.add(edge.getBlockTo());
                        }
                    }
                }
            }

        }
        if (finish.getStartDistance() > Integer.MAX_VALUE - 3) {
            return false;
        } else {
            // System.out.println("finish real distance: " + finish.getStartDistance());
            return true;
        }
    }

    public ArrayList<Point> getLineGraph() {
//        System.out.println("tart: " + start.toString());
//        System.out.println("finish" + finish.toString());
        ArrayList<Point> path = new ArrayList<>();
        Block b = finish;
        path.add(b.getCenter());
        b = b.getPreviousBlock();
        while (!b.equals(start)) {
            path.add(b.getCenter());
            path.add(b.getCenter());
            b = b.getPreviousBlock();
        }
        path.add(start.getCenter());
        return path;
    }

    public ArrayList<Block> getBlockGraph() {
        ArrayList<Block> path = new ArrayList<>();
        Block b = finish;
        while (!b.equals(start)) {
            path.add(b);
            b = b.getPreviousBlock();
        }
        path.add(start);
        return path;
    }

    private void resetValues() {
        for (Block block : blocks) {
            block.resetSomeValues();
        }
    }
}
