package sustain.kNN.preprocessing;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import sustain.kNN.utility.DateTimeExtractor;
import sustain.kNN.utility.PropertyLoader;
import sustain.kNN.utility.exceptions.ValueNotFoundException;
import sustain.synopsis.common.CommonUtil;
import sustain.synopsis.common.ProtoBuffSerializedStrand;
import sustain.synopsis.common.Strand;
import sustain.synopsis.dht.store.services.*;
import sustain.synopsis.sketch.serialization.SerializationOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by laksheenmendis on 6/11/20 at 3:39 PM
 */
public class SketchPreprocessor {

    public static void main(String[] args) throws ValueNotFoundException {

        PropertyLoader.loadPropertyFile();

        System.out.println("Host : " + PropertyLoader.getHost());
        //loadSketches(host, port, dataset, geoHashes.split(Constants.SEPARATOR_COMMA), fromTimestamp, toTimestamp, intermediateOutFile);

    }


    /*private static void loadSketches() throws IOException, ValueNotFoundException {
        Channel channel = ManagedChannelBuilder.forAddress(PropertyLoader.getHost(), PropertyLoader.getPort()).usePlaintext().build();
        TargetedQueryServiceGrpc.TargetedQueryServiceBlockingStub stub =
                TargetedQueryServiceGrpc.newBlockingStub(channel);

        // Temporal predicate - should be provided using epochs (UTC)
        // t >= HH:MM Mon DD, YYYY
        Predicate fromPredicate =
                Predicate.newBuilder().setComparisonOp(Predicate.ComparisonOperator.GREATER_THAN_OR_EQUAL)
                        .setIntegerValue(CommonUtil.localDateTimeToEpoch(DateTimeExtractor.getLocalDateTime(PropertyLoader.getFromTimeStamp())))
                        .build();
        // t < HH:MM Mon DD, YYYY
        Predicate toPredicate = Predicate.newBuilder().setComparisonOp(Predicate.ComparisonOperator.LESS_THAN)
                .setIntegerValue(CommonUtil.localDateTimeToEpoch(DateTimeExtractor.getLocalDateTime(PropertyLoader.getToTimeStamp())))
                .build();
        // combine both predicates such that 00:00 Jan 01, 2015 =< t < 12:00 Jan 03, 2015
        Expression temporalExp =
                Expression.newBuilder().setPredicate1(fromPredicate).setCombineOp(Expression.CombineOperator.AND)
                        .setPredicate2(toPredicate).build();

        FileOutputStream fileOutputStream = new FileOutputStream(PropertyLoader.getIntermediateFile() + PropertyLoader.getDataset() + ".txt");
        SerializationOutputStream dataOutputStream = new SerializationOutputStream(fileOutputStream);

        // run queries for each of the geohashes
        Map<String, Strand> aggregatedStrands = new HashMap<>();
        for (String geohash : PropertyLoader.getGeoHashes()) {
            Predicate spatialPredicate = Predicate.newBuilder().setStringValue(geohash).build();
            // I've set the dataset id to be noaa_2015_jan during ingestion.
            TargetQueryRequest targetQueryRequest =
                    TargetQueryRequest.newBuilder().setDataset(PropertyLoader.getDataset()).addSpatialScope(spatialPredicate).setTemporalScope(temporalExp).build();

            long fetchedData = 0;
            long t1 = System.currentTimeMillis();
            Iterator<TargetQueryResponse> queryResponseIterator = stub.query(targetQueryRequest);
            while (queryResponseIterator.hasNext()) {
                TargetQueryResponse response = queryResponseIterator.next();
                fetchedData += response.getSerializedSize();
                for (ProtoBuffSerializedStrand strand : response.getStrandsList()) {
                    sustain.synopsis.common.Strand s = CommonUtil.protoBuffToStrand(strand);
                    if (aggregatedStrands.containsKey(s.getKey())) {
                        aggregatedStrands.get(s.getKey()).merge(s);
                    } else {
                        aggregatedStrands.put(s.getKey(), s);
                    }
                }
            }
            long t2 = System.currentTimeMillis();
            System.out.println("Query is complete. Elapsed time (ms): " + (t2 - t1) + ", fetched data: " + fetchedData
                    + ", download rate (MB/s): " + fetchedData * 1000 / (1204 * 1024d * (t2 - t1)));
            System.out.println("\n-----------------\n");
        }

        System.out.println("---- Aggregated Strands -----");
        System.out.println("Total Count: " + aggregatedStrands.size());
        for (Strand s : aggregatedStrands.values()) {
//            System.out.println("--");
//            System.out.println("Geohash: " + s.getGeohash());
//            System.out.println("From Timestamp: " + s.getFromTimeStamp());
//            System.out.println("Path: " + s.getPath().stream().map(vertex -> vertex.getLabel().getDouble() + ",")
//                    .collect(Collectors.joining()));
//            RunningStatisticsND stats = s.getPath().get(s.getPath().size() - 1).getData().statistics;
//            System.out.println("Observation Count: " + stats.count());
//            System.out.println("Mean: " + Arrays.toString(stats.means()));
//            System.out.println("Std. Dev.: " + Arrays.toString(stats.stds()));
//            System.out.println("Mins: " + Arrays.toString(stats.mins()));
//            System.out.println("Maxes: " + Arrays.toString(stats.maxes()));

            s.serialize(dataOutputStream);
        }
        dataOutputStream.close();
    }*/
}
