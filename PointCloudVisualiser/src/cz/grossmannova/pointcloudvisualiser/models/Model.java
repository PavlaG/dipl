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

/**
 *
 * @author Pavla
 */
public abstract class Model {
   List<Point> pointsList= new ArrayList<Point>(); 
   
   public void drawPoints(){
       GL11.glColor3f(0.098f, 0.098f, 0.44f);
       GL11.glBegin(GL11.GL_POINTS);
       for (Point point : pointsList) {
            GL11.glVertex3f(point.getCoords().getX(), point.getCoords().getY(), point.getCoords().getZ());
       }
       GL11.glEnd();
   }
}
