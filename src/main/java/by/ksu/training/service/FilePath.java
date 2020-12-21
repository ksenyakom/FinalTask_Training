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
 //   public static String xsdFile;

    static {
        try {
            String mainPathProperties = initMainPath(dataBasePropertiesFolder);
            dataBasePropertiesPath = String.valueOf(Paths.get(mainPathProperties, dataBasePropertiesFile));

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


    public static String initMainPath(String folder) throws ServiceException {
        // ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URI uri = null;
        try {
            //   uri = loader.getResource(folder).toURI();
            uri = ClassLoader.getSystemResource(folder).toURI();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return Paths.get(uri).toString();
    }

}
