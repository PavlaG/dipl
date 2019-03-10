/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;

/**
 *
 * @author Pavla
 */
public abstract class Model {

    public List<Point> pointsList;
    public List<Block> blocksList;
    public Point point;

    public Model(Point point) {
        this.point = point;
    }

    public Model(List<Point> pointsList) {
        this.pointsList = pointsList;
    }

    public Model(List<Block> blocksList, int a) { //a je tam jen aby se odlišily konstruktory
        this.blocksList = blocksList;
    }

    public abstract void draw();

}
