package cz.grossmannova.pointcloudvisualiser.io;

import cz.grossmannova.pointcloudvisualiser.pointcloud.Point;
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
    private List<Point> list2 = new ArrayList<>();

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
        while (this.readLine() != 0) {
        }
        close();
        return list2;
    }

    public int readLine() {
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
            return 0;
        }
        if (line == null) {
            return 0;
        }
        if (line.startsWith("v ")) {

            list2.add(new Point(line));
            //System.out.println("color::::"+ list2.get(list2.size()-1).getColor().toString());
            return 1;
        }
        return -1;

    }

}
