package cz.grossmannova.pointcloudvisualiser.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

public class LibUtils {

    public static void addPathToLibrary(String newPath) throws Exception {
        final Field fieldOfPaths = ClassLoader.class.getDeclaredField("usr_paths");
        fieldOfPaths.setAccessible(true);
        final String[] oldPaths = (String[]) fieldOfPaths.get(null);
        for (String p : oldPaths) {
            if (p.equals(newPath)) {
                return;
            }
        }
        final String[] NewPathsArray = Arrays.copyOf(oldPaths, oldPaths.length + 1);
        NewPathsArray[NewPathsArray.length - 1] = newPath;
        fieldOfPaths.set(null, NewPathsArray);
    }

    public static void createCopyOfNatives() throws IOException, FileNotFoundException {
        int read = -1;

        File nativesFolder = new File(".", "natives");
        nativesFolder.mkdirs();
        String[] files = new String[]{
            "jinput-dx8.dll",
            "jinput-dx8_64.dll",
            "jinput-raw.dll",
            "jinput-raw_64.dll",
            "lwjgl.dll",
            "lwjgl64.dll",
            "OpenAL32.dll",
            "OpenAL64.dll"
        };
        for (String f : files) {
            File file = new File(nativesFolder, f);
            if (file.exists() && 0 < file.length()) {
                continue;
            }
            file.createNewFile();
            InputStream inputS = LibUtils.class.getResourceAsStream("/natives/" + f);
            OutputStream outputS = new FileOutputStream(file);
            byte[] buffer = new byte[4096];

            while ((read = inputS.read(buffer)) != -1) {
                outputS.write(buffer, 0, read);
            }
            inputS.close();
            outputS.close();
        }
    }
}
