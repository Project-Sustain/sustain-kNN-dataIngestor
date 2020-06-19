package sustain.kNN.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by laksheenmendis on 6/19/20 at 10:32 AM
 */
public class FileLoader {

    public static Properties loadFile(){

        Properties prop = new Properties();

        String path = "./config.properties";

        try {
            FileInputStream file = new FileInputStream(path);

            // load the properties file
            prop.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}

