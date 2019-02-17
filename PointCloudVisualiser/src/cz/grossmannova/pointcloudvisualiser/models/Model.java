/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public abstract class Model {

    public List<Point> pointsList = new ArrayList<Point>();
    public List<Point> pointsListNormalised = new ArrayList<Point>();
    public List<Point> pointsListScaled = new ArrayList<Point>();
    public List<Point> pointsListRounded = new ArrayList<Point>();
    ArrayList<Vector3f> triangles = new ArrayList<Vector3f>();
    ArrayList<ArrayList<ArrayList<Point>>> pointsAccordingToCountoursForEachLevel = new ArrayList<>(); //seznam seznamu seznamů bodů, které reprezentují několik kontur pro danou hodnotu y

    float minX, minY, minZ, maxX, maxY, maxZ; //není stále aktuální, dobře je po zavolání fce moveCornerOfObjectToCoords000()
    Vector3f objectDimensions;

    public void drawPoints() {
//        GL11.glColor3f(0.098f, 0.098f, 0.44f);
//        GL11.glBegin(GL11.GL_POINTS);
//       for (Point point : pointsListRounded) {
//            GL11.glVertex3f(point.getCoords().getX(), point.getCoords().getY(), point.getCoords().getZ());
//       }
//GL11.glEnd();

//pro marchingCubes
//GL11.glBegin(GL11.GL_TRIANGLES);
//for (Vector3f point : triangles) {
//           GL11.glVertex3f(point.getX(), point.getY(), point.getZ());
//       }
//       GL11.glEnd();
       

        GL11.glColor3f(1f, 0.0f, 0.0f);


for (ArrayList<ArrayList<Point>> arrayList : pointsAccordingToCountoursForEachLevel) {
            for (ArrayList<Point> contour : arrayList) {
                GL11.glBegin(GL11.GL_LINE_STRIP);
                 //               GL11.glBegin(GL11.GL_POINTS);

                for (Point point : contour) {
                    
                    GL11.glVertex3f(point.getCoords().getX(), point.getCoords().getY(), point.getCoords().getZ());
                }
              //  GL11.glVertex3f(contour.get(0).getCoords().getX(), contour.get(0).getCoords().getY(), contour.get(0).getCoords().getZ());
                GL11.glEnd();

           }
        }

    }
}
