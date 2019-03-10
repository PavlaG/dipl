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
public class BlockComparator implements Comparator<Block>{

    @Override
    public int compare(Block o1, Block o2) {
         if (o1.startDistance < o2.startDistance) 
                    return -1; 
                else if (o1.startDistance > o2.startDistance) 
                    return 1; 
                                return 0; 
                } 
    }
    

