package com.abinj.clusters.dao;

import com.abinj.clusters.models.User;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.util.concurrent.CompletableFuture;

import static com.abinj.clusters.constants.MongoConstants.USER_COLLECTION;

public class UserDao {
    private static DB db = new MongoClient().getDB("vertx");

    public static User createUser(User user) {
        JacksonDBCollection<User, String> userCollection =
                JacksonDBCollection.wrap(db.getCollection(USER_COLLECTION),
                        User.class, String.class);
        WriteResult<User, String> result = userCollection.insert(user);
        return result.getSavedObject();
    }

    public static CompletableFuture<Boolean> checkUserDuplicate(String queryKey, String queryValue) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        JacksonDBCollection<User, String> userCollection =
                JacksonDBCollection.wrap(db.getCollection(USER_COLLECTION),
                        User.class, String.class);
        User user = userCollection.findOne(DBQuery.is(queryKey, queryValue));
        if (user != null) {
            future.complete(true);
        } else {
            future.complete(false);
        }
        return future;
    }
}
