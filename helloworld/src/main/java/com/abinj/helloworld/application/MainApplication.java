package com.abinj.helloworld.application;

import io.vertx.core.Vertx;

public class MainApplication {

    public static void main(String[] args) {
        Vertx.vertx().createHttpServer().requestHandler(httpServerRequest -> httpServerRequest.response()
                .end("Hello World!!!")).listen(8080);
    }
}
