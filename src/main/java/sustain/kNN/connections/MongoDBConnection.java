package sustain.kNN.connections;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * Created by laksheenmendis on 6/21/20 at 11:26 PM
 */
public class MongoDBConnection {

    private static MongoClient mongoClient = createMongoClient();

    public static MongoClient getMongoClient()
    {
        return mongoClient;
    }

    private static MongoClient createMongoClient() {

        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://pierre.cs.colostate.edu:27017");
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        return mongoClient;
    }
}
