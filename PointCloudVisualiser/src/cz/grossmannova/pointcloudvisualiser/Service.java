package cz.grossmannova.pointcloudvisualiser;

import cz.grossmannova.pointcloudvisualiser.io.PointCloudFile;
import cz.grossmannova.pointcloudvisualiser.io.PointCloudFileCSV;
import cz.grossmannova.pointcloudvisualiser.models.Block;
import cz.grossmannova.pointcloudvisualiser.models.BlockCloudModel;
import cz.grossmannova.pointcloudvisualiser.models.ContoursModel;
import cz.grossmannova.pointcloudvisualiser.models.CubesCloudModel;
import cz.grossmannova.pointcloudvisualiser.models.Graph;
import cz.grossmannova.pointcloudvisualiser.models.GraphModel;
import cz.grossmannova.pointcloudvisualiser.models.Model;
import cz.grossmannova.pointcloudvisualiser.models.ModelPointCloud;
import cz.grossmannova.pointcloudvisualiser.models.SphereModel;
import cz.grossmannova.pointcloudvisualiser.pathfinding.Dijkstra;
import cz.grossmannova.pointcloudvisualiser.pathfinding.Pathfinder;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockMakerType;
import cz.grossmannova.pointcloudvisualiser.pointcloud.BlockType;
import cz.grossmannova.pointcloudvisualiser.pointcloud.ContourMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.CubesMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Pavla
 */
public class Service {

    ModelPointCloud modelPointCloud;
    CubesCloudModel cubesCloudModel;
    BlockCloudModel cubeBlockCloudModel;
    BlockCloudModel cuboidBlockCloudModel;
    BlockCloudModel cubeOctreeBlockCloudModel;
    GraphModel graphModel;
    GraphModel graphModelForPath;
    BlockCloudModel blockModelForPath;
    ContoursModel contoursModel;
    SphereModel start;
    SphereModel finish;
    List<Point> cubes;
    List<Block> cubeBlocks;
    List<Block> cuboidBlocks;
    List<Block> cubesOctreeBlocks;
    PointCloudVisualiser app;
    List<Model> spaceModels;
    List<Model> pathfindingModels;
    GraphModel graphModelCubes;

    ContourMaker contourMaker;
    CubesMaker cubesMaker;
    BlockMaker cubeBlockMaker;
    BlockMaker cuboidBlockMaker;
    BlockMaker cubesOctreeBlockMaker;
    Pathfinder pathfinder;

    Pathfinder pathfinderCubes;
    GraphModel graphModelCuboids;
    Pathfinder pathfinderCuboids;
    GraphModel graphModelCubesOctree;
    Pathfinder pathfinderCubesOctree;

    GraphModel graphModelForPathCubes;
    GraphModel graphModelForPathCuboids;
    GraphModel graphModelForPathCubesOctree;

    BlockCloudModel blockModelForPathCubes;
    BlockCloudModel blockModelForPathCuboids;
    BlockCloudModel blockModelForPathCubesOctree;

    SphereModel startCubes;
    SphereModel finishCubes;
    SphereModel startCuboids;
    SphereModel finishCuboids;
    SphereModel startCubesOctree;
    SphereModel finishCubesOctree;
    boolean sameBlock = false;
    int pathFound = 0;

    public Service(String path, PointCloudVisualiser app, int scale) {
        this.app = app;
        PointCloudFile file = new PointCloudFileCSV(path);
        modelPointCloud = new ModelPointCloud(file.readPoints(), scale);
        spaceModels = new ArrayList<>();
        pathfindingModels = new ArrayList<>();

    }

    public void inicialisation(int contoursDistanceLimit) {
        //kontury
        contourMaker = new ContourMaker(modelPointCloud.pointsList, modelPointCloud.getMaxY(), contoursDistanceLimit);
        ArrayList<ArrayList<ArrayList<Point>>> contours = contourMaker.generate();
        contoursModel = new ContoursModel(contours);

        //vnitřní prvotní krychličky
        cubesMaker = new CubesMaker(contours, modelPointCloud.getMaxZ());
        cubes = cubesMaker.generate();
        cubesCloudModel = new CubesCloudModel(cubes, cubesMaker.getColors());

        //vytváření větších krychlí
        cubeBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.CUBE_EXPANSION);
        cubeBlocks = cubeBlockMaker.generateCubes();
        cubeBlockCloudModel = new BlockCloudModel(cubeBlocks);

        //vytváření kvádrů:
        cuboidBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.CUBOID_EXPANSION);
        cuboidBlocks = cuboidBlockMaker.generateCuboids();
        cuboidBlockCloudModel = new BlockCloudModel(cuboidBlocks);

        //vytváření krychlí skrz octree
        cubesOctreeBlockMaker = new BlockMaker(cubes, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ(), BlockMakerType.OCTREE);
        cubesOctreeBlocks = cubesOctreeBlockMaker.generateCubesThroughOctree();
        cubeOctreeBlockCloudModel = new BlockCloudModel(cubesOctreeBlocks);

        sendModeltsFromInicialisationToDraw();
        setVisibility(modelPointCloud);
    }

    public void setVisibility(Model model) {
        for (Model spaceModel : spaceModels) {
            spaceModel.setVisible(false);
        }
        model.setVisible(true);
    }

    public void pathfindingResetVisibility() {
        for (Model pathfindingModel : pathfindingModels) {
            pathfindingModel.setVisible(false);
        }
        start.setVisible(true);
        finish.setVisible(true);
    }

    public void sendModeltsFromInicialisationToDraw() {
        spaceModels.add(cubesCloudModel);
        spaceModels.add(cubeBlockCloudModel);
        spaceModels.add(cuboidBlockCloudModel);
        spaceModels.add(cubeOctreeBlockCloudModel);
        spaceModels.add(contoursModel);
        spaceModels.add(modelPointCloud);
        for (Model spaceModel : spaceModels) {
            app.sendModelToDraw(spaceModel);
        }
    }

    public int pathfindingInicialisation() {
        pathFound = 0;
        sameBlock = false;
        app.deleteAllPathfindingModelsToDraw(pathfindingModels);
        pathfindingModels.clear();

        Random r = new Random();
        int seed = r.nextInt();

        Graph graphCubes = new Graph(cubeBlocks, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ());
        graphModelCubes = new GraphModel(graphCubes.getLineGraph());
        pathfinderCubes = new Dijkstra(graphCubes);
        pathfinderCubes.resetStartAndFinish2(seed);
        if (pathfinderCubes.getStart().equals(pathfinderCubes.getFinish())) {
            sameBlock = true;
            pathFound = 2;
        }
        startCubes = new SphereModel(pathfinderCubes.getStart().getCenter());
        finishCubes = new SphereModel(pathfinderCubes.getFinish().getCenter());

        Graph graphCuboids = new Graph(cuboidBlocks, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ());
        graphModelCuboids = new GraphModel(graphCuboids.getLineGraph());
        pathfinderCuboids = new Dijkstra(graphCuboids);
        pathfinderCuboids.resetStartAndFinish2(seed);

        startCuboids = new SphereModel(pathfinderCuboids.getStart().getCenter());
        finishCuboids = new SphereModel(pathfinderCuboids.getFinish().getCenter());
        if (pathfinderCuboids.getStart().equals(pathfinderCuboids.getFinish())) {
            sameBlock = true;
            pathFound = 2;
        }

        Graph graphCubesOctree = new Graph(cubesOctreeBlocks, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ());
        graphModelCubesOctree = new GraphModel(graphCubesOctree.getLineGraph());
        pathfinderCubesOctree = new Dijkstra(graphCubesOctree);
        pathfinderCubesOctree.resetStartAndFinish2(seed);

        startCubesOctree = new SphereModel(pathfinderCubesOctree.getStart().getCenter());
        finishCubesOctree = new SphereModel(pathfinderCubesOctree.getFinish().getCenter());

        if (pathfinderCubesOctree.getStart().equals(pathfinderCubesOctree.getFinish())) {
            sameBlock = true;
            pathFound = 2;
        }
        if (sameBlock == false) {
            if (pathfinderCubes.findPath() == true) {
                pathFound = 1; //nalezená cesta
                graphModelForPathCubes = new GraphModel(pathfinderCubes.getLineGraph());
                blockModelForPathCubes = new BlockCloudModel(pathfinderCubes.getBlockGraph());
                pathfindingModels.add(graphModelForPathCubes);
                pathfindingModels.add(blockModelForPathCubes);

                pathfinderCuboids.findPath();
                graphModelForPathCuboids = new GraphModel(pathfinderCuboids.getLineGraph());
                blockModelForPathCuboids = new BlockCloudModel(pathfinderCuboids.getBlockGraph());
                pathfindingModels.add(graphModelForPathCuboids);
                pathfindingModels.add(blockModelForPathCuboids);

                pathfinderCubesOctree.findPath();
                graphModelForPathCubesOctree = new GraphModel(pathfinderCubesOctree.getLineGraph());
                blockModelForPathCubesOctree = new BlockCloudModel(pathfinderCubesOctree.getBlockGraph());
                pathfindingModels.add(graphModelForPathCubesOctree);
                pathfindingModels.add(blockModelForPathCubesOctree);

            }
//            else {
//                System.out.println("nepodařiloo se najít konec");
//            }
        }
//        else {
//            System.out.println("start a finish leží ve stejném bloku, opakujte hledání");
//        }
        sendModelsFromPathfindingInicialisation();
        return pathFound;
    }

    public void sendModelsFromPathfindingInicialisation() {
        pathfindingModels.add(graphModelCubes);
        pathfindingModels.add(graphModelCuboids);
        pathfindingModels.add(graphModelCubesOctree);
        pathfindingModels.add(startCubes);
        pathfindingModels.add(finishCubes);
        pathfindingModels.add(startCuboids);
        pathfindingModels.add(finishCuboids);
        pathfindingModels.add(startCubesOctree);
        pathfindingModels.add(finishCubesOctree);

        for (Model model : pathfindingModels) {
            app.sendModelToDraw(model);
        }
    }

    public void setAllPathfindingModelsToUnvisible() {
        for (Model pathfindingModel : pathfindingModels) {
            pathfindingModel.setVisible(false);
        }
    }

    public ModelPointCloud getModelPointCloud() {
        return modelPointCloud;
    }

    public CubesCloudModel getCubesCloudModel() {
        return cubesCloudModel;
    }

    public BlockCloudModel getCubeBlockCloudModel() {
        return cubeBlockCloudModel;
    }

    public BlockCloudModel getCuboidBlockCloudModel() {
        return cuboidBlockCloudModel;
    }

    public BlockCloudModel getCubeOctreeBlockCloudModel() {
        return cubeOctreeBlockCloudModel;
    }

    public GraphModel getGraphModel() {
        return graphModel;
    }

    public GraphModel getGraphModelForPath() {
        return graphModelForPath;
    }

    public BlockCloudModel getBlockModelForPath() {
        return blockModelForPath;
    }

    public SphereModel getStart() {
        return start;
    }

    public SphereModel getFinish() {
        return finish;
    }

    public ContoursModel getContoursModel() {
        return contoursModel;
    }

    public List<Block> getCubeBlocks() {
        return cubeBlocks;
    }

    public List<Block> getCuboidBlocks() {
        return cuboidBlocks;
    }

    public List<Block> getCubesOctreeBlocks() {
        return cubesOctreeBlocks;
    }

    public List<Point> getCubes() {
        return cubes;
    }

    public CubesMaker getCubesMaker() {
        return cubesMaker;
    }

    public BlockMaker getCubeBlockMaker() {
        return cubeBlockMaker;
    }

    public BlockMaker getCuboidBlockMaker() {
        return cuboidBlockMaker;
    }

    public BlockMaker getCubesOctreeBlockMaker() {
        return cubesOctreeBlockMaker;
    }

    public ContourMaker getContourMaker() {
        return contourMaker;
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }

    public GraphModel getGraphModelCubes() {
        return graphModelCubes;
    }

    public void setGraphModelCubes(GraphModel graphModelCubes) {
        this.graphModelCubes = graphModelCubes;
    }

    public GraphModel getGraphModelCuboids() {
        return graphModelCuboids;
    }

    public void setGraphModelCuboids(GraphModel graphModelCuboids) {
        this.graphModelCuboids = graphModelCuboids;
    }

    public GraphModel getGraphModelCubesOctree() {
        return graphModelCubesOctree;
    }

    public void setGraphModelCubesOctree(GraphModel graphModelCubesOctree) {
        this.graphModelCubesOctree = graphModelCubesOctree;
    }

    public GraphModel getGraphModelForPathCubes() {
        return graphModelForPathCubes;
    }

    public void setGraphModelForPathCubes(GraphModel graphModelForPathCubes) {
        this.graphModelForPathCubes = graphModelForPathCubes;
    }

    public GraphModel getGraphModelForPathCuboids() {
        return graphModelForPathCuboids;
    }

    public void setGraphModelForPathCuboids(GraphModel graphModelForPathCuboids) {
        this.graphModelForPathCuboids = graphModelForPathCuboids;
    }

    public GraphModel getGraphModelForPathCubesOctree() {
        return graphModelForPathCubesOctree;
    }

    public void setGraphModelForPathCubesOctree(GraphModel graphModelForPathCubesOctree) {
        this.graphModelForPathCubesOctree = graphModelForPathCubesOctree;
    }

    public BlockCloudModel getBlockModelForPathCubes() {
        return blockModelForPathCubes;
    }

    public void setBlockModelForPathCubes(BlockCloudModel blockModelForPathCubes) {
        this.blockModelForPathCubes = blockModelForPathCubes;
    }

    public BlockCloudModel getBlockModelForPathCuboids() {
        return blockModelForPathCuboids;
    }

    public void setBlockModelForPathCuboids(BlockCloudModel blockModelForPathCuboids) {
        this.blockModelForPathCuboids = blockModelForPathCuboids;
    }

    public BlockCloudModel getBlockModelForPathCubesOctree() {
        return blockModelForPathCubesOctree;
    }

    public void setBlockModelForPathCubesOctree(BlockCloudModel blockModelForPathCubesOctree) {
        this.blockModelForPathCubesOctree = blockModelForPathCubesOctree;
    }

    public SphereModel getStartCubes() {
        return startCubes;
    }

    public SphereModel getFinishCubes() {
        return finishCubes;
    }

    public SphereModel getStartCuboids() {
        return startCuboids;
    }

    public SphereModel getFinishCuboids() {
        return finishCuboids;
    }

    public SphereModel getStartCubesOctree() {
        return startCubesOctree;
    }

    public SphereModel getFinishCubesOctree() {
        return finishCubesOctree;
    }

    public boolean isSameBlock() {
        return sameBlock;
    }

    public int getPathFound() {
        return pathFound;
    }

    public void invalidateSelectedModel(BlockType blockType) {
        if (blockType == BlockType.POINTS) {
            modelPointCloud.invalidateDisplayList();
        } else if (blockType == BlockType.CONTOURS) {
            contoursModel.invalidateDisplayList();
        } else if (blockType == BlockType.TINY_CUBES) {
            cubesCloudModel.invalidateDisplayList();
        } else if (blockType == BlockType.CUBES) {
            cubeBlockCloudModel.invalidateDisplayList();
        } else if (blockType == BlockType.CUBOIDS) {
            cuboidBlockCloudModel.invalidateDisplayList();
        } else if (blockType == BlockType.CUBES_OCTREE) {
            cubeOctreeBlockCloudModel.invalidateDisplayList();
        }
    }

}
