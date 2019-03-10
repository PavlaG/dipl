/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.pathfinding;

import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;

/**
 *
 * @author Pavla
 */
public class AlgorithmName extends Pathfinder {

    public AlgorithmName(Graph graph) {
        super(graph);
    }

    @Override
    public void findPath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void randomlySetStartAndFinish() {
        boolean stop = false;
        for (int y = 0; y < cubesArray[1].length; y++) {
            for (int x = 0; x < cubesArray.length; x++) {
                for (int z = 0; z < cubesArray[1][1].length; z++) {
                    if (cubesExistance[x][y][z] == true) {
                        start = cubesArray[x][y][z];
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            }
        }

        stop = false;
        for (int y = cubesArray[1].length - 1; y >= 0; y--) {
            for (int x = cubesArray.length - 1; x >= 0; x--) {
                for (int z = cubesArray[1][1].length - 1; z >= 0; z--) {
                    if (cubesExistance[x][y][z] == true) {
                        finish = cubesArray[x][y][z];
                        stop = true;
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }
            if (stop) {
                break;
            }
        }
        System.out.println("start: "+ start.getCoords().toString());
        System.out.println("finish: "+ finish.getCoords().toString());
    }

   
    
    

}
