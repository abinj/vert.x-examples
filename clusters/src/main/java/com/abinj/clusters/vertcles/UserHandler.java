package com.abinj.clusters.vertcles;

import static com.abinj.clusters.constants.ResponseConstants.*;
import com.abinj.clusters.dao.UserDao;
import com.abinj.clusters.models.User;
import com.abinj.clusters.utility.ResponseUtility;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.CompletableFuture;

import static com.abinj.clusters.constants.MongoConstants.EMAIL;

public class UserHandler extends AbstractVerticle {

    @Override
    public void start() throws Exception {

        vertx.eventBus().consumer("user.signup", message -> {
            CompletableFuture<Boolean> future1;
            CompletableFuture<Object> future2;

            User user = (User) message.body();
            String email = user.getEmail();
            future1 = UserDao.checkUserDuplicate(EMAIL, email);
            future2 = future1.thenCompose( result -> {
                CompletableFuture<Object> future = new CompletableFuture<Object>();
                if (result) {
                    String responseMessage = "An account already exist for user:" + user.getEmail();
                    Object response = ResponseUtility.bindRequestResponse(FAILURE, responseMessage);
                    future.complete(response);
                } else {
                    UserDao.createUser(user);
                    String responseMessage = "A new account created for user:" + user.getEmail();
                    JsonObject response = ResponseUtility.bindRequestResponse(SUCCESS, responseMessage);
                    response.put(USER, UserDao.createUser(user));
                    future.complete(response);
                }
                return future;
            });

        });
    }
}
