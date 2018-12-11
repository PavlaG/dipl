/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.io;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.io.File;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Pavla
 */
public abstract class PointCloudFile extends File{

    public PointCloudFile(String pathname) {
        super(pathname);
    }

    public PointCloudFile(String parent, String child) {
        super(parent, child);
    }

    public PointCloudFile(File parent, String child) {
        super(parent, child);
    }

    public PointCloudFile(URI uri) {
        super(uri);
    }
    
    
    
    public abstract Point readPoint();
    public abstract List<Point> readPoints();

    public abstract void close() ;
    
}
