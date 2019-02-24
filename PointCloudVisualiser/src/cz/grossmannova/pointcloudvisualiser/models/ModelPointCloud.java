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
            float newX = (point.getCoords().getX() - minX) / maxDiff;
            float newY = (point.getCoords().getY() - minY) / maxDiff;
            float newZ = (point.getCoords().getZ() - minZ) / maxDiff;
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
        for (Point point : pointsListScaled) {
            for (Point pointRound : pointsListRounded) {
                if (pointRound.getCoords().equals(point.getRoundedCoords())) {
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

    private float selectMax(float xDiff, float yDiff, float zDiff) {
        return Math.max(Math.max(xDiff, yDiff), zDiff);
    }

    public float getMaxY() {
        return maxY;
    }

    public float getMaxZ() {
        return maxZ;
    }

}
