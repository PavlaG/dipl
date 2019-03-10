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

    public Pathfinder(Graph graph) {
        this.graph = graph;
        this.blocks = graph.getBlocks();
        start = new Block();
        finish = new Block();
        cubesExistance = graph.getCubesExistance();
        cubesArray = graph.getCubesArray();
    }

    public abstract boolean findPath();

    public abstract void randomlySetStartAndFinish();

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
    
    

}
