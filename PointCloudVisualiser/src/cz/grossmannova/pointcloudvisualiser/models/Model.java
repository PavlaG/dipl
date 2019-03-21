/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public abstract class Model {

    public List<Point> pointsList;
    public List<Block> blocksList;
    public ArrayList<ArrayList<ArrayList<Point>>> contours;
    public Point point;
    private boolean visible=false;
    public List<Vector3f> colors;

    public Model(Point point) {
        this.point = point;
    }

    public Model(List<Point> pointsList) {
        this.pointsList = pointsList;
    }
    public Model(List<Point> pointsList, List<Vector3f> colors) {
        this.pointsList = pointsList;
        this.colors=colors;
    }
    
    public Model(ArrayList<ArrayList<ArrayList<Point>>> contours){
        this.contours=contours;
    }

    public Model(List<Block> blocksList, int a) { //a je tam jen aby se odlišily konstruktory
        this.blocksList = blocksList;
    }

    public abstract void draw();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<Point> getPointsList() {
        return pointsList;
    }
    

}
