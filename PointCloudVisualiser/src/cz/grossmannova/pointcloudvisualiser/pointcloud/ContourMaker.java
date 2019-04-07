package cz.grossmannova.pointcloudvisualiser.pointcloud;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pavla
 */
public class ContourMaker {

    private List<Point> pointsList;
    private ArrayList<ArrayList<ArrayList<Point>>> contours = new ArrayList<>(); //seznam seznamu seznamů bodů, které reprezentují několik kontur pro danou hodnotu y
    private final float maxY;
    private long time, startTime, endTime;
    int distanceLimit;

    public ContourMaker(List<Point> pointsList, float maxY, int distanceLimit) {
        this.pointsList = pointsList;
        this.maxY = maxY;
        this.distanceLimit = distanceLimit;
    }

    public ArrayList<ArrayList<ArrayList<Point>>> generate() {
        startTime = System.currentTimeMillis();
        Point currentPoint;
        float distanceFromCurrentPoint;
        int currentIndexForContourInSpecifiedLevel = -1;
        float minDistanceFromCurrentPoint = Float.MAX_VALUE;
        Point nearestPointToCurrentPoint = new Point();
        boolean nearestPointSet = false;
        boolean doNextRound = true;
        ArrayList<ArrayList<Point>> pointsAccordintToLevels = new ArrayList<>(); //vytvoření hlavního seznamu, který bude obsahovat seznamy bodů pro danou hodnotu y
        for (int y = 0; y <= maxY; y++) {
            pointsAccordintToLevels.add(new ArrayList<>());
            contours.add(new ArrayList<ArrayList<Point>>());
        }
        for (Point point : pointsList) { //rozdělím body do seznamů podle hodnoty y
            pointsAccordintToLevels.get((int) point.getCoords().getY()).add(point);
        }

        for (ArrayList<Point> listForLevel : pointsAccordintToLevels) { //prvotní cyklus přes všechny levely
            if (listForLevel.isEmpty()) { //ověřit, že to pak nidke nespadne, když level opravdu nebude nic obsahovat!!!
                System.out.println("level neobsahuje žádné body");
                continue;
            }
            currentIndexForContourInSpecifiedLevel = -1;
            while ((currentPoint = thereIsStillUnvisitedPoint(listForLevel)) != null) {
                contours.get(pointsAccordintToLevels.indexOf(listForLevel)).add(new ArrayList<Point>());
                currentIndexForContourInSpecifiedLevel++;
                currentPoint.setVisited(true);
                contours.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(currentPoint));
                doNextRound = true;
                int i = 0;
                while (doNextRound) {
                    minDistanceFromCurrentPoint = Float.MAX_VALUE;
                    for (Point point : listForLevel) {
                        if (i > 2) {
                            if (!point.equals(currentPoint) && (point.isVisited() == false || point.equals(contours.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0)))) {
                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint && distanceFromCurrentPoint < distanceLimit) {
                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
                                    nearestPointToCurrentPoint = point;
                                    nearestPointSet = true;
                                }
                            }
                        } else if (i <= 2) {
                            if (!point.equals(currentPoint) && point.isVisited() == false) {
                                distanceFromCurrentPoint = countDistanceBetween2PointsIn2D(currentPoint, point);
                                if (distanceFromCurrentPoint < minDistanceFromCurrentPoint && distanceFromCurrentPoint < distanceLimit) {
                                    minDistanceFromCurrentPoint = distanceFromCurrentPoint;
                                    nearestPointToCurrentPoint = point;
                                    nearestPointSet = true;
                                }
                            }
                        }
                    }
                    if (nearestPointSet) {
                        i++;
                        if (nearestPointToCurrentPoint.equals(contours.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).get(0))) {
                            doNextRound = false;
                            nearestPointSet = false;
                            //System.out.println("nearest is first");
                            contours.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));

                            nearestPointToCurrentPoint = new Point();
                        } else {
                            nearestPointToCurrentPoint.setVisited(true);
                            contours.get(pointsAccordintToLevels.indexOf(listForLevel)).get(currentIndexForContourInSpecifiedLevel).add(new Point(nearestPointToCurrentPoint));

                            nearestPointSet = false;
                            doNextRound = true;
                            currentPoint = nearestPointToCurrentPoint;
                        }
                    } else {
                        doNextRound = false;
                        nearestPointSet = false;
                        nearestPointToCurrentPoint = new Point();
                    }
                }
            }
        }
        closeUnclosedContours();
        endTime = System.currentTimeMillis();
        time = endTime - startTime;
        return contours;
    }

    private float countDistanceBetween2PointsIn2D(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.getCoords().getX() - p2.getCoords().getX(), 2) + Math.pow(p1.getCoords().getZ() - p2.getCoords().getZ(), 2));
    }

    private Point thereIsStillUnvisitedPoint(ArrayList<Point> listForLevel) {
        for (Point point : listForLevel) {
            if (point.isVisited() == false) {
                return point;
            }
        }
        return null;
    }

    private void closeUnclosedContours() {
        for (ArrayList<ArrayList<Point>> arrayList : contours) {
            for (ArrayList<Point> contour : arrayList) {
                if (!contour.get(0).equals(contour.get(contour.size() - 1))) {
                    contour.add(contour.get(0));
                }
            }
        }
    }

    public int getAmountOfContours() {
        int amount = 0;
        for (ArrayList<ArrayList<Point>> arrayList : contours) {
            for (ArrayList<Point> contour : arrayList) {
                amount++;
            }
        }
        return amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
