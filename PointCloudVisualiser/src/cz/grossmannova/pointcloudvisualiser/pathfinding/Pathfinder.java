/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.List;

/**
 *
 * @author Pavla
 */
public abstract class Pathfinder {
    public List<Point> path;
    
     public abstract void findPath();
    
}
