package cz.grossmannova.pointcloudvisualiser.pointcloud;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public class CubesMaker {

    private long time, startTime, endTime;

    private ArrayList<ArrayList<ArrayList<Point>>> contours;
    private final float maxZ;
    private int amountOfCubes = 0;
    private List<Vector3f> colors = new ArrayList<>();

    public CubesMaker(ArrayList<ArrayList<ArrayList<Point>>> contours, float maxZ) {
        this.contours = contours;
        this.maxZ = maxZ;
    }

    public List<Point> generate() {
        startTime = System.currentTimeMillis();
        List<Point> cubesPoints = new ArrayList<>();
        for (int y = 0; y < contours.size(); y++) {
            ArrayList<ArrayList<Point>> level = contours.get(y);
            for (int z = 0; z < maxZ + 1; z++) {
                ArrayList<Point> intersections = getIntersections(y, z, level);
                if (intersections.size() % 2 == 1) {
                    //  System.err.println("Lichý počet průsečíků:" + y);
                }
                for (int i = 0; i < intersections.size() / 2; i++) {
                    float from = Math.min(intersections.get(i * 2).getCoords().getX(), intersections.get(i * 2 + 1).getCoords().getX());
                    float to = Math.max(intersections.get(i * 2).getCoords().getX(), intersections.get(i * 2 + 1).getCoords().getX());

                    for (int x = ((int) from) + 1; x < to; x++) {
                        cubesPoints.add(new Point(x, y, z));
                    }
                }
            }
        }

        Random r = new Random();
        for (Point point : cubesPoints) {
            colors.add(new Vector3f(r.nextFloat(), r.nextFloat(), r.nextFloat()));
        }
        endTime = System.currentTimeMillis();
        time = endTime - startTime;
        return cubesPoints;
    }

    private ArrayList<Point> getIntersections(int y, int z, ArrayList<ArrayList<Point>> level) {
        ArrayList<Point> intersections = new ArrayList<>();
        for (ArrayList<Point> contour : level) {
            for (int i = 0; i < contour.size() - 1; i++) {
                Point p1 = contour.get(i);
                Point p2 = contour.get(i + 1);
                if (!isBetween(p1.getCoords().getZ(), p2.getCoords().getZ(), z)) {
                    continue;
                }
                float x = p1.getCoords().getX() + (p2.getCoords().getX() - p1.getCoords().getX()) * ((z - p1.getCoords().getZ()) / (p2.getCoords().getZ() - p1.getCoords().getZ()));
                intersections.add(new Point(x, y, z));
            }
        }
        return intersections;
    }

    private boolean isBetween(float a, float b, int c) {
        return (a <= c && b >= c) || (a >= c && b <= c);
    }

    public List<Vector3f> getColors() {
        return colors;
    }

    public int getAmountOfCubes() {
        return amountOfCubes;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
