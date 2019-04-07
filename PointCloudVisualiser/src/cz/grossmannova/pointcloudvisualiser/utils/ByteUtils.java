package cz.grossmannova.pointcloudvisualiser.utils;

/**
 *
 * @author Pavla
 */
public class ByteUtils {

    public static int colorByteToInt(byte red, byte green, byte blue) {
        int color = 0;
        color = (255 << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
        return color;
    }
}
