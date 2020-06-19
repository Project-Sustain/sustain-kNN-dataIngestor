package sustain.kNN.utility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import sustain.kNN.Constants;
import sustain.kNN.utility.exceptions.ValueNotFoundException;
import java.util.Map;
import java.util.Properties;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by laksheenmendis on 6/19/20 at 11:05 AM
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({FileLoader.class, PropertyLoader.class})
public class PropertyLoaderTest {

    private static final Properties PROPERTIES = createMockProperties();
    private static final String HOST = "host";
    private static final String PORT = "1234";
    private static final String WRONG_PORT = "alphaport";
    private static final String DATASET = "dataset";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String GEOHASHES = "9x,9xave";
    private static final String EMPTY_GEOHASHES = "";
    private static final String INTERMEDIATE_FILE = "filepath";

    private static Properties createMockProperties() {

        Properties properties = new Properties();
        properties.setProperty("host", HOST);
        properties.setProperty("port", PORT);
        properties.setProperty("dataset", DATASET);
        properties.setProperty("fromTimestamp", FROM);
        properties.setProperty("toTimestamp", TO);
        properties.setProperty("geohashes", GEOHASHES);
        properties.setProperty("intermediate.output.file", INTERMEDIATE_FILE);
        return properties;
    }

    @Before
    public void setUp() throws Exception {
        mockStatic(FileLoader.class);
        when(FileLoader.loadFile()).thenReturn(PROPERTIES);
    }

    @Test
    public void loadPropertyFile() {
        PropertyLoader.loadPropertyFile();
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        assertProperties(propertyValues);
    }

    private void assertProperties(Map<String, String> propertyValues) {
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_HOST, HOST));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_PORT, PORT));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_DATASET, DATASET));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_FROMTIMESTAMP, FROM));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_TOTIMESTAMP, TO));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_GEOHASHES, GEOHASHES));
        assertThat(propertyValues, hasEntry(Constants.PROPERTY_KEY_INTERMEDIATE_OUTPUT_FILE, INTERMEDIATE_FILE));
    }

    @Test
    public void getHost() throws Exception{
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_HOST, HOST);

        assertEquals(HOST, PropertyLoader.getHost());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getHost_onValueException() throws Exception{
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.clear();
        PropertyLoader.getHost();
    }

    @Test
    public void getPort() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_PORT, PORT);

        assertEquals(Integer.parseInt(PORT), PropertyLoader.getPort());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getPort_onValueException() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.clear();
        PropertyLoader.getPort();
    }

    @Test(expected = NumberFormatException.class)
    public void getPort_onNumberException() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_PORT, WRONG_PORT);

        PropertyLoader.getPort();
        propertyValues.clear();
    }

    @Test
    public void getDataset() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_DATASET, DATASET);

        assertEquals(DATASET, PropertyLoader.getDataset());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getDataset_onValueException() throws Exception{
        PropertyLoader.getDataset();
    }

    @Test
    public void getFromTimeStamp() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_FROMTIMESTAMP, FROM);

        assertEquals(FROM, PropertyLoader.getFromTimeStamp());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getFromTimestamp_onValueException() throws Exception{
        PropertyLoader.getFromTimeStamp();
    }

    @Test
    public void getToTimeStamp() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_TOTIMESTAMP, TO);

        assertEquals(TO, PropertyLoader.getToTimeStamp());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getToTimestamp_onValueException() throws Exception{
        PropertyLoader.getToTimeStamp();
    }

    @Test
    public void getGeoHashes() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_GEOHASHES, GEOHASHES);

        assertArrayEquals(GEOHASHES.split(Constants.SEPARATOR_COMMA), PropertyLoader.getGeoHashes());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getGeoHashes_onValueException() throws Exception{
        PropertyLoader.getGeoHashes();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getGeoHashes_onEmptyValueException() throws Exception{
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_GEOHASHES, EMPTY_GEOHASHES);

        PropertyLoader.getGeoHashes();
        propertyValues.clear();
    }

    @Test
    public void getIntermediateFile() throws Exception {
        Map<String, String> propertyValues = Whitebox.getInternalState(PropertyLoader.class, "propertyValues");
        propertyValues.put(Constants.PROPERTY_KEY_INTERMEDIATE_OUTPUT_FILE, INTERMEDIATE_FILE);

        assertEquals(INTERMEDIATE_FILE, PropertyLoader.getIntermediateFile());
        propertyValues.clear();
    }

    @Test(expected = ValueNotFoundException.class)
    public void getIntermediateFile_onValueException() throws Exception{
        PropertyLoader.getIntermediateFile();
    }
}