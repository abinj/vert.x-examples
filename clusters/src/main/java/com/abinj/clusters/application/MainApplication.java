package com.abinj.clusters.application;

import com.abinj.clusters.vertcles.AdminHandler;
import com.abinj.clusters.vertcles.UserHandler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;

public class MainApplication {

    public static void main(String[] args) throws Exception {

        VertxOptions vertxOptions = new VertxOptions().setEventBusOptions(new EventBusOptions());

        Vertx.clusteredVertx(vertxOptions, vertxAsyncResult -> {
            if (vertxAsyncResult.succeeded()) {

            } else {
                Vertx vertx = vertxAsyncResult.result();
                vertx.deployVerticle(new UserHandler());
                vertx.deployVerticle(new AdminHandler());
            }
        });
    }
}
