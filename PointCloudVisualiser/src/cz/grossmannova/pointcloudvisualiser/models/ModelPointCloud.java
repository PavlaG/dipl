/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;


public class ModelPointCloud extends Model {

    public ModelPointCloud(List<Point> pointsList) {
        this.pointsList = pointsList;
    }
    
}
