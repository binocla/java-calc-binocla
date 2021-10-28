package com.example;

import io.smallrye.mutiny.Multi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Path("/calc")
public class CalcResource {

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String calc(@QueryParam("data") String data) throws ExecutionException, InterruptedException {
        String[] ob = data.split("\\s+");
        // + - URL Encoded %2B
        // todo Сделать * / -
        AtomicReference<BigInteger> cnt = new AtomicReference<>();
        cnt.set(new BigInteger("0"));
        return Multi.createFrom()
                .items(ob)
                .onItem()
                .transform(BigInteger::new)
                .onItem()
                .transform(x -> cnt.getAndSet(cnt.get().add(x)))
                .onFailure(Exception.class)
                .recoverWithCompletion()
                .collect()
                .last()
                .onItem()
                .transform(x -> cnt.get().toString())
                .subscribeAsCompletionStage()
                .get();
    }

}