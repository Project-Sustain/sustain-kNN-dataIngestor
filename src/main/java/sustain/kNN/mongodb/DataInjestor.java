package sustain.kNN.mongodb;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sustain.kNN.utility.PropertyLoader;
import sustain.kNN.utility.exceptions.ValueNotFoundException;
import java.util.List;

/**
 * Created by laksheenmendis on 6/21/20 at 11:26 PM
 */
public class DataInjestor {

    private MongoClient mongoClient = createMongoClient();

    public DataInjestor() throws ValueNotFoundException {
    }

    public boolean saveDocuments(List<Document> documentList) {

        boolean successfull = true;
        try {

            /*// if we need to add shards here, use below code
            // Add the shards
            final int [] _shardPorts = { 27018, 27019 };
            for (final int shardPort : _shardPorts) {
                final CommandResult result
                        = mongo.getDB("admin").command(new BasicDBObject("addshard", ("localhost:" + shardPort)));
                System.out.println(result);
            }
            // Sleep for a bit to wait for all the nodes to be intialized.
            Thread.sleep(5000);*/

            // before sharding the collection, need to enable sharding at the DB level
            try{
                mongoClient.getDatabase("admin").runCommand(new BasicDBObject("enablesharding", PropertyLoader.getMongoDBDB()));
            }
            catch (MongoCommandException e)
            {
                e.printStackTrace();
                if(e.getCode() != 23) // this happens when the DB is already shard enabled, thus, we can proceed with it
                {
                    System.out.println(e.getResponse().toJson());
                    return false;
                }
            }

            //TODO change these accordingly
            final BasicDBObject shardKey = new BasicDBObject(PropertyLoader.getMongoDBShardKey(), "hashed");

            final BasicDBObject cmd = new BasicDBObject("shardCollection", getShardCollectionString());
            cmd.put("key", shardKey);

            // Running the command to create the sharded collection
            try{
                Document resultDoc = mongoClient.getDatabase("admin").runCommand(cmd);
            }
            catch (MongoCommandException e)
            {
                if(e.getCode() != 23) // this happens when the collection is already shard enabled, thus, we can proceed with it
                {
                    System.out.println(e.getResponse().toJson());
                    return false;
                }
            }

            // If a database does not exist, MongoDB creates the database when you first store data for that database.
            MongoDatabase database = mongoClient.getDatabase(PropertyLoader.getMongoDBDB());
            // If a collection does not exist, MongoDB creates the collection when you first store data for that collection.
            MongoCollection<Document> collection = database.getCollection(PropertyLoader.getMongoDBCollection());

            // TODO add validation options if needed
//        ValidationOptions collOptions = new ValidationOptions().validator(
//                Filters.or(Filters.exists("GeoHash")));
//        database.createCollection(PropertyLoader.getMongoDBCollection(),
//                new CreateCollectionOptions().validationOptions(collOptions));

            collection.insertMany(documentList);
        } catch (ValueNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            successfull = false;
        }
        return successfull;
    }

    private MongoClient getMongoClient() {
        return mongoClient;
    }

    private MongoClient createMongoClient() throws ValueNotFoundException {

        MongoClientURI mongoClientURI = new MongoClientURI(getConnectionString());
        return new MongoClient(mongoClientURI);
    }

    private String getConnectionString() throws ValueNotFoundException {
        StringBuilder sb = new StringBuilder("mongodb://");

        sb.append(PropertyLoader.getMongoDBHost());
        sb.append(":");
        sb.append(PropertyLoader.getMongoDBPort());

        return sb.toString();
    }

    private String getShardCollectionString() throws ValueNotFoundException{
        StringBuilder sb = new StringBuilder();

        sb.append(PropertyLoader.getMongoDBDB());
        sb.append(".");
        sb.append(PropertyLoader.getMongoDBCollection());

        return sb.toString();
    }
}
