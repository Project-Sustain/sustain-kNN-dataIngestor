package sustain.kNN.utility;

import sustain.kNN.Constants;
import sustain.kNN.utility.exceptions.ValueNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by laksheenmendis on 6/19/20 at 12:54 AM
 */
public class PropertyLoader {

    private static Map<String, String> propertyValues = new HashMap<>();

    public static void loadPropertyFile()
    {
        Properties prop = FileLoader.loadFile();

        // get the property values
        propertyValues.put(Constants.PROPERTY_KEY_HOST, prop.getProperty("host"));
        propertyValues.put(Constants.PROPERTY_KEY_PORT, prop.getProperty("port"));
        propertyValues.put(Constants.PROPERTY_KEY_DATASET, prop.getProperty("dataset"));
        propertyValues.put(Constants.PROPERTY_KEY_FROMTIMESTAMP, prop.getProperty("fromTimestamp"));
        propertyValues.put(Constants.PROPERTY_KEY_TOTIMESTAMP, prop.getProperty("toTimestamp"));
        propertyValues.put(Constants.PROPERTY_KEY_GEOHASHES, prop.getProperty("geohashes"));
        propertyValues.put(Constants.PROPERTY_KEY_INTERMEDIATE_OUTPUT_FILE, prop.getProperty("intermediate.output.file"));
    }

    public static String getHost() throws ValueNotFoundException
    {
        String host = propertyValues.get(Constants.PROPERTY_KEY_HOST);

        if(host != null)
        {
            return host;
        }
        throw new ValueNotFoundException("host not found");
    }

    public static int getPort() throws NumberFormatException, ValueNotFoundException
    {
        String portString = propertyValues.get(Constants.PROPERTY_KEY_PORT);
        if( portString != null )
        {
            int port = Integer.parseInt(portString);
            return port;
        }
        throw new ValueNotFoundException("port not found");
    }

    public static String getDataset() throws ValueNotFoundException
    {
        String dataset = propertyValues.get(Constants.PROPERTY_KEY_DATASET);

        if(dataset != null)
        {
            return dataset;
        }
        throw new ValueNotFoundException("dataset not found");
    }

    public static String getFromTimeStamp() throws ValueNotFoundException
    {
        String timestamp = propertyValues.get(Constants.PROPERTY_KEY_FROMTIMESTAMP);

        if(timestamp != null)
        {
            return timestamp;
        }
        throw new ValueNotFoundException("from timestamp not found");
    }

    public static String getToTimeStamp() throws ValueNotFoundException
    {
        String timestamp = propertyValues.get(Constants.PROPERTY_KEY_TOTIMESTAMP);

        if(timestamp != null)
        {
            return timestamp;
        }
        throw new ValueNotFoundException("to timestamp not found");
    }

    public static String[] getGeoHashes() throws ValueNotFoundException
    {
        String fullString = propertyValues.get(Constants.PROPERTY_KEY_GEOHASHES);

        if(fullString != null && !fullString.isEmpty())
        {
            String [] arr = fullString.split(Constants.SEPARATOR_COMMA);
            return arr;
        }
        throw new ValueNotFoundException("GeoHashes not found in property file");
    }

    public static String getIntermediateFile() throws ValueNotFoundException
    {
        String outputfile = propertyValues.get(Constants.PROPERTY_KEY_INTERMEDIATE_OUTPUT_FILE);
        if(outputfile != null)
        {
            return outputfile;
        }
        throw new ValueNotFoundException("Intermediate output path not found");
    }
}
