package sustain.kNN.mongodb;

import org.bson.Document;
import sustain.kNN.Constants;
import sustain.kNN.utility.exceptions.IncompatibleFeatures;
import sustain.synopsis.common.ExtStrand;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by laksheenmendis on 6/23/20 at 2:23 PM
 */
public class DocGenerator {

    /**
     * Generates the documents to be stored on MongoDB
     * @param strandList extracted strands from sustain-dht
     * @return List of Documents
     */
    public static List<Document> generate(List<ExtStrand> strandList)
    {
        List<Document> documents = new ArrayList<>();
        for( ExtStrand strand: strandList )
        {
            Document innerDoc = new Document(Constants.GEOHASH, strand.getGeohash()).
                    append(Constants.LATITUDE, strand.getMeanLat()).
                    append(Constants.LONGITUDE, strand.getMeanLon());

            //TODO need a more standard way to extract features and relevant feature name
            // this should be possible from metadata server
            String[] featureArr = {Constants.TEMPERATURE,Constants.PRECIPITATION, Constants.HUMIDITY};

            try {
                appendFeatures(innerDoc, featureArr, strand.getMeanFeatureArray());
                Document doc1 = new Document("data", innerDoc);
                documents.add(doc1);
            } catch (IncompatibleFeatures incompatibleFeatures) {
                incompatibleFeatures.printStackTrace();
            }
        }
        return documents;
    }

    /**
     * Appends features and respective values of the sketch to the Document
     * @param document Document to be modified
     * @param fieldNames Feature names to be appended
     * @param meanFeatures Mean values of features to be appended
     * @throws IncompatibleFeatures when number of fieldNames don't match with number of feature values
     */
    private static void appendFeatures(Document document, String [] fieldNames, double[] meanFeatures) throws IncompatibleFeatures {
        //both String array and double array should be of same size
        if( fieldNames.length != meanFeatures.length)
        {
            throw new IncompatibleFeatures("Fields don't match with sketch features");
        }

        for(int i=0; i<fieldNames.length; i++)
        {
            document.append(fieldNames[i], meanFeatures[i]);
        }
    }
}
