/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;

/**
 *
 * @author Pavla
 */
public abstract class Pathfinder {

    protected List<Point> path;
    protected Graph graph;
    protected List<Block> blocks;
    protected Point start;
    protected Point finish;
    protected boolean[][][] cubesExistance;
    protected Point[][][] cubesArray;

    public Pathfinder(Graph graph) {
        this.graph = graph;
        this.blocks = graph.getBlocks();
        start = new Point();
        finish = new Point();
        cubesExistance = graph.getCubesExistance();
        cubesArray = graph.getCubesArray();
    }

    public abstract void findPath();

    public abstract void randomlySetStartAndFinish();

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }
    
    

}
