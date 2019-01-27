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
        normalise();
        scale();
        round();
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
        int scale = 40;
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
        System.out.println("rounded amount of points:"+ pointsListRounded.size());
        System.out.println("previous amount of points:"+ pointsList.size());
    }

}
