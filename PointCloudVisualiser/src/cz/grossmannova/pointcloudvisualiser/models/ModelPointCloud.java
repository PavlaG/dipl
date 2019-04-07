package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class ModelPointCloud extends Model implements Cutable {

    private List<Point> pointsListNormalised = new ArrayList<>();
    private List<Point> pointsListScaled = new ArrayList<>();
    private List<Point> help = new ArrayList<>();
    private List<Point> help2 = new ArrayList<>();
    private List<Point> pointsListRounded = new ArrayList<>();
    private float minX, minY, minZ, maxX, maxY, maxZ; //není stále aktuální, dobře je po zavolání fce moveCornerOfObjectToCoords000()

    private int listId = -1;

    public ModelPointCloud(List<Point> pointsList, int scale) {

        super(pointsList);
//         for (Point point : pointsList) {
//            point.colorString();
//        }
        normalise();
        scale(scale);
        //round(); //tahle metoda zaokrouhlí souřadnice a posune body do mřížky, prozatím ji nahradím následujícím řádkem
        pointsListRounded = pointsListScaled;
        moveCornerOfObjectToCoords000(); //posune roh obalu objektu do 000      
        help = pointsListRounded;
//deduplikace:
        for (int i = 0; i < pointsListRounded.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (pointsListRounded.get(i).equals(pointsListRounded.get(j))) {
                    help.remove(pointsListRounded.get(i));
                }
            }
            for (int j = i + 1; j < pointsListRounded.size(); j++) {
                if (pointsListRounded.get(i).equals(pointsListRounded.get(j))) {
                    help.remove(pointsListRounded.get(i));
                }
            }
        }
        super.pointsList = help;
    }

    @Override
    public void draw() {
        if (isVisible()) {
            if (invalidate) {
                glInvalidateDisplayList();
            }
            GL11.glPointSize(4);
            GL20.glUseProgram(0);
            GL11.glDisable(GL11.GL_LIGHTING);
            if (listId == -1) {
                listId = GL11.glGenLists(1);
                GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
                GL11.glBegin(GL11.GL_POINTS);
                for (Point point : pointsList) {
                    if (doCut) {
                        if ((int) point.getCoords().y < cuttingPosition || (int) point.getCoords().y > cuttingPosition) {
                            continue;
                        }
                    }
                    GL11.glColor3f(point.getColor().x, point.getColor().y, point.getColor().z);
                    GL11.glVertex3f(point.getCoords().x, point.getCoords().y, point.getCoords().z);

                }
                GL11.glEnd();
                GL11.glEndList();
            } else {
                GL11.glCallList(listId);
            }
            GL11.glEnable(GL11.GL_LIGHTING);
        }
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
            pointsListNormalised.add(new Point(newX, newY, newZ, point.getColor()));
        }

        //  System.out.println("minX=" + minX + " maxX=" + maxX + " minY=" + minY + " maxY=" + maxY + " minZ=" + minZ + " maxZ=" + maxZ);
    }

    private void scale(int scale) {
        for (Point point : pointsListNormalised) {
            pointsListScaled.add(new Point(
                    point.getCoords().getX() * scale,
                    point.getCoords().getY() * scale,
                    point.getCoords().getZ() * scale, point.getColor())
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
                pointsListRounded.add(new Point(point.getRoundedCoords(), point.getColor()));
            } else {
                containsFlag = false;
            }
        }
//        System.out.println("rounded amount of points:" + pointsListRounded.size());
//        System.out.println("previous amount of points:" + pointsList.size());
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

    private float countDistanceBetween2PointsIn2D(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
    }

    private float selectMax(float xDiff, float yDiff, float zDiff) {
        return Math.max(Math.max(xDiff, yDiff), zDiff);
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public float getMaxZ() {
        return maxZ;
    }

    @Override
    public void invalidateDisplayList() {
        invalidate = true;
    }

    public void glInvalidateDisplayList() {
        if (listId != -1) {
            GL11.glDeleteLists(listId, 1);
            listId = -1;
        }
        invalidate = false;
    }

    

}
