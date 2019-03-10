/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.BlockComparator;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import sun.jvm.hotspot.memory.Universe;

/**
 *
 * @author Pavla
 */
public class AlgorithmName extends Pathfinder {

    //List<Block> unvisited = new ArrayList();
    PriorityQueue<Block> unvisited = new PriorityQueue<Block>(new BlockComparator());
    boolean finishFound = false;

    public AlgorithmName(Graph graph) {
        super(graph);

    }

    @Override
    public boolean findPath() {
        System.out.println("maxvalue: " + Float.MAX_VALUE);
        System.out.println("start finding");
        System.out.println("start");
        start.myString();
        System.out.println("finish");
        finish.myString();

        start.setStartDistance(0);
        start.countFinishDistance(finish);
        start.setPreviousBlock(null);
        for (Block block : blocks) {
            unvisited.add(block);
        }
        //unvisited.addAll(blocks);

        Block current = new Block();
        current = start;
        while (!finishFound) {
            current = unvisited.poll();
            System.out.println("porovnání: cur distance" + current.getStartDistance() + "Float.MAX_VALUE - 3:" + (Integer.MAX_VALUE));
            if (current.getStartDistance() >= (Integer.MAX_VALUE)) {
                System.out.println("asi jsme mimo");
                return false;
            }
            //   System.out.println("current: " + current.getStartDistance());
            if (current.equals(finish)) {
//                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: "+ finish.getPreviousBlock().toString());
                finishFound = true;
                //         System.out.println("finnish found");
            } else {
                for (Edge edge : current.getEdges()) {
                    if (unvisited.contains(edge.getBlockTo())) {

                        edge.getBlockTo().countFinishDistance(finish);
                        if (edge.getBlockTo().getStartDistance() > (current.getStartDistance() + edge.getWeight())) {
                            //       System.out.println("reseting previous");

                            edge.getBlockTo().setPreviousBlock(current);
//                            if(edge.getBlockTo().equals(finish)){System.out.println("edge.getBldsfsdfsdf: "+  edge.getBlockTo().getPreviousBlock().toString());
//                                System.out.println("ffffffffffffuckkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");}
                            edge.getBlockTo().setStartDistance(current.getStartDistance() + edge.getWeight());
                            //   System.out.println("current."+ current.getPosition()+ );
                            unvisited.remove(edge.getBlockTo());
                            unvisited.add(edge.getBlockTo());
                            System.out.println("block to: " + edge.getBlockTo().getStartDistance());
                        } else {
                            System.out.println("elseeeeeeeeeeeeeeeeeeeeee> current: " + current.getStartDistance() + " weight: " + edge.getWeight() + "> " + edge.getBlockTo().getStartDistance());
                        }
                        //}
                    }
                }
            }
            if (unvisited.size() == 0) {
                System.out.println("nulaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            }
        }
        if (finish.getStartDistance() > Integer.MAX_VALUE - 3) {
            System.out.println("returnig false");
            return false;
        } else {
            System.out.println("finish real distance: " + finish.getStartDistance());
            return true;
        }
    }

    @Override
    public void randomlySetStartAndFinish() {
        boolean stop = false;
        for (int y = 0; y < cubesArray[1].length; y++) {
            for (int x = 0; x < cubesArray.length; x++) {
                for (int z = 0; z < cubesArray[1][1].length; z++) {
                    if (cubesExistance[x][y][z] == true) {
                        start = cubesArray[x][y][z].getCorrespondingBlock();
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            }
        }

        stop = false;
        for (int y = cubesArray[1].length - 7; y >= 0; y--) {
            for (int x = cubesArray.length - 1; x >= 0; x--) {
                for (int z = cubesArray[1][1].length - 1; z >= 0; z--) {
                    if (cubesExistance[x][y][z] == true) {
                        finish = cubesArray[x][y][z].getCorrespondingBlock();
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        System.out.println("start: " + start.getCenter().getCoords().toString());
        System.out.println("finish: " + finish.getCenter().getCoords().toString());
    }

//    public ArrayList<Point> getLineGraph() {
//        ArrayList<Point> path = new ArrayList<>();
//        System.out.println("finish: "+ finish.getCenter().getCoords()+ " prev: "+ finish.getPreviousBlock());
//        Block b = finish;
//
//        while (!b.equals(start)) {
//            path.add(b.getCenter());
//            b = b.getPreviousBlock();
//        }
//        path.add(start.getCenter());
//        return path;
//    }
    public ArrayList<Point> getLineGraph() {
        ArrayList<Point> path = new ArrayList<>();
        Block b = finish;
        path.add(b.getCenter());
        b=b.getPreviousBlock();
        while (!b.equals(start)) {
            path.add(b.getCenter());
            path.add(b.getCenter());
            b = b.getPreviousBlock();
        }
        path.add(start.getCenter());

        return path;
    }

}
