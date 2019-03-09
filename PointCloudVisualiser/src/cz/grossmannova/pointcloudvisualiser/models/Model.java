/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;

//    ArrayList<Vector3f> triangles = new ArrayList<Vector3f>();
//    ArrayList<ArrayList<ArrayList<Point>>> pointsAccordingToCountoursForEachLevel = new ArrayList<>(); //seznam seznamu seznamĹŻ bodĹŻ, kterĂ© reprezentujĂ­ nÄ›kolik kontur pro danou hodnotu y
//    public List<Point> pointsListCubesCenters = new ArrayList<Point>();
//    public List<Point> pointsListIntersects = new ArrayList<Point>();
/**
 *
 * @author Pavla
 */
public abstract class Model {

    public List<Point> pointsList;
    public List<Block> blocksList;

    public Model(List<Point> pointsList) {
        this.pointsList = pointsList;
    }
    
    public Model(List<Block> blocksList, int a) { //a je tam jen aby se odlišily konstruktory
        this.blocksList = blocksList;
    }

    public abstract void draw();

}
