/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.grossmannova.pointcloudvisualiser.utils;

/**
 *
 * @author Pavla
 */
public class ByteUtils {

    public static int colorByteToInt(byte red, byte green, byte blue) {
        int color = 0;
//        int ired = (red & 0xFF);
//        int igreen = (green & 0xFF) << 8;
//        int iblue = (blue & 0xFF) << 16;

        color = (255 << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
        //   color = ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
        return color;
    }
}
