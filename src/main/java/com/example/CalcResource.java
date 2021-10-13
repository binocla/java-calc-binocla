package com.example;

import io.smallrye.mutiny.Multi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/calc")
public class CalcResource {

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String calc(@QueryParam("data") String data) throws ExecutionException, InterruptedException {
        String[] ob = data.split("\\s+");
        AtomicInteger cnt = new AtomicInteger();
        return Multi.createFrom()
                .items(ob)
                .onItem()
                .transform(Integer::parseInt)
                .onItem()
                .transform(cnt::addAndGet)
                .onFailure(Exception.class)
                .recoverWithCompletion()
                .collect()
                .last()
                .onItem()
                .transform(String::valueOf)
                .subscribeAsCompletionStage()
                .get();
    }
}