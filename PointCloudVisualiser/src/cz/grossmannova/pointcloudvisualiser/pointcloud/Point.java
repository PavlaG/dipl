/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pointcloud;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public class Point {

    private Vector3f coords = new Vector3f();

    public Point() {
    }

    public Point(String input) {
        String[] split = input.split(";");
        coords.set(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
this.toString();
    }

    @Override
    public String toString() {
        return "Point{" + "coords=" + coords.toString()+ '}';
    }

    public Vector3f getCoords() {
        return coords;
    }

  
    

}
