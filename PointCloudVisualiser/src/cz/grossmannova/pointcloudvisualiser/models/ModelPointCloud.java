package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class ModelPointCloud extends Model {

    private List<Point> pointsListNormalised = new ArrayList<>();
    private List<Point> pointsListScaled = new ArrayList<>();
    private List<Point> pointsListRounded = new ArrayList<>();

    private float minX, minY, minZ, maxX, maxY, maxZ; //není stále aktuální, dobře je po zavolání fce moveCornerOfObjectToCoords000()

    private int listId = -1;

    public ModelPointCloud(List<Point> pointsList) {
        super(pointsList);
        normalise();
        scale();
        //round(); //tahle metoda zaokrouhlí souřadnice a posune body do mřížky, prozatím ji nahradím následujícím řádkem
        pointsListRounded = pointsListScaled;
        moveCornerOfObjectToCoords000(); //posune roh obalu objektu do 000
        //createPointListForTriangles(); //této metodě musí těsně předcházet moveCornerOfObjectToCoords000(), protože v ní se spočte min, max pro x,y,z, s těmito hodnotami se zde pak počítá
        //createSetOfContoursNew();

        //fillModelWithCubes();
        //findDuplicatedPoints();
        // testPrintIntersectsIndexes();
        super.pointsList = pointsListRounded;
    }

    @Override
    public void draw() {
        GL11.glPointSize(4);
        GL11.glColor3f(0.85f, 0.0f, 0.0f);
        GL20.glUseProgram(0);
        GL11.glDisable(GL11.GL_LIGHTING);
        if (listId == -1) {
            listId = GL11.glGenLists(1);
            GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
            GL11.glBegin(GL11.GL_POINTS);
            for (Point point : pointsList) {
                GL11.glVertex3f(point.getCoords().getX(), point.getCoords().getY(), point.getCoords().getZ());
            }
            GL11.glEnd();
            GL11.glEndList();
        } else {
            GL11.glCallList(listId);
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
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
        int scale = 80;
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

//    private void createPointListForTriangles() { //vezme zaokrouhlené body a v listu triangles z nich udělá seznam tak, jak jdou po sobě pro vykreslení
//        objectDimensions = new Vector3f(Math.abs(minX - maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
//        Point[][][] pointsGrid = transformPointsToPoinsGrid();
//        for (int z = 0; z < (int) objectDimensions.getZ() - 1; z++) {
//            for (int y = 0; y < (int) objectDimensions.getY() - 1; y++) {
//                for (int x = 0; x < (int) objectDimensions.getX() - 1; x++) {
//                    MarchingCubes.polygonise(pointsGrid, x, y, z, triangles);
//                }
//            }
//        }
////        System.out.println("size: "+triangles.size());
////        for (Vector3f triangle : triangles) {
////            System.out.println(triangle.getX());
////        }
//    }
//    private Point[][][] transformPointsToPoinsGrid() {
//        objectDimensions = new Vector3f(Math.abs(minX - maxX), Math.abs(maxY - minY), Math.abs(maxZ - minZ));
//        //int xSize = 3, zSize = 2, ySize = 2; //tohle se zjistí z pointListu, který se nejpreve přesune rohem do 000      
//        Point[][][] pointsGrid = new Point[(int) objectDimensions.getZ()][(int) objectDimensions.getY()][(int) objectDimensions.getX()];
//        for (int z = 0; z < (int) objectDimensions.getZ(); z++) {
//            for (int y = 0; y < (int) objectDimensions.getY(); y++) {
//                for (int x = 0; x < (int) objectDimensions.getX(); x++) {
//                    for (Point point : pointsListRounded) {
//                        if (point.getCoords().getX() == x && point.getCoords().getY() == y && point.getCoords().z == z) {
//                            pointsGrid[z][y][x] = point;
//                            break;
//                        }
//                        pointsGrid[z][y][x] = new Point(x, y, z);
//                        pointsGrid[z][y][x].setExists(false);
//                    }
//                }
//            }
//        }
//        return pointsGrid;
//    }
//
//    private float countDistanceBetween2Points(Point p1, Point p2) {
//        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getY() - p2.getCoords().getY(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
//    }
    private float countDistanceBetween2PointsIn2D(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
    }

//    private void createSetOfContoursNew() {
//        Point currentPoint;
//        float distanceFromCurrentPoint;
//        int currentIndexForContourInSpecifiedLevel = -1;
//        float minDistanceFromCurrentPoint = Float.MAX_VALUE;
//        Point nearestPointToCurrentPoint = new Point();
//        boolean nearestPointSet = false;
//        boolean doNextRound = true;
//        float distanceLimit = 18; //tohle se pak bude někde uživatelsky nastavovat?
//        ArrayList<ArrayList<Point>> pointsAccordintToLevels = new ArrayList<>(); //vytvoření hlavního seznamu, který bude obsahovat seznamy bodů pro danou hodnotu y
//        Point firstPointInContour;
//        for (int y = 0; y <= maxY; y++) {
//            pointsAccordintToLevels.add(new ArrayList<>()); //
//            pointsAccordingToCountoursForEachLevel.add(new ArrayList<ArrayList<Point>>());
//        }
//        for (Point point : pointsListRounded) { //rozdělím body do seznamů podle hodnoty y
//            pointsAccordintToLevels.get((int) point.getCoords().getY()).add(point);
//        }
//
//        for (ArrayList<Point> listForLevel : pointsAccordintToLevels) { //prvotní cyklus přes všechny levely
//            if (listForLevel.isEmpty()) { //ověřit, že to pak nidke nespadne, když level opravdu nebude nic obsahovat!!!
//                System.out.println("level neobsahuje žádné body");
//                continue;
//            }
////            if (pointsAccordintToLevels.indexOf(listForLevel) != 5) {
////                continue; //podmínka pro testování
////            }
//            currentIndexForContourInSpecifiedLevel = -1;
//            while ((currentPoint = thereIsStillUnvisitedPoint(listForLevel)) != null) {
//                pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).add(new ArrayList<Point>());
//                currentIndexForContourInSpecifiedLevel++;
//                currentPoint.setVisited(true);
//                pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(currentPoint));
//                doNextRound = true;
//                int i = 0;
//                while (doNextRound) {
//                    minDistanceFromCurrentPoint = Float.MAX_VALUE;
//                    for (Point point : listForLevel) {
//                        if (i > 2) {
//                            if (!point.equals(currentPoint) && (point.isVisited() == false || point.equals(pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0)))) {
//                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
//                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint //&& distanceFromCurrentPoint < distanceLimit
//                                        ) {
//                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
//                                    nearestPointToCurrentPoint = point;
//                                    nearestPointSet = true;
//                                }
//                            }
//                        } else if (i <= 2) {
//                            if (!point.equals(currentPoint) && point.isVisited() == false) {
//                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
//                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint //&& distanceFromCurrentPoint < distanceLimit
//                                        ) {
//                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
//                                    nearestPointToCurrentPoint = point;
//                                    nearestPointSet = true;
//                                }
//                            }
//                        }
//
//                    }
//                    if (nearestPointSet) {
//                        i++;
//                        if (nearestPointToCurrentPoint.equals(pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0))) {
//                            doNextRound = false;
//                            nearestPointSet = false;
//                            //System.out.println("nearest is first");
//                            pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));
//
//                            nearestPointToCurrentPoint = new Point();
//                        } else {
//                            nearestPointToCurrentPoint.setVisited(true);
//                            pointsAccordingToCountoursForEachLevel.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));
////i++;
//                            nearestPointSet = false;
//                            doNextRound = true;
//                            currentPoint = nearestPointToCurrentPoint;
//                        }
//                        //currentPoint = nearestPointToCurrentPoint;
//                    } else {
//                        doNextRound = false;
//                        nearestPointSet = false;
//                        //System.out.println("set doNextRound to false");
//                        nearestPointToCurrentPoint = new Point();
//                    }
//                }
//
//            }
//
//        }
//        closeUnclosedContours();
////        for (ArrayList<Point> listForLevel : pointsAccordintToLevels) {
////            System.out.println("new level Y: ");
////            for (Point point : listForLevel) {
////                System.out.println("point: " + point.getCoords().getX() + " y: " + point.getCoords().getY());
////            }
////        }
////        for (ArrayList<ArrayList<Point>> arrayList : pointsAccordingToCountoursForEachLevel) {
////            System.out.println("level");
////            for (ArrayList<Point> contour : arrayList) {
////                System.out.println("contour:");
////                for (Point point : contour) {
////                    System.out.println("point: " + point.getCoords().getX() + " y: " + point.getCoords().getY() + " z: " + point.getCoords().getZ());
////
////                }
////
////            }
////        }
//    }
//    private Point thereIsStillUnvisitedPoint(ArrayList<Point> listForLevel) {
//        for (Point point : listForLevel) {
//            if (point.isVisited() == false) {
//                return point;
//            }
//        }
//        return null;
//    }
//
//    private void closeUnclosedContours() {
//        for (ArrayList<ArrayList<Point>> arrayList : pointsAccordingToCountoursForEachLevel) {
//            for (ArrayList<Point> contour : arrayList) {
//                if (!contour.get(0).equals(contour.get(contour.size() - 1))) {
//                    contour.add(contour.get(0));
//                    System.out.println("finishing contour");
//                }
//
//            }
//        }
//    }
    private float selectMax(float xDiff, float yDiff, float zDiff) {
        return Math.max(Math.max(xDiff, yDiff), zDiff);
    }

//    private boolean findIfCenterOfCubeIsInsideContour(int level, int contourIndex, Point centerOfCube) {
//        int rayIntersectsWithLine = 0;
//        int leftIntersects = 0;
//        int rightIntersects = 0;
//        // for (ArrayList<Point> contour : pointsAccordingToCountoursForEachLevel.get(level)) {
//        rayIntersectsWithLine = 0;
//        leftIntersects = 0;
//        rightIntersects = 0;
//        for (Point point : pointsAccordingToCountoursForEachLevel.get(level).get(contourIndex)) {
//            if (pointsAccordingToCountoursForEachLevel.get(level).get(contourIndex).indexOf(point) + 1 == pointsAccordingToCountoursForEachLevel.get(level).get(contourIndex).size()) {
//                break; //aby na konci kontury nehledal další bod, který tam není
//            }
//            rayIntersectsWithLine = rayIntersectsWithLine(point, level, pointsAccordingToCountoursForEachLevel.get(level).get(contourIndex).get(pointsAccordingToCountoursForEachLevel.get(level).get(contourIndex).indexOf(point) + 1), centerOfCube);
//            if (rayIntersectsWithLine == 1) {
//                leftIntersects++;
//            } else if (rayIntersectsWithLine == 2) {
//                rightIntersects++;
//            }
//        }
//        if ((leftIntersects % 2) != (rightIntersects % 2)) {
//            System.out.println("něco je špatně L:" + leftIntersects + " R:" + rightIntersects);
//            //return false;
//        }
//        if (leftIntersects % 2 == 0) {
//            return false; //bod je mimo
//        } else {
//            return true;//bod je uvnitř
//        }
//        // }
//        //return false;
//    }
//
//    private int rayIntersectsWithLine(Point p1, int level, Point p2, Point centerOfCube) { //ret: 0- není průnik, 1-průnik nalevo od bodu, 2-průnik napravo od bodu
//        if ((p1.getCoords().getZ() <= centerOfCube.getCoords().getZ() && p2.getCoords().getZ() >= centerOfCube.getCoords().getZ())
//                || (p1.getCoords().getZ() >= centerOfCube.getCoords().getZ() && p2.getCoords().getZ() <= centerOfCube.getCoords().getZ())) {
//            if (p1.getCoords().getZ() == p2.getCoords().getZ()) {
//                return 0;
//            } else {
//                float intersectCoordX = p1.getCoords().getX() + (p2.getCoords().getX() - p1.getCoords().getX()) * (((centerOfCube.getCoords().getZ() - p1.getCoords().getZ()) / (p2.getCoords().getZ() - p1.getCoords().getZ())));
//                pointsListIntersects.add(new Point(intersectCoordX, level, centerOfCube.getCoords().getZ())); //pak se tohohle zbavit!!!!
//                //Point intersect= new Point(x,0,centerOfCube.getCoords().getZ()); //pouze s skutečnými souřadnicemi z,x
//                // if(intersectCoordX == centerOfCube.getCoords().getX())System.out.println("rovnají se");
//                if (intersectCoordX == p1.getCoords().getX() || intersectCoordX == p2.getCoords().getX()) {
//                    System.out.println("noooooo");
//                }
//                if (centerOfCube.getCoords().getZ() == p1.getCoords().getZ() || centerOfCube.getCoords().getZ() == p2.getCoords().getZ()) {
//                    System.out.println("noooooo2222");
//                }
//                if (intersectCoordX == centerOfCube.getCoords().getX()) {
//                    System.out.println("nooooo333");
//                }
//                if (p2.getCoords().getX() == p1.getCoords().getX()) {
//                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                }
//                if (intersectCoordX < centerOfCube.getCoords().getX()) {
//                    return 1;
//                } else {
//                    return 2;
//                }
//            }
//        } else {
//            return 0;
//        }
//    }
//    private void fillModelWithCubes() {
//        for (int y = 0; y < pointsAccordingToCountoursForEachLevel.size(); y++) {
//            ArrayList<ArrayList<Point>> level = pointsAccordingToCountoursForEachLevel.get(y);
//            for (int z = 0; z < maxZ + 1; z++) {
//                ArrayList<Point> intersections = getIntersections(y, z, level);
//                //System.out.println("y:" + y + " " + "z:" + z + " " + intersections.size());
//                if (intersections.size() % 2 == 1) {
//                    System.err.println("Lichý počet průsečíků:" + y);
//                }
//                for (int i = 0; i < intersections.size() / 2; i++) {
//                    float from = Math.min(intersections.get(i * 2).getCoords().getX(), intersections.get(i * 2 + 1).getCoords().getX());
//                    float to = Math.max(intersections.get(i * 2).getCoords().getX(), intersections.get(i * 2 + 1).getCoords().getX());
//
//                    for (int x = ((int) from) + 1; x < to; x++) {
//                        pointsListCubesCenters.add(new Point(x, y, z));
//                    }
//                }
//            }
//        }
//        /*
//        for (ArrayList<ArrayList<Point>> listOfContours : pointsAccordingToCountoursForEachLevel) {
//            for (ArrayList<Point> contour : listOfContours) {
//                int y = pointsAccordingToCountoursForEachLevel.indexOf(listOfContours);
////                if (y == -1) {
////                    continue;
////                }
//                if (contour.size() == 3) {
//                    continue; // je to vlastně čára tam a zpět
//                }
//                if (listOfContours.indexOf(contour) != 1) {
//                    continue; //pak dstranit, je to jen pro test
//                }
//                System.out.println("xmin:" + minX + " ymin" + minY + " minZ" + minZ + " ksldfjlsdkjflskdjf");
//                for (float z = 0; z <= maxZ; z += 1) {
//                    for (float x = 0; x <= maxX; x += 1) {
//                        int a = listOfContours.indexOf(contour);
//                        if (findIfCenterOfCubeIsInsideContour(y, a, new Point(x, y, z))) {
//                            pointsListCubesCenters.add(new Point(x, y, z));
//                        }
//                    }
//                }
//
//            }
//        }
//         */
//    }
//    private void findDuplicatedPoints() {
//        int sum = 0;
//        for (Point point : pointsAccordingToCountoursForEachLevel.get(25).get(1)) {
//            sum = 0;
//            for (Point p : pointsAccordingToCountoursForEachLevel.get(25).get(1)) {
//                if (point.getCoords().equals(p.getCoords())) {
//                    sum++;
//                }
//            }
//            System.out.println("id: " + pointsAccordingToCountoursForEachLevel.get(25).get(1).indexOf(point) + " sum: " + sum);
//        }
//
//    }
//    private void testPrintIntersectsIndexes() {
//        for (Point point : pointsListIntersects) {
//            System.out.println("index: " + pointsListIntersects.indexOf(point));
//            System.out.println("       x: " + point.getCoords().getX() + " y: " + point.getCoords().getY() + " z: " + point.getCoords().getZ());
//
//        }
//    }
//    private ArrayList<Point> getIntersections(int y, int z, ArrayList<ArrayList<Point>> level) {
//        ArrayList<Point> intersections = new ArrayList<>();
//        for (ArrayList<Point> contour : level) {
//            for (int i = 0; i < contour.size() - 1; i++) {
//                Point p1 = contour.get(i);
//                Point p2 = contour.get(i + 1);
//                if (!isBetween(p1.getCoords().getZ(), p2.getCoords().getZ(), z)) {
//                    continue;
//                }
//                float x = p1.getCoords().getX() + (p2.getCoords().getX() - p1.getCoords().getX()) * ((z - p1.getCoords().getZ()) / (p2.getCoords().getZ() - p1.getCoords().getZ()));
//                intersections.add(new Point(x, y, z));
//            }
//        }
//        return intersections;
//    }
//
//    private boolean isBetween(float a, float b, int c) {
//        return (a <= c && b >= c) || (a >= c && b <= c);
//    }
    public float getMaxY() {
        return maxY;
    }

    public float getMaxZ() {
        return maxZ;
    }

}
