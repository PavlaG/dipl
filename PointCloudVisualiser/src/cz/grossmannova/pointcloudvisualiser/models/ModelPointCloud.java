/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.MarchingCubes;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public class ModelPointCloud extends Model {

    public ModelPointCloud(List<Point> pointsList) {
        this.pointsList = pointsList;
        normalise();
        scale();
        round();
        createPointListForTriangles();
    }

    private void normalise() {
        maxX = minX = pointsList.get(0).getCoords().getX();
        maxY = minY = pointsList.get(0).getCoords().getY();
        maxZ = minZ = pointsList.get(0).getCoords().getZ();

        for (Point point : pointsList) {
            if (point.getCoords().getX() < minX) {
                minX = point.getCoords().getX();
            }
            if (point.getCoords().getY() < minY) {
                minY = point.getCoords().getY();
            }
            if (point.getCoords().getZ() < minZ) {
                minZ = point.getCoords().getZ();
            }

            if (point.getCoords().getX() > maxX) {
                maxX = point.getCoords().getX();
            }
            if (point.getCoords().getY() > maxY) {
                maxY = point.getCoords().getY();
            }
            if (point.getCoords().getZ() > maxZ) {
                maxZ = point.getCoords().getZ();
            }

        }
        float xDiff = maxX - minX;
        float yDiff = maxY - minY;
        float zDiff = maxZ - minZ;
        for (Point point : pointsList) {
            float newX = (point.getCoords().getX() - minX) / xDiff;
            float newY = (point.getCoords().getY() - minY) / yDiff;
            float newZ = (point.getCoords().getZ() - minZ) / zDiff;
            pointsListNormalised.add(new Point(newX, newY, newZ));
        }

        System.out.println("minX=" + minX + " maxX=" + maxX + " minY=" + minY + " maxY=" + maxY + " minZ=" + minZ + " maxZ=" + maxZ);
    }

    private void scale() {
        //pak se bude zadávat uživatelem, teď natvrdo:
        int scale = 20;
        for (Point point : pointsListNormalised) {
            pointsListScaled.add(new Point(
                    point.getCoords().getX() * scale,
                    point.getCoords().getY() * scale,
                    point.getCoords().getZ() * scale)
            );
        }
    }

    private void round() {
        boolean containsFlag = false;
        // Point p=new Point();
        for (Point point : pointsListScaled) {
            for (Point pointRound : pointsListRounded) {
                if (pointRound.getCoords().equals(point.getRoundedCoords())) {
                    //System.out.println("aaa");
                    containsFlag = true;
                    break;
                };
            }
            if (containsFlag == false) {
                pointsListRounded.add(new Point(point.getRoundedCoords()));
            } else {
                containsFlag = false;
            }
        }
        System.out.println("rounded amount of points:" + pointsListRounded.size());
        System.out.println("previous amount of points:" + pointsList.size());
    }

    private Vector3f moveCornerOfObjectToCoords000() {
        maxX=minX = pointsListRounded.get(0).getCoords().getX();
        maxY=minY = pointsListRounded.get(0).getCoords().getY();
       maxZ=minZ = pointsListRounded.get(0).getCoords().getZ();
        for (Point point : pointsListRounded) {
            if (point.getCoords().getX() < minX) {
                minX = point.getCoords().getX();
            }
            if (point.getCoords().getY() < minY) {
                minY = point.getCoords().getY();
            }
            if (point.getCoords().getZ() < minZ) {
                minZ = point.getCoords().getZ();
            }
                  if (point.getCoords().getX() > maxX) {
                maxX = point.getCoords().getX();
            }
            if (point.getCoords().getY() > maxY) {
                maxY = point.getCoords().getY();
            }
            if (point.getCoords().getZ() > maxZ) {
                maxZ = point.getCoords().getZ();
            }

        }
       // System.out.println(" v2: minX=" + minX + " maxX=" + maxX + " minY=" + minY + " maxY=" + maxY + " minZ=" + minZ + " maxZ=" + maxZ);
  
        for (Point point : pointsListRounded) {
            point.getCoords().set(point.getCoords().getX() - minX, point.getCoords().getY() - minY, point.getCoords().getZ() - minZ);
        }
        return new Vector3f(Math.abs(minX-maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
    }

    private void createPointListForTriangles() { //vezme zaokrouhlené body a v listu triangles z nich udělá seznam tak, jak jdou po sobě pro vykreslení

      //  ArrayList<Vector3f> triangles = new ArrayList<Vector3f>();
        Vector3f objectDimensions=moveCornerOfObjectToCoords000(); //roh obalu objektu se posune do souřadnic 0,0,0
        //int xSize = 3, zSize = 2, ySize = 2; //tohle se zjistí z pointListu, který se nejpreve přesune rohem do 000      
        Point[][][] pointsGrid = new Point[(int)objectDimensions.getZ()][(int)objectDimensions.getY()][(int)objectDimensions.getX()];
        for (int z = 0; z < (int)objectDimensions.getZ(); z++) {
            for (int y = 0; y < (int)objectDimensions.getY(); y++) {
                for (int x = 0; x < (int)objectDimensions.getX(); x++) {
                    for (Point point : pointsListRounded) {
                        if (point.getCoords().getX() == x && point.getCoords().getY() == y && point.getCoords().z == z) {
                            pointsGrid[z][y][x] = point;
                            break;
                        }
                        pointsGrid[z][y][x] = new Point(x, y, z);
                        pointsGrid[z][y][x].setExists(false);
                    }
                }
            }
        }
        for (int z = 0; z < (int)objectDimensions.getZ() - 1; z++) {
            for (int y = 0; y < (int)objectDimensions.getY() - 1; y++) {
                for (int x = 0; x < (int)objectDimensions.getX() - 1; x++) {
                    MarchingCubes.polygonise(pointsGrid, x, y, z, triangles);
                }
            }
        }
//        System.out.println("size: "+triangles.size());
//        for (Vector3f triangle : triangles) {
//            System.out.println(triangle.getX());
//        }
    }

}
