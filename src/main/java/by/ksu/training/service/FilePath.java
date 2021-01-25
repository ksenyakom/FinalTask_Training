package by.ksu.training.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class FilePath {
    private static final Logger logger = LogManager.getLogger(FilePath.class);

    public static String mainPath;
    public static String dataBasePropertiesPath;
    private static String dataBasePropertiesFile = "database.properties";
    private static String dataBasePropertiesFolder = "properties";
    private static final String imgFolder = "img";
    private static final String audioFolder = "audio";
    public static String imgFolderPath;
    public static String audioFolderPath;

    static {
        try {
            String mainPathProperties = initMainPath(dataBasePropertiesFolder);
            dataBasePropertiesPath = String.valueOf(Paths.get(mainPathProperties, dataBasePropertiesFile));
//            imgFolderPath = initMainPath(imgFolder);
//            audioFolderPath = initMainPath(audioFolder);

        } catch (ServiceException e) {
            e.printStackTrace();
            //TODO something with this
        }
//        try {
//            mainPath = initMainPath("data");
//        } catch (ServiceException e) {
//            logger.error(e.getMessage(), e);
//        }
//        xsdFile = getPath(xsdSchemaFile);
    }

    public static String getImgPath(String filename) {
        return String.valueOf(Paths.get(imgFolderPath,"exercises", filename));
    }

    public static String getAudioPath(String filename) {
        return String.valueOf(Paths.get(audioFolderPath, filename));
    }

    public static String initMainPath(String folder) throws ServiceException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URI uri = null;
        try {
            uri = loader.getResource(folder).toURI();
            //  uri = ClassLoader.getSystemResource(folder).toURI();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return Paths.get(uri).toString();
    }

}
