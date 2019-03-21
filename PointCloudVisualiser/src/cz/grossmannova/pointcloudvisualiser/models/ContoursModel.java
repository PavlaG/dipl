/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 *
 * @author Pavla
 */
public class ContoursModel extends Model {

    private int listId = -1;

    public ContoursModel(ArrayList<ArrayList<ArrayList<Point>>> contours) {
        super(contours);
    }

    @Override
    public void draw() {
        if (isVisible()) {

            GL11.glPointSize(4);
            GL11.glColor3f(0.85f, 0.0f, 0.0f);
            GL20.glUseProgram(0);
            GL11.glDisable(GL11.GL_LIGHTING);
            if (listId == -1) {
                listId = GL11.glGenLists(1);
                GL11.glNewList(listId, GL11.GL_COMPILE_AND_EXECUTE);
                for (ArrayList<ArrayList<Point>> arrayList : contours) {
                    for (ArrayList<Point> contours : arrayList) {
                        for (int i = 0; i < contours.size() - 1; i++) {
                            GL11.glBegin(GL11.GL_LINES);
                            GL11.glVertex3f(contours.get(i).getCoords().x, contours.get(i).getCoords().y, contours.get(i).getCoords().z);
                            GL11.glVertex3f(contours.get(i + 1).getCoords().x, contours.get(i + 1).getCoords().y, contours.get(i + 1).getCoords().z);
                            GL11.glEnd();

                        }
                    }

                }
                GL11.glEndList();
            } else {
                GL11.glCallList(listId);
            }

            GL11.glEnable(GL11.GL_LIGHTING);
        }
    }
}
