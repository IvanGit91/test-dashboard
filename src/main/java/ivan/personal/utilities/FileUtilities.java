package ivan.personal.utilities;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileUtilities {
    private static final Logger LOG = Logger.getLogger(FileUtilities.class.getName());

    private FileUtilities() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean createDirIfNotExist(String filePath) {
        File theDir = new File(filePath);
        boolean result = false;
        if (!theDir.exists()) {
            try {
                result = theDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            if (result) {
                LOG.log(Level.INFO, "DIR created: " + filePath);
            }
        }
        return result;
    }

    public static boolean deleteFile(String filePath) {
        boolean ret = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (file.delete()) {
                    LOG.log(Level.INFO, file.getName() + " is deleted!");
                    ret = true;
                } else {
                    LOG.log(Level.INFO, "Delete operation is failed.");
                }
            }
        } catch (Exception e) {
            LOG.log(Level.INFO, "Delete operation is failed. " + e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }
}
