/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.models;

import cz.grossmannova.pointcloudvisualiser.pathfinding.Edge;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockMakerType;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Pavla
 */
public class Graph {

    private boolean[][][] cubesExistance;
    private Point[][][] cubesArray;
    private final float maxX, maxY, maxZ;
    List<Block> blocks;
    ArrayList<Point> centers;

    public Graph(List<Block> blocks, float maxX, float maxY, float maxZ) {
        this.blocks = blocks;
        centers = new ArrayList<>();

        cubesArray = new Point[(int) maxX + 1][(int) maxY + 1][(int) maxZ + 1];
        cubesExistance = new boolean[(int) maxX + 1][(int) maxY + 1][(int) maxZ + 1];
        turnCubesIntoArray(blocks);
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        blocksToGraph();
    }

    public void turnCubesIntoArray(List<Block> blocks) {
        for (Block block : blocks) {
            for (Point p : block.getInnerPoints()) {
                cubesArray[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = p;
                cubesExistance[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = true;
            }
        }

    }

    public ArrayList<Point> getLineGraph() {
        centers = new ArrayList<>();
        for (Block block : blocks) {
            for (Block neighbour : block.getNeighbours()) {
                centers.add(block.getCenter());
                centers.add(neighbour.getCenter());
            }
        }
        return centers;
    }

    private void blocksToGraph() {
        for (Block block : blocks) {
            //stěna vpravo
            for (int z = (int) block.getPosition().z; z <= (int) block.getPosition().z + block.getSize().z - 1; z++) {
                for (int y = (int) block.getPosition().y; y <= (int) block.getPosition().y + block.getSize().y - 1; y++) {
                    if (block.getPosition().x + (int) block.getSize().x <= cubesArray.length - 1 && cubesExistance[(int) block.getPosition().x + (int) block.getSize().x][y][z] == true) {
                        if (!cubesArray[(int) block.getPosition().x + (int) block.getSize().x - 1][y][z].getCorrespondingBlock().equals(cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock())) {
                            block.addNeighbour(cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock());
                            block.addEdge(new Edge(block, cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock()));
                            cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock().addNeighbour(block);
                            cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock().addEdge(new Edge(cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z].getCorrespondingBlock(), block));
                        }
                    }
                }

            }
            //stěna vepředu
            for (int x = (int) block.getPosition().x; x <= (int) block.getPosition().x + block.getSize().x - 1; x++) {
                for (int y = (int) block.getPosition().y; y <= (int) block.getPosition().y + block.getSize().y - 1; y++) {
                    if (block.getPosition().z + (int) block.getSize().z <= cubesArray[x][y].length - 1 && cubesExistance[x][y][(int) block.getPosition().z + (int) block.getSize().z] == true) {
                        if (!cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z - 1].getCorrespondingBlock().equals(cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock())) {
                            block.addNeighbour(cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock());
                            cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock().addNeighbour(block);

                            block.addEdge(new Edge(block, cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock()));
                            cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock().addEdge(new Edge(cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z].getCorrespondingBlock(), block));

                        }
                    }
                }

            }

            //stěna dole
            for (int x = (int) block.getPosition().x; x <= (int) block.getPosition().x + block.getSize().x - 1; x++) {
                for (int z = (int) block.getPosition().z; z <= (int) block.getPosition().z + block.getSize().z - 1; z++) {
                    if (block.getPosition().y + (int) block.getSize().y <= cubesArray[x].length - 1 && cubesExistance[x][(int) block.getPosition().y + (int) block.getSize().y][z] == true) {
                        if (!cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y - 1][z].getCorrespondingBlock().equals(cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock())) {
                            block.addNeighbour(cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock());
                            cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock().addNeighbour(block);

                            block.addEdge(new Edge(block, cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock()));
                            cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock().addEdge(new Edge(cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z].getCorrespondingBlock(), block));

                        }
                    }
                }
            }
        }

    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public boolean[][][] getCubesExistance() {
        return cubesExistance;
    }

    public void setCubesExistance(boolean[][][] cubesExistance) {
        this.cubesExistance = cubesExistance;
    }

    public Point[][][] getCubesArray() {
        return cubesArray;
    }

    public void setCubesArray(Point[][][] cubesArray) {
        this.cubesArray = cubesArray;
    }

    public ArrayList<Point> getCenters() {
        return centers;
    }

    public void setCenters(ArrayList<Point> centers) {
        this.centers = centers;
    }

}
