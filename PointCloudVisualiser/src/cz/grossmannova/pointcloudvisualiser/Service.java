/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import cz.grossmannova.pointcloudvisualiser.pointcloud.ContourMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.CubesMaker;
import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.util.ArrayList;
import java.util.List;

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
     
    public Service(String path, PointCloudVisualiser app) {
//
        this.app = app;
        PointCloudFile file = new PointCloudFileCSV(path);
        modelPointCloud = new ModelPointCloud(file.readPoints());
        spaceModels = new ArrayList<>();
        pathfindingModels = new ArrayList<>();
    }

    public void inicialisation() {//kontury
//        this.app = app;
//        PointCloudFile file = new PointCloudFileCSV(path);
//        modelPointCloud = new ModelPointCloud(file.readPoints());
//        spaceModels = new ArrayList<>();
//        pathfindingModels = new ArrayList<>();
//        

        // app.deleteAllModelsFromModelsToDraw();
       
        contourMaker = new ContourMaker(modelPointCloud.pointsList, modelPointCloud.getMaxY());
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
//        app.sendModelToDraw(cubesCloudModel);
//        app.sendModelToDraw(cubeBlockCloudModel);
//        app.sendModelToDraw(cuboidBlockCloudModel);
//        app.sendModelToDraw(cubeOctreeBlockCloudModel);
    }

    

    public void graphAndSoOn(List<Block> blocks, boolean showLines) {
        app.deleteAllPathfindingModelsToDraw(pathfindingModels);
        pathfindingModels.clear();
        //vytváření grafu
        
        Graph graph = new Graph(blocks, modelPointCloud.getMaxX(), modelPointCloud.getMaxY(), modelPointCloud.getMaxZ());
        graphModel = new GraphModel(graph.getLineGraph());

        pathfinder = new Dijkstra(graph);
        pathfinder.randomlySetStartAndFinish();
        //pathfinder.resetStartAndFinish();
        start = new SphereModel(pathfinder.getStart().getCenter());
        finish = new SphereModel(pathfinder.getFinish().getCenter());
        pathfindingResetVisibility();
        if (pathfinder.findPath()) {
            System.out.println("nalezeno");
            graphModelForPath = new GraphModel(pathfinder.getLineGraph());
            blockModelForPath = new BlockCloudModel(pathfinder.getBlockGraph());
            pathfindingModels.add(graphModelForPath);
            pathfindingModels.add(blockModelForPath);

            if (showLines) {
                graphModelForPath.setVisible(true);
            } else {
                blockModelForPath.setVisible(true);
            }
        } else {
            System.out.println("nepodařiloo se najít konec");
        }
        sendModelsFromGraph();
    }

    public void sendModelsFromGraph() {

        // pathfindingModels.add(graphModelForPath);
        pathfindingModels.add(graphModel);
        //graphModel.setVisible(true);
        pathfindingModels.add(start);
        pathfindingModels.add(finish);
        for (Model model : pathfindingModels) {
            app.sendModelToDraw(model);
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
    
}
