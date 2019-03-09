package cz.grossmannova.pointcloudvisualiser.pointcloud;

import cz.grossmannova.pointcloudvisualiser.models.Block;
//import cz.grossmannova.pointcloudvisualiser.utils.PowersOf2;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Pavla
 */
public class BlockMaker {

    List<Point> cubes;
    boolean unusedCubeExists;
    private final float maxX, maxY, maxZ;
    private ArrayList<Block> blocks;

    private boolean[][][] cubesExistance;
    private Point[][][] cubesArray;

    public BlockMaker(List<Point> cubes, float maxX, float maxY, float maxZ, BlockMakerType type) {
        this.cubes = cubes;
        if (type == BlockMakerType.CUBE_EXPANSION || type == BlockMakerType.CUBOID_EXPANSION) {
            cubesArray = new Point[(int) maxX + 1][(int) maxY + 1][(int) maxZ + 1];
            cubesExistance = new boolean[(int) maxX + 1][(int) maxY + 1][(int) maxZ + 1];
        }
        if (type == BlockMakerType.OCTREE) {
            float max = Math.max(Math.max(maxX, maxY), maxZ);
            float sqrt = (float) Math.sqrt(max);
            sqrt=max+1;
            int nearestPowOf2 = nearestPowOf2((int)sqrt);
            cubesArray = new Point[nearestPowOf2][nearestPowOf2][nearestPowOf2];
            cubesExistance = new boolean[nearestPowOf2][nearestPowOf2][nearestPowOf2];

        }
        turnCubesIntoArray(cubes);
        unusedCubeExists = true;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public void turnCubesIntoArray(List<Point> cubes) {
        for (Point cube : cubes) {
            cubesArray[(int) cube.getCoords().x][(int) cube.getCoords().y][(int) cube.getCoords().z] = cube;
            cubesExistance[(int) cube.getCoords().x][(int) cube.getCoords().y][(int) cube.getCoords().z] = true;
        }
    }

    public List<Block> generateCubes() {
        blocks = new ArrayList<>();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                for (int z = 0; z <= maxZ; z++) {
                    if (cubesExistance[x][y][z] == true) {
                        createCubeBlock(cubesArray[x][y][z]);
                    }

                }
            }
        }
        System.out.println("Cubes count: " + blocks.size());
        return blocks;
    }

    public List<Block> generateCuboids() {
        blocks = new ArrayList<>();
        for (int y = 0; y <= maxY; y++) {
            for (int x = 0; x <= maxX; x++) {
                for (int z = 0; z <= maxZ; z++) {
                    if (cubesExistance[x][y][z] == true) {
                        expandCubeBlock(createCubeBlock(cubesArray[x][y][z]));

                    }

                }
            }
        }
        System.out.println("CCuboid count: " + blocks.size());
        return blocks;
    }

//     public List<Block> generate1() {
//        System.out.println("generování začátek");
//        blocks = new ArrayList<>();
//        while (unusedCubeExists) {
//            for (int y = 0; y <= maxY; y++) {
//                for (int x = 0; x <= maxX; x++) {
//                    for (int z = 0; z <= maxZ; z++) {
//                        if (cubes.isEmpty()) {
//                            System.out.println("už je prázné");
//                            //System.out.println("size"+ cubes.size());
//                            unusedCubeExists = false;
//                            break;
//                        }
//                        Point helpPoint = new Point(x, y, z);
//                        if (!cubes.contains(helpPoint)) {
//                            continue;
//                        }
//                        createBlock(helpPoint);
//                    }
//                    if (unusedCubeExists == false) {
//                        break;
//                    }
//                }
//                if (unusedCubeExists == false) {
//                    break;
//                }
//            }
//
//        }
//        System.out.println("generování konec");
//        return blocks;
//    }
    private Block createCubeBlock(Point point) {

        ArrayList<Point> cubeBuffer = new ArrayList<>();
        cubeBuffer.add(point);
        cubesExistance[(int) point.getCoords().x][(int) point.getCoords().y][(int) point.getCoords().z] = false;
        Block block = new Block();
        block.setPosition(point.getCoords());

        blocks.add(block);
        boolean useBuffer = true;
        int max = cubes.size();
        for (int step = 1; step < max; step++) { //původně bylo místo max Integer.maxValue, stejně operuju s tím, že se to nakonec z cyklu breakne
            //stěna vpravo
            for (int z = (int) point.getCoords().getZ(); z <= (int) point.getCoords().getZ() + step; z++) {
                for (int y = (int) point.getCoords().getY(); y <= (int) point.getCoords().getY() + step; y++) {
                    Point currentPoint = new Point(point.getCoords().getX() + step, y, z);
                    if (cubesExistance[(int) point.getCoords().getX() + step][y][z] == true) {
                        cubeBuffer.add(currentPoint);
                    } else {
                        useBuffer = false;
                        break;
                    }
                }
                if (useBuffer == false) {
                    break;
                }
            }
            if (useBuffer != false) {
                //stěna vepředu
                for (int x = (int) point.getCoords().getX(); x < (int) point.getCoords().getX() + step; x++) {
                    for (int y = (int) point.getCoords().getY(); y <= (int) point.getCoords().getY() + step; y++) {
                        Point currentPoint = new Point(x, y, point.getCoords().getZ() + step);
                        if (cubesExistance[x][y][(int) point.getCoords().getZ() + step] == true) {
                            cubeBuffer.add(currentPoint);
                        } else {
                            useBuffer = false;
                            break;
                        }
                    }
                    if (useBuffer == false) {
                        break;
                    }
                }
            }
            if (useBuffer != false) {
                //stěna dole
                for (int x = (int) point.getCoords().getX(); x < (int) point.getCoords().getX() + step; x++) {
                    for (int z = (int) point.getCoords().getZ(); z < (int) point.getCoords().getZ() + step; z++) {
                        Point currentPoint = new Point(x, point.getCoords().getY() + step, z);
                        if (cubesExistance[x][(int) point.getCoords().getY() + step][z] == true) {
                            cubeBuffer.add(currentPoint);
                        } else {
                            useBuffer = false;
                            break;
                        }
                    }
                    if (useBuffer == false) {
                        break;
                    }
                }
            }

            if (useBuffer == true) {
                //System.out.println("use" + step);
                block.addInnerPoints(cubeBuffer);
                block.setSize(step + 1);
                for (Point p : cubeBuffer) {
                    cubesExistance[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = false;
                }
                cubeBuffer.clear();
            } else {
                if (step == 1) {
                    List<Point> oneItemList = new ArrayList<Point>();
                    oneItemList.add(point);
                    block.setInnerPoints(oneItemList);
                    block.setSize(1);
                }
                cubeBuffer.clear();
                break;
            }
        }
        block.createCenter();
        return block;
    }

    private void createBlock1(Point point) {
        ArrayList<Point> cubeBuffer = new ArrayList<>();
        cubeBuffer.add(point);
        cubes.remove(point);

        Block block = new Block();
        block.setPosition(point.getCoords());

        blocks.add(block);
        // int step = 1;
        boolean useBuffer = true;
        // while (useBuffer) {
        int max = cubes.size();
        for (int step = 1; step < max; step++) { //původně bylo místo max Integer.maxValue, stejně operuju s tím, že se to nakonec z cyklu breakne
            //stěna vpravo
            for (int z = (int) point.getCoords().getZ(); z <= (int) point.getCoords().getZ() + step; z++) {
                for (int y = (int) point.getCoords().getY(); y <= (int) point.getCoords().getY() + step; y++) {
                    Point currentPoint = new Point(point.getCoords().getX() + step, y, z);
                    if (cubes.contains(currentPoint)) {
                        cubeBuffer.add(currentPoint);
                    } else {
                        useBuffer = false;
                        break;
                    }
                }
                if (useBuffer == false) {
                    break;
                }
            }
            if (useBuffer != false) {
                //stěna vepředu
                for (int x = (int) point.getCoords().getX(); x < (int) point.getCoords().getX() + step; x++) {
                    for (int y = (int) point.getCoords().getY(); y <= (int) point.getCoords().getY() + step; y++) {
                        Point currentPoint = new Point(x, y, point.getCoords().getZ() + step);
                        if (cubes.contains(currentPoint)) {
                            cubeBuffer.add(currentPoint);
                        } else {
                            useBuffer = false;
                            break;
                        }
                    }
                    if (useBuffer == false) {
                        break;
                    }
                }
            }
            if (useBuffer != false) {
                //stěna dole
                for (int x = (int) point.getCoords().getX(); x < (int) point.getCoords().getX() + step; x++) {
                    for (int z = (int) point.getCoords().getZ(); z < (int) point.getCoords().getZ() + step; z++) {
                        Point currentPoint = new Point(x, point.getCoords().getY() + step, z);
                        if (cubes.contains(currentPoint)) {
                            cubeBuffer.add(currentPoint);
                        } else {
                            useBuffer = false;
                            break;
                        }
                    }
                    if (useBuffer == false) {
                        break;
                    }
                }
            }

            if (useBuffer == true) {
                //System.out.println("use" + step);
                block.addInnerPoints(cubeBuffer);
                block.setSize(step + 1);
                cubes.removeAll(cubeBuffer);
                cubeBuffer.clear();
            } else {
                if (step == 1) {
                    List<Point> oneItemList = new ArrayList<Point>();
                    oneItemList.add(point);
                    block.setInnerPoints(oneItemList);
                    block.setSize(1);
                }
                cubeBuffer.clear();
                break;
            }
        }

    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    private void expandCubeBlock(Block block) {
        boolean right = true, front = true, bottom = true;
        Vector3f rightV = new Vector3f(1, 0, 0);
        Vector3f frontV = new Vector3f(0, 0, 1);
        Vector3f bottomV = new Vector3f(0, 1, 0);

        ArrayList<Point> cubeBuffer = new ArrayList<>();
        for (int z = (int) block.getPosition().z; z <= (int) block.getPosition().z + block.getSize().z - 1; z++) {
            for (int y = (int) block.getPosition().y; y <= (int) block.getPosition().y + block.getSize().y - 1; y++) {
                if (((int) block.getPosition().x + (int) block.getSize().x) >= cubesExistance.length || !cubesExistance[(int) block.getPosition().x + (int) block.getSize().x][y][z]) {
                    right = false;
                    break;
                } else {
                    cubeBuffer.add(cubesArray[(int) block.getPosition().x + (int) block.getSize().x][y][z]);

                }
            }
            if (right == false) {
                break;
            }
        }
        if (right) {
            block.expand(rightV);
            block.addInnerPoints(cubeBuffer);
            for (Point p : cubeBuffer) {
                cubesExistance[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = false;
            }
            cubeBuffer.clear();

        }
        for (int x = (int) block.getPosition().x; x <= (int) block.getPosition().x + block.getSize().x - 1; x++) {
            for (int y = (int) block.getPosition().y; y <= (int) block.getPosition().y + block.getSize().y - 1; y++) {
                if (((int) block.getPosition().z + (int) block.getSize().z) >= cubesExistance[x][y].length || !cubesExistance[x][y][(int) block.getPosition().z + (int) block.getSize().z] == true) {
                    front = false;
                    break;
                } else {
                    cubeBuffer.add(cubesArray[x][y][(int) block.getPosition().z + (int) block.getSize().z]);
                }
            }
            if (front == false) {
                break;
            }
        }
        if (front) {
            block.expand(frontV);
            block.addInnerPoints(cubeBuffer);
            for (Point p : cubeBuffer) {
                cubesExistance[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = false;
            }
            cubeBuffer.clear();
        }

        for (int x = (int) block.getPosition().x; x <= (int) block.getPosition().x + block.getSize().x - 1; x++) {
            for (int z = (int) block.getPosition().z; z <= (int) block.getPosition().z + block.getSize().z - 1; z++) {
                if (((int) block.getPosition().y + (int) block.getSize().y) >= cubesExistance[x].length || !cubesExistance[x][(int) block.getPosition().y + (int) block.getSize().y][z] == true) {
                    bottom = false;
                    break;
                } else {
                    cubeBuffer.add(cubesArray[x][(int) block.getPosition().y + (int) block.getSize().y][z]);

                }
            }
            if (bottom == false) {
                break;
            }
        }
        if (bottom) {
            block.expand(bottomV);
            block.addInnerPoints(cubeBuffer);

            for (Point p : cubeBuffer) {
                cubesExistance[(int) p.getCoords().x][(int) p.getCoords().y][(int) p.getCoords().z] = false;
            }
            cubeBuffer.clear();
        }
        block.createCenter();

    }
    

//    public List<Block> generateCubesThroughOctree() {
//        //tady pokračovat 
//        blocks = new ArrayList<>();
//        for (int y = 0; y <= maxY; y++) {
//            for (int x = 0; x <= maxX; x++) {
//                for (int z = 0; z <= maxZ; z++) {
//                    if (cubesExistance[x][y][z] == true) {
//                        createCubeBlock(cubesArray[x][y][z]);
//                    }
//
//                }
//            }
//        }
//
//        return blocks;
//    }
    
    public static int nearestPowOf2(int number) {
        int stop = (int) Math.pow(2, 24);
        for (int i = 1; i < stop; i *= 2) {
            if (i > number) {
                return number % 2;
            }
        }
        return 0;
    }

}
