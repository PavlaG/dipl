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
    protected boolean[][][] cubesExistance;
    protected Point[][][] cubesArray;
    protected int steps = 0;

    public Pathfinder(Graph graph) {
        this.graph = graph;
        this.blocks = graph.getBlocks();
        start = new Block();
        finish = new Block();
        cubesExistance = graph.getCubesExistance();
        cubesArray = graph.getCubesArray();
    }

    public abstract boolean findPath();

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
            y = r.nextInt(cubesArray[1].length - 1);
            x = r.nextInt(cubesArray.length - 1);
            z = r.nextInt(cubesArray[1][1].length - 1);
            if (cubesExistance[x][y][z] == true && !cubesArray[x][y][z].getCorrespondingBlock().equals(start)) {
                finish = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;
            }
        }
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
            y = r.nextInt(cubesArray[1].length - 1);
            x = r.nextInt(cubesArray.length - 1);
            z = r.nextInt(cubesArray[1][1].length - 1);
            if (cubesExistance[x][y][z] == true) {
                finish = cubesArray[x][y][z].getCorrespondingBlock();
                stop = true;
            }
        }
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

    public void printSteps() { //pak se může odstranit
        System.out.println("steps: " + steps);
    }

    public void setStartAndFinishBlocks(Point startP, Point finishP) {
        start = startP.getCorrespondingBlock();
        finish = finishP.getCorrespondingBlock();
    }

}
