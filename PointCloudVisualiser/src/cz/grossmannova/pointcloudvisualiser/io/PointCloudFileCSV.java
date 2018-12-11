/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.io;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PointCloudFileCSV extends PointCloudFile {

    private FileInputStream fis = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;

    public PointCloudFileCSV(String pathname) {
        super(pathname);
    }

    public PointCloudFileCSV(String parent, String child) {
        super(parent, child);
    }

    public PointCloudFileCSV(File parent, String child) {
        super(parent, child);
    }

    public PointCloudFileCSV(URI uri) {
        super(uri);
    }

    @Override
    public Point readPoint() {
        String line = null;
        if (fis == null) {
            try {
                fis = new FileInputStream(this);

                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PointCloudFileCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            line = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(PointCloudFileCSV.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (line == null) {
            return null;
        }
        return new Point(line);
    }

    @Override
    public void close() {
        try {
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(PointCloudFileCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Point> readPoints() {
        List<Point> list = new ArrayList<>();
        Point point = null;
        while ((point = this.readPoint()) != null) {
            list.add(point);
        }
        close();
        return list;
    }

}
