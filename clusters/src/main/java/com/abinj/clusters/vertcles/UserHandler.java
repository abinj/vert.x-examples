package com.abinj.clusters.vertcles;

import com.abinj.clusters.models.User;
import com.abinj.clusters.services.UserService;
import io.vertx.core.AbstractVerticle;

public class UserHandler extends AbstractVerticle {
    private UserService userService;

    @Override
    public void start() throws Exception {

        vertx.eventBus().consumer("user.signup", message -> {

            User user = (User) message.body();
            userService.userSignUp(user).thenAccept(res -> {
                message.reply(res);
            });
        });
    }
}
