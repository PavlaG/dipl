/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pointcloud;

import java.util.Objects;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public class Point {

    private Vector3f coords = new Vector3f();
    private boolean exists = true;
    private boolean visited = false;

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public Point() {
    }

    public Point(Vector3f coords) {
        this.coords = coords;
    }

    public Point(Point p) {
        coords.set(p.getCoords());
        exists = p.isExists();
        visited = p.isVisited();
    }

    public Point(String input) {
        String[] split = input.split(";");
        coords.set(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
    }

    public Point(float x, float y, float z) {
        coords.set(x, y, z);

    }

    @Override
    public String toString() {
        return "Point{" + "coords=" + coords.toString() + '}';
    }

    public Vector3f getCoords() {
        return coords;
    }

    public Vector3f getRoundedCoords() {
        return new Vector3f(Math.round(coords.getX()), Math.round(coords.getY()), Math.round(coords.getZ()));
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.coords);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        return Objects.equals(this.coords, other.coords);
    }

}
