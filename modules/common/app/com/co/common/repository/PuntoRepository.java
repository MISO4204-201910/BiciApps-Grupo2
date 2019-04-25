package com.co.common.repository;

import io.ebean.*;
import com.co.common.models.Punto;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import play.libs.concurrent.HttpExecutionContext;


public class PuntoRepository {

    private final EbeanServer ebeanServer;
    private HttpExecutionContext executionContext;

    @Inject
    public PuntoRepository(EbeanConfig ebeanConfig, HttpExecutionContext executionContext) {
        this.ebeanServer = Ebean.getServer(ebeanConfig.defaultServer());
        this.executionContext = executionContext;
    }

    public CompletionStage<List<Punto>> lookupByUserId(Long idUsuario, String categoria) {
        return supplyAsync(() -> ebeanServer.find(Punto.class)
                .where().eq("id_usuario", idUsuario)
                .and().eq("gamification", categoria)
                .findList(), executionContext.current());
    }

    public CompletionStage<Long> insert(Punto punto) {
        return supplyAsync(() -> {
            punto.id = System.currentTimeMillis(); // not ideal, but it works
            ebeanServer.insert(punto);
            return punto.id;
        }, executionContext.current());
    }
}


