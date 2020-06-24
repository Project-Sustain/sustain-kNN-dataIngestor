package sustain.synopsis.common;

import sustain.synopsis.sketch.dataset.SpatialRange;
import sustain.synopsis.sketch.graph.Path;
import sustain.synopsis.sketch.serialization.SerializationException;
import sustain.synopsis.sketch.serialization.SerializationInputStream;
import sustain.synopsis.sketch.stat.RunningStatisticsND;
import sustain.synopsis.sketch.util.Geohash;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by laksheenmendis on 6/23/20 at 3:10 PM
 */
public class ExtStrand extends Strand {

    private float meanLon;
    private float meanLat;
    private SpatialRange spatialRange;
    private String features;
    private String mean_features;
    private RunningStatisticsND stats = null;

    private List<ExtStrand> originalStrandsMapped;

    public ExtStrand(String geohash, long fromTimeStamp, long toTimestamp, Path path) {
        super(geohash, fromTimeStamp, toTimestamp, path);
        spatialRange = Geohash.decodeHash(this.getGeohash());
        features = this.getPath().stream().map(vertex -> vertex.getLabel().getDouble() + ",").collect(Collectors.joining());
    }

    public ExtStrand(SerializationInputStream sis) throws IOException, SerializationException {
        super(sis);
        spatialRange = Geohash.decodeHash(this.getGeohash());
        features = this.getPath().stream().map(vertex -> vertex.getLabel().getDouble() + ",").collect(Collectors.joining());
        stats = this.getPath().get(this.getPath().size() - 1).getData().statistics;
        mean_features = getMeanFeaturesList(stats.means());
    }

    /**
     * Takes each String and concatenates them
     * @param arr an Array of feature values
     * @return a String of features concatenated by commas
     */
    private String getMeanFeaturesList(double[] arr)
    {
        int iMax = arr.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(arr[i]);
            if (i == iMax)
                return b.toString();
            b.append(", ");
        }
    }

    public RunningStatisticsND getStatisticsND()
    {
        return stats;
    }

    public float getMeanLat()
    {
        this.meanLat = (spatialRange.getLowerBoundForLatitude() + spatialRange.getUpperBoundForLatitude())/2;
        return this.meanLat;
    }

    public float getMeanLon()
    {
        this.meanLon = (spatialRange.getLowerBoundForLongitude() + spatialRange.getUpperBoundForLongitude())/2;
        return this.meanLon;
    }

    public String getFeatures()
    {
        int len = this.features.length();
        if(this.features.charAt(len - 1) == ',')
        {
            return this.features.substring(0, len-1);
        }
        return this.features;
    }

    public double [] getFeatureArray()
    {
        String [] arr = this.features.split(",");

        return Arrays.stream(arr)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public String getMean_features()
    {
        return this.mean_features;
    }

    public double [] getMeanFeatureArray()
    {
        String [] arr = this.mean_features.split(",");

        return Arrays.stream(arr)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public List<ExtStrand> getOriginalStrandsMapped() {
        if(originalStrandsMapped == null)
        {
            originalStrandsMapped = new ArrayList<>();
        }
        return originalStrandsMapped;
    }

}
