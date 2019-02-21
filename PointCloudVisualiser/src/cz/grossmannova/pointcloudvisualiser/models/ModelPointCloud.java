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
        //round(); //tahle metoda zaokrouhlí souřadnice a posune body do mřížky, prozatím ji nahradím následujícím řádkem
        pointsListRounded = pointsListScaled;
        moveCornerOfObjectToCoords000(); //posune roh obalu objektu do 000
        //createPointListForTriangles(); //této metodě musí těsně předcházet moveCornerOfObjectToCoords000(), protože v ní se spočte min, max pro x,y,z, s těmito hodnotami se zde pak počítá
        createSetOfContoursNew();
        fillModelWithCubes();
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
        float maxDiff = selectMax(xDiff, yDiff, zDiff);

        for (Point point : pointsList) {
            float newX = (point.getCoords().getX() - minX) / maxDiff;//xDiff;
            float newY = (point.getCoords().getY() - minY) / maxDiff;//yDiff;
            float newZ = (point.getCoords().getZ() - minZ) / maxDiff;//zDiff;
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
        System.out.println("rounded amount of points:" + pointsListRounded.size());
        System.out.println("previous amount of points:" + pointsList.size());
    }

    private Vector3f moveCornerOfObjectToCoords000() {
        maxX = minX = pointsListRounded.get(0).getCoords().getX();
        maxY = minY = pointsListRounded.get(0).getCoords().getY();
        maxZ = minZ = pointsListRounded.get(0).getCoords().getZ();
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
        maxX = maxX - minX; //tady nastavuju globální hodnoty pro max, min, abych to dál nemusela znovu počítat.
        maxY = maxY - minY; //nejdřív se musí nastavit max, než se vynuluje min
        maxZ = maxZ - minZ;
        minX = minZ = minY = 0;

        return new Vector3f(Math.abs(minX - maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
    }

    private void createPointListForTriangles() { //vezme zaokrouhlené body a v listu triangles z nich udělá seznam tak, jak jdou po sobě pro vykreslení
        objectDimensions = new Vector3f(Math.abs(minX - maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
        Point[][][] pointsGrid = transformPointsToPoinsGrid();
        for (int z = 0; z < (int) objectDimensions.getZ() - 1; z++) {
            for (int y = 0; y < (int) objectDimensions.getY() - 1; y++) {
                for (int x = 0; x < (int) objectDimensions.getX() - 1; x++) {
                    MarchingCubes.polygonise(pointsGrid, x, y, z, triangles);
                }
            }
        }
//        System.out.println("size: "+triangles.size());
//        for (Vector3f triangle : triangles) {
//            System.out.println(triangle.getX());
//        }
    }

    private Point[][][] transformPointsToPoinsGrid() {
        objectDimensions = new Vector3f(Math.abs(minX - maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
        //int xSize = 3, zSize = 2, ySize = 2; //tohle se zjistí z pointListu, který se nejpreve přesune rohem do 000      
        Point[][][] pointsGrid = new Point[(int) objectDimensions.getZ()][(int) objectDimensions.getY()][(int) objectDimensions.getX()];
        for (int z = 0; z < (int) objectDimensions.getZ(); z++) {
            for (int y = 0; y < (int) objectDimensions.getY(); y++) {
                for (int x = 0; x < (int) objectDimensions.getX(); x++) {
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
        return pointsGrid;
    }

    private float countDistanceBetween2Points(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getY() - p2.getCoords().getY(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
    }

    private float countDistanceBetween2PointsIn2D(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
    }

    private void createSetOfContoursNew() {
        Point currentPoint;
        float distanceFromCurrentPoint;
        int currentIndexForContourInSpecifiedLevel = -1;
        float minDistanceFromCurrentPoint = Float.MAX_VALUE;
        Point nearestPointToCurrentPoint = new Point();
        boolean nearestPointSet = false;
        boolean doNextRound = true;
        float distanceLimit = 18; //tohle se pak bude někde uživatelsky nastavovat?
        ArrayList<ArrayList<Point>> pointsAccordintToLevels = new ArrayList<>(); //vytvoření hlavního seznamu, který bude obsahovat seznamy bodů pro danou hodnotu y
        Point firstPointInContour;
        for (int y = 0; y <= maxY; y++) {
            pointsAccordintToLevels.add(new ArrayList<>()); //
            pointsAccordingToCountoursForEachLevel.add(new ArrayList<ArrayList<Point>>());
        }
        for (Point point : pointsListRounded) { //rozdělím body do seznamů podle hodnoty y
            pointsAccordintToLevels.get((int) point.getCoords().getY()).add(point);
        }

        for (ArrayList<Point> listForLevel : pointsAccordintToLevels) { //prvotní cyklus přes všechny levely
            if (listForLevel.isEmpty()) { //ověřit, že to pak nidke nespadne, když level opravdu nebude nic obsahovat!!!
                System.out.println("level neobsahuje žádné body");
                continue;
            }
            currentIndexForContourInSpecifiedLevel = -1;
            while ((currentPoint = thereIsStillUnvisitedPoint(listForLevel)) != null) {
                pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).add(new ArrayList<Point>());
                currentIndexForContourInSpecifiedLevel++;
                currentPoint.setVisited(true);
                pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(currentPoint));
                doNextRound = true;
                int i = 0;
                while (doNextRound) {
                    minDistanceFromCurrentPoint = Float.MAX_VALUE;
                    for (Point point : listForLevel) {
                        if (i > 1000) {
                            if (!point.equals(currentPoint) && (point.isVisited() == false || point.equals(pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0)))) {
                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint //&& distanceFromCurrentPoint < distanceLimit
                                        ) {
                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
                                    nearestPointToCurrentPoint = point;
                                    nearestPointSet = true;
                                }
                            }
                        } else if (i <= 1000) {
                            if (!point.equals(currentPoint) && point.isVisited() == false) {
                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint //&& distanceFromCurrentPoint < distanceLimit
                                        ) {
                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
                                    nearestPointToCurrentPoint = point;
                                    nearestPointSet = true;
                                }
                            }
                        }

                    }
                    if (nearestPointSet) {
                        i++;
                        if (nearestPointToCurrentPoint.equals(pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0))) {
                            doNextRound = false;
                            nearestPointSet = false;
                            //System.out.println("nearest is first");
                            pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));

                            nearestPointToCurrentPoint = new Point();
                        } else {
                            nearestPointToCurrentPoint.setVisited(true);
                            pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));
//i++;
                            nearestPointSet = false;
                            doNextRound = true;
                            currentPoint = nearestPointToCurrentPoint;
                        }
                        //currentPoint = nearestPointToCurrentPoint;
                    } else {
                        doNextRound = false;
                        nearestPointSet = false;
                        //System.out.println("set doNextRound to false");
                        nearestPointToCurrentPoint = new Point();
                    }
                }

            }

        }

//        for (ArrayList<Point> listForLevel : pointsAccordintToLevels) {
//            System.out.println("new level Y: ");
//            for (Point point : listForLevel) {
//                System.out.println("point: " + point.getCoords().getX() + " y: " + point.getCoords().getY());
//            }
//        }
        for (ArrayList<ArrayList<Point>> arrayList : pointsAccordingToCountoursForEachLevel) {
            System.out.println("level");
            for (ArrayList<Point> contour : arrayList) {
                System.out.println("contour:");
                for (Point point : contour) {
                    System.out.println("point: " + point.getCoords().getX() + " y: " + point.getCoords().getY());

                }

            }
        }
    }

    private Point thereIsStillUnvisitedPoint(ArrayList<Point> listForLevel) {
        // System.out.println("there isstill unvisited");
        for (Point point : listForLevel) {
            if (point.isVisited() == false) {
                return point;
            }
        }
        return null;
    }

    private float selectMax(float xDiff, float yDiff, float zDiff) {
        return Math.max(Math.max(xDiff, yDiff), zDiff);
    }

    private boolean findIfCenterOfCubeIsInsideContour(int level, Point centerOfCube) {

        int rayIntersectsWithLine = 0;
        int leftIntersects = 0;
        int rightIntersects = 0;
        for (ArrayList<Point> contour : pointsAccordingToCountoursForEachLevel.get(level)) {
            rayIntersectsWithLine = 0;
            leftIntersects = 0;
            rightIntersects = 0;
            for (Point point : contour) {
                if(contour.indexOf(point)+1==contour.size()) continue; //aby na konci kontury nehledal další bod, který tam není
                rayIntersectsWithLine = rayIntersectsWithLine(point, contour.get(contour.indexOf(point) + 1), centerOfCube);
                if (rayIntersectsWithLine == 1) {
                    leftIntersects++;
                } else if (rayIntersectsWithLine == 2) {
                    rightIntersects++;
                }
            }
            if (leftIntersects % 2 == 0) {
                return false; //bod je mimo
            } else {
                return true;//bod je uvnitř
            }
        }
        return false;
    }

    private int rayIntersectsWithLine(Point p1, Point p2, Point centerOfCube) { //ret: 0- není průnik, 1-průnik nalevo od bodu, 2-průnik napravo od bodu
        if ((p1.getCoords().getZ() <= centerOfCube.getCoords().getZ() && p2.getCoords().getZ() >= centerOfCube.getCoords().getZ())
                || (p1.getCoords().getZ() >= centerOfCube.getCoords().getZ() && p2.getCoords().getZ() <= centerOfCube.getCoords().getZ())) {
            if (p1.getCoords().getZ() == p2.getCoords().getZ()) {
                return 0;
            } else {
                float intersectCoordX = p1.getCoords().getX() + (p2.getCoords().getX() - p1.getCoords().getX()) * ((centerOfCube.getCoords().getZ() - p1.getCoords().getZ()) / (p2.getCoords().getZ() - p1.getCoords().getZ()));
                //Point intersect= new Point(x,0,centerOfCube.getCoords().getZ()); //pouze s skutečnými souřadnicemi z,x
                if (intersectCoordX < centerOfCube.getCoords().getX()) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {
            return 0;
        }
    }

    private void fillModelWithCubes() {
        
        for (ArrayList<ArrayList<Point>> listOfContours : pointsAccordingToCountoursForEachLevel) {
            for (ArrayList<Point> contour : listOfContours) {
                int y = pointsAccordingToCountoursForEachLevel.indexOf(listOfContours);
                if(y==-1) continue;
                for (int z = 0; z <= maxZ; z++) {
                    for (int x = 0; x <= maxX; x++) {
                        if(findIfCenterOfCubeIsInsideContour(y, new Point(x, y, z))){
                            System.out.println("adding");
                            pointsListCubesCenters.add(new Point(x,y,z));
                        }
                    }
                }

            }
        }

    }

}
