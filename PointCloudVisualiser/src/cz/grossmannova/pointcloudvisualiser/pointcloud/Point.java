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
    public Point(Vector3f coords) {
        this.coords=coords;
    }

    public Point(String input) {
        String[] split = input.split(";");
        coords.set(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
       //coords.scale(1000); //kvůli souboru bunny.txt
//this.toString();
    }
    public Point(float x, float y, float z){
        coords.set(x,y,z);
       
    }

    @Override
    public String toString() {
        return "Point{" + "coords=" + coords.toString()+ '}';
    }

    public Vector3f getCoords() {
        return coords;
    }

  public Vector3f getRoundedCoords(){
      return new Vector3f(Math.round(coords.getX()), Math.round(coords.getY()), Math.round(coords.getZ()));
  }
    

}
