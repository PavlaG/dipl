/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pavla
 */
public abstract class Pathfinder {

    protected List<Point> path;
    protected Graph graph;
    protected List<Block> blocks;
    protected Block start;
    protected Block finish;
    protected  boolean[][][] cubesExistance;
    protected  Point[][][] cubesArray;
    protected int steps = 0;

//    public static Point generateStartPoint(int seed) {
//        boolean stop = false;
//        int x, y, z;
//        Random r = new Random(seed);
//        while (stop == false) {
//            y = r.nextInt(cubesArray[1].length);
//            x = r.nextInt(cubesArray.length);
//            z = r.nextInt(cubesArray[1][1].length);
//            if (cubesExistance[x][y][z] == true) {
//                stop = true;
//                System.out.println("nalezeny start: "+ cubesArray[x][y][z].getCoords().toString());
//                return cubesArray[x][y][z];
//
//            }
//        }
//        return null;
//    }
//
//    public static Point generateFinishPoint(int seed) {
//        boolean stop = false;
//        int x, y, z;
//        Random r = new Random(seed);
//        while (stop == false) {
//            y = r.nextInt(cubesArray[1].length - 1);
//            x = r.nextInt(cubesArray.length - 1);
//            z = r.nextInt(cubesArray[1][1].length - 1);
//            if (cubesExistance[x][y][z] == true) {
//                stop = false;
//                 System.out.println("nalezeny konec: "+ cubesArray[x][y][z].getCoords().toString());
//                return cubesArray[x][y][z];
//            }
//        }
//        return null;
//    }

    public Pathfinder(Graph graph) {
        this.graph = graph;
        this.blocks = graph.getBlocks();
        start = new Block();
        finish = new Block();
        cubesExistance = graph.getCubesExistance();
        cubesArray = graph.getCubesArray();
    }

    public abstract boolean findPath();

//    public void randomlySetStartAndFinish() {
//        System.out.println("aaaaaaaaaaaaaaaaaa");
//        boolean stop = false;
//        for (int y = 0; y < cubesArray[1].length; y++) {
//            for (int x = 0; x < cubesArray.length; x++) {
//                for (int z = 0; z < cubesArray[1][1].length; z++) {
//                    if (cubesExistance[x][y][z] == true) {
//                        //start = cubesArray[x][y][z].getCorrespondingBlock();
//                        System.out.println("cubesExistance::::::::::::::::::::::"+x+ " "+y+" "+z);
//                        stop = true;
//                        break;
//                    }
//                }
//                if (stop) {
//                    break;
//                }
//            }
//            if (stop) {
//                break;
//            }
//        }
//        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//        stop = false;
//        for (int y = cubesArray[1].length - 1; y >= 0; y--) { //-1
//            for (int x = cubesArray.length - 1; x >= 0; x--) { //-1
//                for (int z = cubesArray[1][1].length - 1; z >= 0; z--) {
//                    if (cubesExistance[x][y][z] == true) {
//                        finish = cubesArray[x][y][z].getCorrespondingBlock();
//                        stop = true;
//                        break;
//                    }
//                }
//                if (stop) {
//                    break;
//                }
//            }
//            if (stop) {
//                break;
//            }
//        }
//        System.out.println("weeeeeeeeeeee");
//    }

    public void resetStartAndFinish() {
        boolean stop = false;
        int x, y, z;
        Random r = new Random();
        start = null;
        finish = null;
        
        while (stop == false) {
            y = r.nextInt(cubesArray[1].length);
            x = r.nextInt(cubesArray.length);
            z = r.nextInt(cubesArray[1][1].length);
            if (cubesExistance[x][y][z] == true) {
                start = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;

            }
        }
        stop = false;
        while (stop == false) {
            //Random r = new Random();
            y = r.nextInt(cubesArray[1].length - 1);
            x = r.nextInt(cubesArray.length - 1);
            z = r.nextInt(cubesArray[1][1].length - 1);
            if (cubesExistance[x][y][z] == true && !cubesArray[x][y][z].getCorrespondingBlock().equals(start)) {
                finish = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;

            }
        }
        //start=finish;
        //System.out.println("start: x:" + start.getCenter().getCoords().x + "y:" + start.getCenter().getCoords().y + "z:" + start.getCenter().getCoords().z);
        //System.out.println("finish: x:" + finish.getCenter().getCoords().x + "y:" + start.getCenter().getCoords().y + "z:" + start.getCenter().getCoords().z);
    }

     public void resetStartAndFinish2(int seed) {
        boolean stop = false;
        int x, y, z;
        Random r = new Random(seed);
        start = null;
        finish = null;
        while (stop == false) {
            y = r.nextInt(cubesArray[1].length);
            x = r.nextInt(cubesArray.length);
            z = r.nextInt(cubesArray[1][1].length);
            if (cubesExistance[x][y][z] == true) {
                start = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;

            }
        }
        stop = false;
        while (stop == false) {
            //Random r = new Random();
            y = r.nextInt(cubesArray[1].length - 1);
            x = r.nextInt(cubesArray.length - 1);
            z = r.nextInt(cubesArray[1][1].length - 1);
            if (cubesExistance[x][y][z] == true && !cubesArray[x][y][z].getCorrespondingBlock().equals(start)) {
                finish = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;

            }
        }
        //start=finish;
        Random rand= new Random();
//        if(rand.nextInt(2)==1){
//            System.out.println("vadne");
//        start=cubesArray[7][0][8].getCorrespondingBlock();
//        }
        //System.out.println("start: x:" + start.getCenter().getCoords().x + "y:" + start.getCenter().getCoords().y + "z:" + start.getCenter().getCoords().z);
        //System.out.println("finish: x:" + finish.getCenter().getCoords().x + "y:" + start.getCenter().getCoords().y + "z:" + start.getCenter().getCoords().z);
    }
    public Block getStart() {
        return start;
    }

    public void setStart(Block start) {
        this.start = start;
    }

    public Block getFinish() {
        return finish;
    }

    public void setFinish(Block finish) {
        this.finish = finish;
    }

    public abstract ArrayList<Point> getLineGraph();

    public abstract ArrayList<Block> getBlockGraph();

    public void printSteps() {
        System.out.println("steps: " + steps);
    }

    public void setStartAndFinishBlocks(Point startP, Point finishP) {
        start = startP.getCorrespondingBlock();
        finish = finishP.getCorrespondingBlock();
    }

}
