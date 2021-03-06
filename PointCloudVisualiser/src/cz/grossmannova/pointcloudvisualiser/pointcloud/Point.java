package cz.grossmannova.pointcloudvisualiser.pointcloud;

import cz.grossmannova.pointcloudvisualiser.models.Block;
import java.util.Objects;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public class Point {

    public static final Vector3f X_PLUS = new Vector3f(1, 0, 0);
    public static final Vector3f X_MINUS = new Vector3f(-1, 0, 0);
    public static final Vector3f Y_PLUS = new Vector3f(0, 1, 0);
    public static final Vector3f Y_MINUS = new Vector3f(0, -1, 0);
    public static final Vector3f Z_PLUS = new Vector3f(0, 0, 1);
    public static final Vector3f Z_MINUS = new Vector3f(0, 0, -1);

    private Vector3f coords = new Vector3f();
    private boolean exists = true;
    private boolean visited = false;
    private Block correspondingBlock = new Block();
    private Vector3f color = new Vector3f(1, 0, 0);

    public Point() {
    }

    public Point(Vector3f coords, Vector3f color) {
        this.coords = coords;
        if (color != null) {
            this.color = color;
        }
       // else this.color= new Vector3f(0,1,0);
    }

    public Point(Point p) {
        coords.set(p.getCoords());
        exists = p.isExists();
        visited = p.isVisited();
        color.set(p.getColor());
    }

    public Point(Point p, int a) {
        coords.set(p.getCoords());
        exists = p.isExists();
        visited = p.isVisited();
        correspondingBlock = p.getCorrespondingBlock();
         this.color.set(p.getColor());
    }
public Point(float x, float y, float z,Vector3f color) {
        coords.set(x, y, z);
        this.color.set(color);
    }

public Point(float x, float y, float z) {
        coords.set(x, y, z);
    
    }
    public Point(String input) {
        input = input.substring(2);
        String[] split = input.split(" ");
        coords.set(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
        if (split.length > 3) {
            //System.out.println("changing colors: " + Float.parseFloat(split[3]) + " " + Float.parseFloat(split[4]) + " " + Float.parseFloat(split[5]));
                 color.set(Float.parseFloat(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
            // System.out.println("colorsssssssssssss "+color.toString());
        } else {
          //  System.out.println("elseeee");
            color.set(0, 1, 0);
        }
    }

    

    public Block getCorrespondingBlock() {
        return correspondingBlock;
    }

    public void setCorrespondingBlock(Block correspondingBlock) {
        this.correspondingBlock = correspondingBlock;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    @Override
    public String toString() {
        return "Point{" + "coords=" + coords.toString() + '}';
    }

    public void colorString() {
        System.out.println("colors> " + color.toString());
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

    public void setCoords(Vector3f coords) {
        this.coords = coords;
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

    public Vector3f getColor() {
        //System.out.println("returning: " + color.toString());
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

}
