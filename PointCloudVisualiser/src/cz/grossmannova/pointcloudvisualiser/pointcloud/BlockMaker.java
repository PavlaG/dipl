package cz.grossmannova.pointcloudvisualiser.pointcloud;

import cz.grossmannova.pointcloudvisualiser.models.Block;
//import cz.grossmannova.pointcloudvisualiser.utils.PowersOf2;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
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
            int nearestPowOf2 = nearestPowOf2((int) max + 1);  //zkontrolovat, co tam má valastně jít
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
        return blocks;
    }

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

    public List<Block> generateCubesThroughOctree() {
        ArrayList<Point> cubeBuffer = new ArrayList<>();
        boolean somethingIsEmpty = false;
        boolean somethingIsFull = false;
        blocks = new ArrayList<>();
        Stack<int[]> stack = new Stack<int[]>();
        int[] indexes = {0, cubesArray.length - 1, 0, cubesArray[1].length - 1, 0, cubesArray[1][1].length - 1};
        stack.add(indexes);
        int[] i; //= new int[6];
        while (!stack.empty()) {
            i = stack.pop();
            for (int y = i[2]; y <= i[3]; y++) {
                for (int x = i[0]; x <= i[1]; x++) {
                    for (int z = i[4]; z <= i[5]; z++) {
                        if (!cubesExistance[x][y][z] == true) {
                            somethingIsEmpty = true;

                        } else {
                            somethingIsFull = true;
                        }
                    }
                }
            }
            if (somethingIsEmpty && somethingIsFull) {
                int XLeft = ((i[1] - i[0] + 1) / 2) + i[0] - 1;
                int YTop = ((i[3] - i[2] + 1) / 2) + i[2] - 1;
                int ZBack = ((i[5] - i[4] + 1) / 2) + i[4] - 1;
                int[] newTLB = {i[0], XLeft, i[2], YTop, i[4], ZBack};
                int[] newTRB = {XLeft + 1, i[1], i[2], YTop, i[4], ZBack};
                int[] newTRF = {XLeft + 1, i[1], i[2], YTop, ZBack + 1, i[5]};
                int[] newTLF = {i[0], XLeft, i[2], YTop, ZBack + 1, i[5]};

                int[] newBLB = {i[0], XLeft, YTop + 1, i[3], i[4], ZBack};
                int[] newBRB = {XLeft + 1, i[1], YTop + 1, i[3], i[4], ZBack};
                int[] newBRF = {XLeft + 1, i[1], YTop + 1, i[3], ZBack + 1, i[5]};
                int[] newBLF = {i[0], XLeft, YTop + 1, i[3], ZBack + 1, i[5]};

                stack.push(newTLB);
                stack.push(newTRB);
                stack.push(newTRF);
                stack.push(newTLF);

                stack.push(newBLB);
                stack.push(newBRB);
                stack.push(newBRF);
                stack.push(newBLF);
                somethingIsEmpty = false;
                somethingIsFull = false;
            } else if (somethingIsFull && !somethingIsEmpty) {
                for (int y = i[2]; y <= i[3]; y++) {
                    for (int x = i[0]; x <= i[1]; x++) {
                        for (int z = i[4]; z <= i[5]; z++) {
                            cubeBuffer.add(cubesArray[x][y][z]);
                        }
                    }
                }
                Block block = new Block(new Vector3f(i[1] - i[0] + 1, i[3] - i[2] + 1, i[5] - i[4] + 1), new Vector3f(i[0], i[2], i[4]));
                block.addInnerPoints(cubeBuffer);
                block.createCenter();
                blocks.add(block);
                cubeBuffer.clear();

                somethingIsEmpty = false;
                somethingIsFull = false;
            }
        }
        return blocks;
    }

    public static int nearestPowOf2(int number) {
        int stop = (int) Math.pow(2, 24);
        for (int i = 1; i < stop; i *= 2) {
            if (i > number) {
                return i;
            }
        }
        return 0;
    }

}
