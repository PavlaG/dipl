/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import java.util.Comparator;

/**
 *
 * @author Pavla
 */
public class AStarBlockComparator implements Comparator<Block> {
    float weight=0f;
    @Override
    public int compare(Block o1, Block o2) {
         if (o1.startDistance+o1.finishDistance*weight < o2.startDistance+o2.finishDistance*weight) 
                    return -1; 
                else if (o1.startDistance+ o2.finishDistance*weight > o2.startDistance+o2.finishDistance*weight) 
                    return 1; 
                                return 0; 
                } 
}
